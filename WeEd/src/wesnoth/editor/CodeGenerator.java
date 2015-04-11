package wesnoth.editor;

import java.util.HashMap;
import javax.swing.tree.TreeModel;

public class CodeGenerator
{
  static final String INDENTATION = "        ";

  protected static String parseTagTree(TreeModel treeModel, HashMap<String, KeyData[]> keyList)
  {
    System.out.println("start parsing the tree");

    String result = "";

    WMLTreeNode root = (WMLTreeNode)treeModel.getRoot();

    HashMap<String, String> data = root.getAttributes();
    if ((data != null) && (data.containsKey("#textdomain"))) {
      result = result + newLine(0, new StringBuilder().append("#textdomain ").append((String)data.get("#textdomain")).toString());
    }

    for (int i = 0; i < root.getChildCount(); i++) {
      WMLTreeNode newNode = (WMLTreeNode)root.getChildAt(i);
      result = result + examineNode(0, newNode, keyList);
    }

    return result;
  }

  private static String examineNode(int indentation, WMLTreeNode node, HashMap<String, KeyData[]> keyList) {
    HashMap<String, String> data = node.getAttributes();
    String tag = node.toString();
    boolean isMacro = tag.charAt(0) == '{';
    String[] macros = node.getMacros().split(System.getProperty("line.separator"));
    String result;
    if (isMacro)
      result = newLine(indentation, "#define " + tag.substring(1, tag.length() - 1));
    else {
      result = newLine(indentation, tag);
    }

    if (keyList.containsKey(tag)) {
      KeyData[] keys = (KeyData[])keyList.get(tag);

      for (KeyData key : keys) {
        String keyString = key.toString();
        if (data.containsKey(keyString)) {
          String s = keyString + '=';
          String value = (String)data.get(keyString);

          if (!key.isBig()) {
            if (key.isTranslatable()) {
              s = s + "_";
            }
            if (key.isEnquoted()) {
              value = "\"" + value + "\"";
            }
          }
          result = result + newLine(indentation + 1, new StringBuilder().append(s).append(value).toString());
        } else if (key.isUniversal()) {
          String[] universalKeys = (String[])data.keySet().toArray(new String[0]);
          for (String universalKey : universalKeys) {
            String s = universalKey + '=';
            String value = (String)data.get(universalKey);

            if (!key.isBig()) {
              if (key.isTranslatable()) {
                s = s + "_";
              }
              if (key.isEnquoted()) {
                value = "\"" + value + "\"";
              }
            }
            result = result + newLine(indentation + 1, new StringBuilder().append(s).append(value).toString());
          }
        }
      }
    }

    for (String macro : macros) {
      if (!macro.isEmpty()) {
        result = result + newLine(indentation + 1, macro);
      }
    }
    for (int i = 0; i < node.getChildCount(); i++) {
      WMLTreeNode newNode = (WMLTreeNode)node.getChildAt(i);
      result = result + examineNode(indentation + 1, newNode, keyList);
    }

    if (isMacro)
      result = result + newLine(indentation, new StringBuilder().append("#enddef").append(System.getProperty("line.separator")).toString());
    else {
      result = result + newLine(indentation, new StringBuilder().append("[/").append(tag.substring(1)).toString());
    }
    return result;
  }

  private static String newLine(int indentation, String s) {
    String result = "";

    for (int i = 0; i < indentation; i++) {
      result = result + "        ";
    }

    result = result + s + System.getProperty("line.separator");

    return result;
  }
}