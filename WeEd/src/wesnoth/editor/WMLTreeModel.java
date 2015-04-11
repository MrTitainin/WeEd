package wesnoth.editor;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class WMLTreeModel
  implements TreeModel
{
  WMLTreeNode root;
  TreeModelListener treeModelListener;

  WMLTreeModel(WMLTreeNode root)
  {
    this.root = root;
    this.treeModelListener = null;
  }

  public Object getRoot()
  {
    return this.root;
  }

  public Object getChild(Object parent, int index)
  {
    return ((WMLTreeNode)parent).getChildAt(index);
  }

  public int getChildCount(Object parent)
  {
    return ((WMLTreeNode)parent).getChildCount();
  }

  public boolean isLeaf(Object node)
  {
    return ((WMLTreeNode)node).isLeaf();
  }

  public void valueForPathChanged(TreePath path, Object newValue)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public int getIndexOfChild(Object parent, Object child)
  {
    WMLTreeNode node = (WMLTreeNode)parent;
    return node.getIndex((WMLTreeNode)child);
  }

  public void addTreeModelListener(TreeModelListener l)
  {
    this.treeModelListener = l;
  }

  public void removeTreeModelListener(TreeModelListener l)
  {
    this.treeModelListener = null;
  }

  public void removeTag(TreePath path) {
    WMLTreeNode node = (WMLTreeNode)path.getLastPathComponent();
    WMLTreeNode parent = node.getParent();
    WMLTreeNode[] siblings = parent.getChildren();
    WMLTreeNode[] temp = new WMLTreeNode[siblings.length - 1];
    int j = 0;
    for (int i = 0; i < temp.length; i++) {
      if (siblings[i] == node) {
        j++;
      }
      temp[i] = siblings[j];
      j++;
    }
    parent.setChildren(temp);

    this.treeModelListener.treeNodesRemoved(new TreeModelEvent(this, path.getParentPath()));
    this.treeModelListener.treeStructureChanged(new TreeModelEvent(this, path));
  }

  public void newTag(TreePath path, WMLTreeNode newNode) {
    WMLTreeNode parent = (WMLTreeNode)path.getLastPathComponent();
    WMLTreeNode[] children = parent.getChildren();
    WMLTreeNode[] temp = new WMLTreeNode[children.length + 1];
    System.arraycopy(children, 0, temp, 0, children.length);
    temp[children.length] = newNode;
    parent.setChildren(temp);

    this.treeModelListener.treeStructureChanged(new TreeModelEvent(this, path));
  }

  public void moveUp(TreePath path) {
    WMLTreeNode node = (WMLTreeNode)path.getLastPathComponent();
    WMLTreeNode parent = node.getParent();
    WMLTreeNode[] siblings = parent.getChildren();
    for (int i = 1; i < siblings.length; i++) {
      if (siblings[i] == node) {
        siblings[i] = siblings[(i - 1)];
        siblings[(i - 1)] = node;
        parent.setChildren(siblings);
        break;
      }
    }

    this.treeModelListener.treeStructureChanged(new TreeModelEvent(this, path.getParentPath()));
  }

  public void moveDown(WMLTreeNode node)
  {
  }
}