package wesnoth.editor;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TagList extends JFrame implements ListSelectionListener{
	private static final long serialVersionUID = 1L;
	private static String[] columnNames = {"Key","Value type"};
	private JScrollPane keyListPane;
	private JScrollPane tagListPane;
	private JTable keyList;
	private JSplitPane mainPane;
	private JList<String> tagList;
	private ArrayList<Listconvertable> source;
	
	public TagList(ArrayList<Listconvertable> tagSource) {
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
		this.add(mainPane);
		setVisible(true);
	}
	
	private void assignKeyList(Tag t) {
		Object[][] data = new Object[t.keys.size()][];
		for(int ii=0;ii<data.length;ii++) data[ii] = t.keys.get(ii).toArray();
		keyList = new JTable(data,columnNames);
	}
	
	private void assignKeyList(Macro m) {
		Object[][] data = new Object[m.entries.length][];
		for(int ii=0;ii<data.length;ii++) data[ii] = m.entries[ii].toArray();
		keyList = new JTable(data,columnNames);
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
		}
	}
}
