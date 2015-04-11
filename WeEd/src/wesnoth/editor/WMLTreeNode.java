package wesnoth.editor;

import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.tree.TreeNode;

public class WMLTreeNode implements TreeNode{
  String tag;
  String macros;
  HashMap<String, String> attributes;
  WMLTreeNode[] children;
  WMLTreeNode parent;

  WMLTreeNode(String name, WMLTreeNode parent)
  {
    this.tag = name;
    this.parent = parent;
    this.macros = "";
    this.attributes = new HashMap<String, String>();
    this.children = new WMLTreeNode[0];
  }

  public HashMap<String, String> getAttributes() {
    return this.attributes;
  }

  public void setAttributes(HashMap<String, String> attributes) {
    this.attributes = attributes;
  }

  public void addAttribute(String key, String value) {
    this.attributes.put(key, value);
  }

  public String getMacros() {
    return this.macros;
  }

  public void setChildren(WMLTreeNode[] children) {
    this.children = children;
  }

  public WMLTreeNode[] getChildren() {
    return this.children;
  }

  public void addMacro(String macro) {
    this.macros = (this.macros + macro + System.getProperty("line.separator"));
  }

  public void setMacros(String macro) {
    this.macros = macro;
  }

  public String toString()
  {
    return this.tag;
  }

  public TreeNode getChildAt(int childIndex)
  {
    return this.children[childIndex];
  }

  public int getChildCount()
  {
    return this.children.length;
  }

  public WMLTreeNode getParent()
  {
    return this.parent;
  }

  public boolean getAllowsChildren()
  {
    return true;
  }

  public boolean isLeaf()
  {
    return this.children.length == 0;
  }

  public Enumeration children()
  {
    throw new UnsupportedOperationException("Not supported.");
  }

  public int getIndex(TreeNode node)
  {
    for (int i = 0; i < getChildCount(); i++) {
      if (this.children[i] == node)
        return i;
    }
    return -1;
  }
}