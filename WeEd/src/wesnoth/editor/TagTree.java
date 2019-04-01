package wesnoth.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;


public class TagTree extends JTree implements TreeSelectionListener, MouseListener, ActionListener{
	private static final long serialVersionUID = 1L;
	private FormPanel formPanel;
	private JTextArea textPanel;
	private JPopupMenu popupMenu;

	public TagTree(FormPanel formPanel, JTextArea textPanel){
		this.formPanel = formPanel;
	    this.textPanel = textPanel;
	    this.popupMenu = new JPopupMenu();

	    setModel(null);
	    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	    Icon leaf = renderer.getLeafIcon();
	    renderer.setClosedIcon(leaf);
	    renderer.setOpenIcon(leaf);
	    setCellRenderer(renderer);
	
	    JMenuItem newTag = new JMenuItem("New tag");
	    newTag.addActionListener(this);
	    newTag.setActionCommand("new_tag");
	    this.popupMenu.add(newTag);
	    JMenuItem removeTag = new JMenuItem("Remove tag");
	    removeTag.addActionListener(this);
	    removeTag.setActionCommand("remove_tag");
	    this.popupMenu.add(removeTag);
	    this.popupMenu.addSeparator();
	    JMenuItem moveUp = new JMenuItem("Move up");
	    moveUp.addActionListener(this);
	    moveUp.setActionCommand("move_up");
	    this.popupMenu.add(moveUp);
	    JMenuItem moveDown = new JMenuItem("Move down");
	    moveDown.addActionListener(this);
	    moveDown.setActionCommand("move_down");
	    this.popupMenu.add(moveDown);
	
	    getSelectionModel().setSelectionMode(1);
	    addTreeSelectionListener(this);
	    addMouseListener(this);
	}

	protected void implement(WMLTreeModel tree) {
		implement(tree, 0);
	}
	protected void implement(WMLTreeModel tree, int row) {
		if (tree != null) {
			setModel(tree);
			setSelectionRow(row);
	    }
		else {
			setModel(null);
			this.formPanel.removeAll();
	    }
	}
	
	protected void save() {
		if (this.treeModel == null) {
	      return;
	    }
	    this.formPanel.save();
	
	    String output = CodeGenerator.parseTagTree(getModel(), this.formPanel.getTagList());
	    this.textPanel.setText(output);
	
	    Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
	    boolean saveToFile = prefs.getBoolean("saveToFile", true);
	
	    if (saveToFile) {
	    	FileOutputStream fstream = null;
	    	try {
	    		fstream = new FileOutputStream(this.treeModel.getRoot().toString());
	    		OutputStreamWriter out = new OutputStreamWriter(fstream);
	    		BufferedWriter br = new BufferedWriter(out);
	    		br.write(output);
	    		br.close();
    		}
	    	catch (FileNotFoundException ex) {
	    		Logger.getLogger(TagTree.class.getName()).log(Level.SEVERE, null, ex);
	    	}	
	    	catch (IOException ex) {
	    		Logger.getLogger(TagTree.class.getName()).log(Level.SEVERE, null, ex);
	    	}
	    	finally {
	    		try {
	    			fstream.close();
		        } catch (IOException ex) {
		        	Logger.getLogger(TagTree.class.getName()).log(Level.SEVERE, null, ex);
	          	}
	        }
	    }
	}
	
	public void valueChanged(TreeSelectionEvent e){
		WMLTreeNode selection = (WMLTreeNode)e.getPath().getLastPathComponent();
		boolean root = e.getPath().getPathCount() == 1;
		this.formPanel.save();
		this.formPanel.displayData(selection, root);
	}
	
	public void mouseClicked(MouseEvent e){
		if ((e.getButton() == 3) && (getSelectionPath() != null)) this.popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}
	
	public void mousePressed(MouseEvent e) {
		setSelectionPath(getPathForLocation(e.getX(), e.getY()));
	}
	
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mouseEntered(MouseEvent e) {
	}
	
	public void mouseExited(MouseEvent e) {
	}
	
	public HashMap<String, Tag> getTagList() {
		return this.formPanel.getTagList();
	}
	
	public void actionPerformed(ActionEvent e) {
		String comand = e.getActionCommand();
		TreePath path = getSelectionPath();
		WMLTreeModel treeModel = (WMLTreeModel)getModel();
		switch (comand) {
		case "new_tag":
			WMLTreeNode selectedNode = (WMLTreeNode)path.getLastPathComponent();
			WMLTreeNode newNode = new WMLTreeNode(JOptionPane.showInputDialog(this, "Enter the name of the new child of " + selectedNode), selectedNode);
			if ((newNode.toString() != null) && (!newNode.toString().isEmpty()))
		    treeModel.newTag(path, newNode); break;
		    case "remove_tag":
		    	treeModel.removeTag(path);
		      	break;
		    case "move_up":
		    	treeModel.moveUp(path);
		      	break;
		    case "move_down":
		    	break;
		    default:
		    	throw new UnsupportedOperationException("Not supported yet.");
		    }
	  }
}