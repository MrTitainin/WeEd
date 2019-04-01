package wesnoth.editor;

import java.io.File;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class FileTree extends JTree implements TreeSelectionListener{
	private static final long serialVersionUID = 1L;
	JTextArea textArea;
	TagTree tagTree;
	FormPanel formPanel;

	FileTree(JTextArea textPanel, TagTree tagTree, FormPanel formPanel){
		this.textArea = textPanel;
		this.tagTree = tagTree;
		this.formPanel = formPanel;
		
		setModel(null);
		
		getSelectionModel().setSelectionMode(1);
		addTreeSelectionListener(this);
	}
	
	protected void generateTree(File folder){
		removeAll();
		
		if (!folder.exists()) {
			return;
		}
		
		String path = folder.getAbsolutePath();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(path);
		addNodes(root, folder);
		setModel(new DefaultTreeModel(root));
	}
	
	private static void addNodes(DefaultMutableTreeNode root, File folder){
		File[] files = folder.listFiles();
		
		for (File file : files) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName());
			if (file.isDirectory())
			{
				addNodes(node, file);
			}
			root.add(node);
		}
	}
	
	private String getFilePath(DefaultMutableTreeNode node) {
		String result = "";
		for (Object o : node.getPath()) {
			result = result + System.getProperty("file.separator") + o.toString();
		}
		return result;
	}
	
	public void valueChanged(TreeSelectionEvent e){
		String name = getFilePath((DefaultMutableTreeNode)e.getPath().getLastPathComponent());
		
		File file = new File(name);
		this.textArea.setText("");
		
		if (file.isFile()) {
			String extension = name.substring(name.lastIndexOf(".") + 1);
			this.tagTree.implement(null);
			if (extension.equals("cfg")) {
				CfgParser parser = new CfgParser(this.textArea, this.formPanel.getTagList());
				WMLTreeModel hierarchyTree = parser.parseCfg(file);
				this.tagTree.implement(hierarchyTree);
			} else if (extension.equals("png")) {
				ImageDisplayer.displayPicture(file, this.formPanel, this.textArea);
			}
		}
	}
}