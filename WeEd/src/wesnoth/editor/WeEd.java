package wesnoth.editor;

import java.awt.Dimension;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class WeEd {
	static boolean showAllUnitOptions = false;
	public WeEd(){
	  
	}
	public static void main(String[] args) {
		if (args.length > 1) {
			try {
				Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
				prefs.clear();
			} catch (BackingStoreException ex) {
				Logger.getLogger(WeEd.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

		JFrame frame = new JFrame("Wesnoth Editor (Weed) by ZawaPL");
		frame.setDefaultCloseOperation(3);
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setResizable(true);
		frame.setVisible(true);

		createFrameContent(frame);

		frame.pack();
	}

	private static void createFrameContent(JFrame frame) {
		JTextArea textPanel = new JTextArea();
		textPanel.setEditable(false);
		JScrollPane scrollTextPanel = new JScrollPane(textPanel);
		scrollTextPanel.setMinimumSize(new Dimension(200, 550));

		FormPanel formPanel = new FormPanel();
		JScrollPane scrollFormPanel = new JScrollPane(formPanel);
		scrollFormPanel.setMinimumSize(new Dimension(200, 550));
		scrollFormPanel.setPreferredSize(new Dimension(355, 695));

		TagTree tagTree = new TagTree(formPanel, textPanel);
		JScrollPane scrollTagTree = new JScrollPane(tagTree);
		scrollTagTree.setMinimumSize(new Dimension(200, 250));

		FileTree fileTree = new FileTree(textPanel, tagTree, formPanel);
		JScrollPane scrollFileTree = new JScrollPane(fileTree);
		scrollFileTree.setMinimumSize(new Dimension(200, 250));

		JSplitPane innerJSplitPane = new JSplitPane(1, true, scrollFormPanel, scrollTextPanel);
		innerJSplitPane.setOneTouchExpandable(true);
		JSplitPane treesJSplitPane = new JSplitPane(0, true, scrollFileTree, scrollTagTree);
		treesJSplitPane.setOneTouchExpandable(true);
		JSplitPane container = new JSplitPane(1, true, treesJSplitPane, innerJSplitPane);
		container.setOneTouchExpandable(true);
		frame.add(container);

		createFrameMenu(frame, new MenuListener(fileTree, textPanel, tagTree));

		Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
		String path = prefs.get("addPath", null);
		if (path == null)
			new MenuListener(fileTree, textPanel, tagTree).setPaths();
		else fileTree.generateTree(new File(path));
	}

	private static void createFrameMenu(JFrame frame, MenuListener menuListener) {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(70);

		fileMenu.add(newMenuItem("Open", menuListener, 79));
		fileMenu.add(newMenuItem("Save", menuListener, 83));
		fileMenu.addSeparator();
		fileMenu.add(newMenuItem("Run Wesnoth", menuListener, 82));
		fileMenu.addSeparator();
		fileMenu.add(newMenuItem("Quit", menuListener, 81));

		JMenu optionMenu = new JMenu("Options");
		optionMenu.setMnemonic(84);

		optionMenu.add(newMenuItem("Clear settings", menuListener));
		optionMenu.addSeparator();
		optionMenu.add(newMenuItem("Editor settings", menuListener));
		optionMenu.add(newMenuItem("Set paths", menuListener));
		optionMenu.add(newMenuItem("Saving options", menuListener));

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(72);

		helpMenu.add(newMenuItem("About", menuListener));

		menuBar.add(fileMenu);
		menuBar.add(optionMenu);
		menuBar.add(helpMenu);

		frame.setJMenuBar(menuBar);
	}

	private static JMenuItem newMenuItem(String name, MenuListener menuListener, int key, int mask) {
		JMenuItem menuItem = new JMenuItem(name, key);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(key, mask));
		menuItem.addActionListener(menuListener);
		menuItem.setActionCommand(name.toLowerCase().replace(' ', '_'));
		return menuItem;
	}

	private static JMenuItem newMenuItem(String name, MenuListener menuListener, int key) {
		return newMenuItem(name, menuListener, key, 2);
	}

	private static JMenuItem newMenuItem(String name, MenuListener menuListener) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(menuListener);
		menuItem.setActionCommand(name.toLowerCase().replace(' ', '_'));
		return menuItem;
	}
}
