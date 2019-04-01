package wesnoth.editor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MenuListener implements ActionListener, ItemListener{
	private FileTree fileTree;
	private JTextArea textArea;
	private TagTree tagTree;
	@SuppressWarnings("unused")
	private TagList tagList;

	public MenuListener(FileTree fileTree, JTextArea textArea, TagTree tagTree) {
		this.fileTree = fileTree;
		this.textArea = textArea;
		this.tagTree = tagTree;
	}

	public void actionPerformed(ActionEvent e){
		try {
			String comand = e.getActionCommand();
	    	switch (comand) {
	    		case "open":
	    			openFile();
	    			break;
	    		case "save":
	    			saveFile();
	    			break;
	    		case "run_wesnoth":
	    			run();
	    			break;
	    		case "quit":
	    			System.exit(0);
	    			break;
	    		case "clear_settings":
	    			clearSettings();
		    		 break;
		     	case "saving_options":
		     		break;
		     	case "set_paths":
		     		setPaths();
		     		break;
		     	case "manage_tags":
		     		showTagsList();
		     		break;
		     	case "manage_macros":
		         	
		         	break;
		     	default:
		     		throw new UnsupportedOperationException("Not supported yet.");
	      	}
	    } catch (IOException|BackingStoreException ex) {
	    	Logger.getLogger(MenuListener.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	public void itemStateChanged(ItemEvent e) {
	    throw new UnsupportedOperationException("Not supported yet.");
	}
	
	private void openFile(){
	    JFileChooser chooser = new JFileChooser();
	    int returnVal = chooser.showOpenDialog(this.fileTree);
	    if(returnVal == 0) {
	    	this.fileTree.clearSelection();
	    	CfgParser parser = new CfgParser(this.textArea, this.tagTree.getTagList());
	    	WMLTreeModel hierarchyTree = parser.parseCfg(chooser.getSelectedFile());
	    	this.tagTree.implement(hierarchyTree);
	    }
	}
	
	private void saveFile() {
	    this.tagTree.save();
	}
	
	private void run() throws IOException {
	    Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
	    String path = prefs.get("wesPath", null);
	    File exe = new File(path + "\\wesnoth.exe");
	    if((exe.exists()) && (exe.canExecute())) Runtime.getRuntime().exec(exe.getAbsolutePath(), null, new File(path));
	    else JOptionPane.showMessageDialog(this.fileTree, "Currently only supported in Windows.");
	}
	
	protected void setPaths() {
	    final JFrame frame = new JFrame("Set Paths");
	    JPanel panel = new JPanel();
	    JPanel wesPanel = new JPanel();
	    JPanel addPanel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, 3));
	
	    Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
	    String wesPath = prefs.get("wesPath", null);
	    String addPath = prefs.get("addPath", null);
	
	    final JTextField wesPathField = new JTextField(wesPath);
	    JButton setWesPath = new JButton("Set path to game folder");
	    final JTextField addPathField = new JTextField(addPath);
	    JButton setAddPath = new JButton("Set path to addons folder");
	    JButton saveChanges = new JButton("Save");
	
	    setWesPath.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		JFileChooser chooser = new JFileChooser();
	    		chooser.setFileSelectionMode(1);
	    		int returnVal = chooser.showOpenDialog(MenuListener.this.fileTree);
	    		if (returnVal == 0) wesPathField.setText(chooser.getSelectedFile().getAbsolutePath());
    		}
	    });
	    setAddPath.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e) {
	    		JFileChooser chooser = new JFileChooser();
	    		chooser.setFileSelectionMode(1);
	    		chooser.setFileHidingEnabled(false);
	    		int returnVal = chooser.showOpenDialog(MenuListener.this.fileTree);
	    		if (returnVal == 0)
	    			addPathField.setText(chooser.getSelectedFile().getAbsolutePath());
	    	}
	    });
	    saveChanges.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e) {
	    		frame.setVisible(false);
	    		Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
	    		prefs.put("wesPath", wesPathField.getText());
	    		prefs.put("addPath", addPathField.getText());
	
	    		MenuListener.this.fileTree.generateTree(new File(addPathField.getText()));
	    		frame.dispose();
	    	}
	    });
	    setWesPath.setPreferredSize(new Dimension(180, 32));
	    setAddPath.setPreferredSize(new Dimension(180, 32));
	    wesPathField.setPreferredSize(new Dimension(390, 32));
	    addPathField.setPreferredSize(new Dimension(390, 32));
	
	    wesPanel.add(setWesPath);
	    wesPanel.add(wesPathField);
	    addPanel.add(setAddPath);
	    addPanel.add(addPathField);
	
	    panel.add(wesPanel);
	    panel.add(addPanel);
	    panel.add(saveChanges);
	
	    frame.add(panel);
	    frame.setMinimumSize(new Dimension(600, 160));
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
	private void showTagsList() {
		tagList =  new TagList(DatabaseManager.database.tags);
	}
	
	 /* private void saveOptions() {
	    final JFrame frame = new JFrame("Save options");
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, 3));
	
	    Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
	    boolean save = prefs.getBoolean("saveToFile", false);
	    boolean auto = prefs.getBoolean("autoSave", true);
	
	    final JCheckBox saveToFile = new JCheckBox("Save to file", save);
	    final JCheckBox autoSave = new JCheckBox("Autosave", auto);
	    JButton saveChanges = new JButton("Save");
	
	    saveChanges.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e) {
	        Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
	        prefs.putBoolean("saveToFile", saveToFile.isSelected());
	        prefs.putBoolean("autoSave", autoSave.isSelected());
	        frame.setVisible(false);
	        frame.dispose();
	      }
	    });
	    panel.add(saveToFile);
	    panel.add(autoSave);
	    panel.add(saveChanges);
	
	    frame.add(panel);
	    frame.setMinimumSize(new Dimension(100, 130));
	    frame.setResizable(false);
	    frame.setVisible(true);
	  }*/
	private void clearSettings() throws BackingStoreException {
		Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
	    prefs.clear();
	}
}