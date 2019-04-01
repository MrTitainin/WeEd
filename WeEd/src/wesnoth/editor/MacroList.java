package wesnoth.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComboBox;
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

public class MacroList extends JFrame implements ListSelectionListener,WindowListener{
	private static final long serialVersionUID = 1L;
	private static String[] columnNames = {"Key","Value type"};
	private JScrollPane keyListPane;
	private JScrollPane macroListPane;
	private JTable keyList;
	private JSplitPane mainPane;
	private JList<String> tagList;
	private DefaultTableModel tm = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;
		@Override
		public Class<?> getColumnClass(int col) {
			if(col==1) return JComboBox.class;
			else return String.class;
		}
	};
	
	public MacroList() {
		//DatabaseManager.printDatabase();
		String[] tags = new String[DatabaseManager.database.macros.size()];
		for(int ii=0;ii<tags.length;ii++) tags[ii]=DatabaseManager.database.macros.get(ii).name;
		setAll(tags);
	}
	
	private void setAll(String[] tagData){
		keyList = new JTable();
		keyList.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

	                int row = keyList.getSelectedRow();
	                int column = keyList.getSelectedColumn();
	                Macro act =DatabaseManager.database.macros.get(row);
	                switch(column) {
	                case 0:
                		Macro m =(Macro) act;
                		m.entries[row].changeKey(keyList.getValueAt(row, column).toString());
	                	if (keyList.isEditing()) keyList.getCellEditor().stopCellEditing();
	                	break;
	                case 1:
	                	break;
	                }
	                //String resul = table.getValueAt(row, column).toString();
	            }
	        }
	    });
		tagList = new JList<String>(tagData);
		tagList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tagList.getSelectionModel().addListSelectionListener(this);
		
		keyListPane = new JScrollPane(keyList);
		macroListPane = new JScrollPane(tagList);
		mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,macroListPane,keyListPane);
		add(mainPane);
		pack();
		addWindowListener(this);
		setVisible(true);
	}
	
	private void assignKeyList(Macro m) {
		if(tm.getColumnCount()!=2) for(String cm:columnNames) tm.addColumn(cm);
		tm.setRowCount(0);
		for(KeyData entry:m.entries) tm.addRow(entry.toArray());
		keyList.setModel(tm);
		revalidate();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			try {
				assignKeyList(DatabaseManager.database.macros.get(tagList.getSelectedIndex()));
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
	
	/*private class TableClickHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			keyList.rowAtPoint(e.getPoint());
			keyList.columnAtPoint(e.getPoint());
		}
	}*/
}
