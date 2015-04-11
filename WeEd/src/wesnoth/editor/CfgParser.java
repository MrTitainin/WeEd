package wesnoth.editor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JTextArea;

public class CfgParser {
	private JTextArea output;
	private HashMap<String, KeyData[]> keyList;

	public CfgParser(JTextArea textArea, HashMap<String, KeyData[]> keys)
	{
		this.output = textArea;
		this.keyList = keys;
	}
	/**
	 * General parse function, changes file into reader, catches errors
	 * @param file - a file to read
	 * @return WMLTreeModel
	 */
	public WMLTreeModel parseCfg(File file) {
		WMLTreeNode root = new WMLTreeNode(file.getAbsolutePath(), null);
		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			this.output.removeAll();

			parseCFG(br, root, null, null);

			in.close();
		}
		catch (Exception e) {
			this.output.removeAll();
			String error = "Error while parsing:" + System.getProperty("line.separator");
			error = error + e.getMessage() + System.getProperty("line.separator");
			error = error + e.getStackTrace().toString() + System.getProperty("line.separator");
			error = error + e.getLocalizedMessage() + System.getProperty("line.separator");
			e.printStackTrace();
			this.output.setText(error);
		}

		WMLTreeModel treeModel = new WMLTreeModel(root);
		return treeModel;
	}
		/**
		 * Proper parse function, it often calls itself
		 * @param br   - a reader of file, defined in general function from file
		 * @param node - a tree node, in first call it's a root node
		 * @param tag  - when this function is called by itself, it's a start tag, otherwise null
		 * @param end  - when this function is called by itself, it's an end tag, otherwise null
		 * @throws IOException
		 */
	private void parseCFG(BufferedReader br, WMLTreeNode node, String tag, String end) throws IOException {
		HashMap<String, String> attributes = new HashMap<String, String>();//attributs are tags, macros and #if's, also #textdomain, but in other way
		LinkedList<WMLTreeNode> children = new LinkedList<WMLTreeNode>();//list of children nodes
    
		KeyData[] keys = (KeyData[])this.keyList.get(tag);
		String strLine;
		while ((strLine = br.readLine()) != null) {
			String trimmed = strLine.trim();
			this.output.append(strLine + System.getProperty("line.separator"));//shows line in output (right panel)

			if (!trimmed.isEmpty()){ //prevents displaying new line and more similar operations pn empty line
				System.out.print("New line: ");//console info
				System.out.println(trimmed);//also console info

				if ((end != null) && (trimmed.startsWith(end))) {//code used in tags, tests for closing tag
					node.setAttributes(attributes);//if tag ends, adds attributes
					node.setChildren((WMLTreeNode[])children.toArray(new WMLTreeNode[0]));//if tag ends, adds childrens
					return;//ends loop
				}

				switch (trimmed.charAt(0)){//switch depending on first char
				case '#'://macro defines and hash if's, also textdomain
					trimmed = trimmed.substring(1);//deletes hash symbol

					if (trimmed.startsWith("textdomain")) {//#textdomain case
						attributes.put("#textdomain", trimmed.substring(10).trim());//adds textdomain as an attribut
        	 }
					else if (trimmed.startsWith("define")) {//#define case
						WMLTreeNode macro = new WMLTreeNode('{' + trimmed.substring(6).trim() + '}', node);//to attributes adds macro in format {name}
						parseMacro(br, macro);//simply reads all of macro define without causing nested tags, macros and others to work as in tag reading
						children.add(macro);//adds node to list of nodes, but it isn't working properly because of no reading nested tags and others
					}
					else if (trimmed.startsWith("ifdef")) {//#ifdef case
						WMLTreeNode definePreprocessor = new WMLTreeNode("test for defined " + trimmed.substring(5).trim(), node);//like above, but for #ifdef
						parseIfdef(br, definePreprocessor);
						children.add(definePreprocessor);
					}
					else if (trimmed.startsWith("ifver")) {//#ifver case
						WMLTreeNode definePreprocessor = new WMLTreeNode("version test: " + trimmed.substring(5).trim(), node);//like above, but for #ifver
						parseIfdef(br, definePreprocessor);
						children.add(definePreprocessor);
					}
					else if (trimmed.startsWith("ifhave")) {//#ifhave case
						WMLTreeNode definePreprocessor = new WMLTreeNode("search for file: " + trimmed.substring(6).trim(), node);//like above, but for #ifhave
						parseIfdef(br, definePreprocessor);
						children.add(definePreprocessor);
					}
					else{
						node.addMacro("#" + trimmed);//otherwise, it's added to content
					}
					break;
				case '[':
					trimmed = trimmed.substring(1);
					System.out.println("Tag found: [" + trimmed);

					if (!trimmed.startsWith("/")) {
						WMLTreeNode subtag = new WMLTreeNode("[" + trimmed, node);//if find new tag open
						if(trimmed.startsWith("+")){//catch for rare "+" tags
							parseCFG(br, subtag, "[" + trimmed, "[/" + trimmed.substring(1));//it calls itself with new parameters - new node for subtag, tag open and expected tag end
						}
						else{
							parseCFG(br, subtag, "[" + trimmed, "[/" + trimmed);//it calls itself with new parameters - new node for subtag, tag open and expected tag end
						}
						children.add(subtag);//adds new (filled by previous function) node to this node
					} else {
						node.addMacro(trimmed);//it's probably an error in file, but not something what causes to reading failure
					}
					break;
				case '{':
					node.addMacro(trimmed);//for macros, it's only adds it to content
					break;
				default:
					if (trimmed.contains("=")) {//keys
						int split = trimmed.indexOf('=');
						if (split < 0) {
							continue;//catch of bug, for me unknown
						}
						String key = trimmed.substring(0, split).trim();//splits to "=", it's a name of key
						String value = trimmed.substring(split + 1).trim();//splits from "=", it's a value of key

						if (insert(key, value, attributes, keys, br)) node.addMacro(trimmed);//???
						break;
					}
					node.addMacro(trimmed);//if it isn't a key, adds to content
				}

				System.out.println("Line parsed: " + strLine.trim());//console info
			}
		}
		node.setAttributes(attributes);//if the file ends, it do exactly the same as in case that tag ends
		node.setChildren((WMLTreeNode[])children.toArray(new WMLTreeNode[0]));
	}

	private void parseMacro(BufferedReader br, WMLTreeNode node) throws IOException {
		/*String strLine;
		while ((strLine = br.readLine()) != null) {
			this.output.append(strLine + System.getProperty("line.separator"));//adds to content

			if (strLine.equals("#enddef")) {
				return;//it reads until #enddef is found
			}
			node.addMacro(strLine);//adds to content
		}*/
		parseCFG(br,node,null,"#enddef");
	}
	private void parseIfdef(BufferedReader br, WMLTreeNode node) throws IOException {//same as above, but until #endif
    	/*String strLine;
    	while ((strLine = br.readLine()) != null) {
	      	this.output.append(strLine + System.getProperty("line.separator"));

	      	if (strLine.equals("#endif")) {
	      		return;
	      	}
	      	node.addMacro(strLine);
    	}*/
    	parseCFG(br,node,null,"#endif");
	}

	private boolean insert(String key, String value, HashMap<String, String> attributes, KeyData[] keys, BufferedReader br) throws IOException {
		if ((keys != null) && (key != null)) {
			for (KeyData keyData : keys) {
				if ((keyData.isUniversal()) || (keyData.toString().equals(key))) {
					attributes.put(key, keyData.parseString(value, br));
					return false;
				}
			}
		}
		return true;
	}
}