package wesnoth.editor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

public class TagList extends JFrame implements ListSelectionListener,WindowListener{
	private static final long serialVersionUID = 1L;
	private static String[] columnNames = {"Key","Value type"};
	private JScrollPane keyListPane;
	private JScrollPane tagListPane;
	private JTable keyList;
	private JSplitPane mainPane;
	private JList<String> tagList;
	private ArrayList<? extends Listconvertable> source;
	
	public TagList(ArrayList<? extends Listconvertable> tagSource) {
		DatabaseManager.printDatabase();
		source=tagSource;
		String[] tags = new String[tagSource.size()];
		for(int ii=0;ii<tags.length;ii++) tags[ii]=tagSource.get(ii).toList();
		setAll(tags);
	}
	
	private void setAll(String[] tagData){
		keyList = new JTable();
		tagList = new JList<String>(tagData);
		tagList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tagList.getSelectionModel().addListSelectionListener(this);
		
		keyListPane = new JScrollPane(keyList);
		tagListPane = new JScrollPane(tagList);
		mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tagListPane,keyListPane);
		add(mainPane);
		pack();
		addWindowListener(this);
		setVisible(true);
	}
	
	private void assignKeyList(Tag t) {
		DefaultTableModel tm = new DefaultTableModel();
		for(String cm:columnNames) tm.addColumn(cm);
		for(KeyData entry:t.keys) tm.addRow(entry.toArray());
		keyList.setModel(tm);
		revalidate();
	}
	
	private void assignKeyList(Macro m) {
		DefaultTableModel tm = new DefaultTableModel();
		for(String cm:columnNames) tm.addColumn(cm);
		for(KeyData entry:m.entries) tm.addRow(entry.toArray());
		keyList.setModel(tm);
		revalidate();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			try {
				Listconvertable c = source.get(tagList.getSelectedIndex());
				if(c.getClass().equals(Macro.class)) assignKeyList((Macro) c);
				else if(c.getClass().equals(Tag.class)) assignKeyList((Tag) c);
			} catch (IndexOutOfBoundsException ex){
				return;
			}
			revalidate();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			DatabaseManager.updateDatabase();
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
}
