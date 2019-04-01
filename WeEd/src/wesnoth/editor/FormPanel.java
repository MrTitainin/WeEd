package wesnoth.editor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class FormPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	//private HashMap<String, Tag> keyList;
	//private final int COLUMNS = 2;
	private WMLTreeNode tagNode;
	private LinkedList<JComponent> labels;
	private LinkedList<JComponent> fields;
	private JTextArea macroTextArea;

	FormPanel(){
		setLayout(new GridBagLayout());

		this.labels = new LinkedList<JComponent>();
		this.fields = new LinkedList<JComponent>();
		this.macroTextArea = null;

		//this.keyList = StructureGenerator.constructAttrTable();
		//DatabaseManager.database.importOldDatabase(keyList);
	}

	protected HashMap<String, Tag> getTagList() {
		return DatabaseManager.database.getTagList();
	}

	protected void save() {
		if (this.tagNode == null) {
			return;
		}
		HashMap<String,String> data = new HashMap<String,String>();

		JComponent[] l = (JComponent[])this.labels.toArray(new JComponent[0]);
		JComponent[] f = (JComponent[])this.fields.toArray(new JComponent[0]);

		for (int i = 0; i < l.length; i++) {
			JComponent label = l[i];
			JComponent field = f[i];
			String s1;
			if ((label instanceof JTextField)) {
				JTextField textField = (JTextField)label;
				s1 = textField.getText();
			} else {
				if (!(label instanceof JCheckBox)) continue;
				JCheckBox checkBox = (JCheckBox)label;
				s1 = checkBox.getText();
				if (!checkBox.isSelected())
					continue;
			}
			String s2;
			if ((field instanceof JSpinner)) {
				JSpinner spinner = (JSpinner)field;
				s2 = spinner.getValue().toString();
			}
			else
			{
				if ((field instanceof JScrollPane)) {
					JViewport viewPort = (JViewport)field.getComponent(0);
					JTextArea textArea = (JTextArea)viewPort.getComponent(0);
					s2 = textArea.getText();
				}
				else
				{
					if ((field instanceof JTextField)) {
						JTextField textField = (JTextField)field;
						s2 = textField.getText();
					}
					else
					{
						if ((field instanceof JCheckBox)) {
							JCheckBox checkBox = (JCheckBox)field;
							if (checkBox.isSelected())
								s2 = "true";
							else
								s2 = "false";
						} else {
							if (!(field instanceof JComboBox)) continue;
							@SuppressWarnings("unchecked")
							JComboBox<JComponent> comboBox = (JComboBox<JComponent>)field;
							s2 = comboBox.getSelectedItem().toString();
						}
					}
				}
			}
			
			data.put(s1, s2);
		}
		
		this.tagNode.setMacros(this.macroTextArea.getText());
		this.tagNode.setAttributes(data);
	}

	protected void displayData(WMLTreeNode selection, boolean root){
		removeAll();
		this.labels = new LinkedList<JComponent>();
		this.fields = new LinkedList<JComponent>();

		HashMap<String,String> attributes = selection.getAttributes();
		String tag = selection.toString();
		String macros = selection.getMacros();

		GridBagConstraints fieldConstrains = new GridBagConstraints();
		fieldConstrains.fill = 2;
		fieldConstrains.weightx = 0.5D;
		fieldConstrains.gridx = (fieldConstrains.gridy = 0);
		fieldConstrains.insets = new Insets(5, 10, 5, 10);
		fieldConstrains.anchor = 23;

		if (root) {
			for (KeyData key : getTagList().get("*special").keys) {
				if (key.toString().equals("#textdomain")) {
					addField(attributes, key, fieldConstrains, false);
				}
			}
		}

		if (getTagList().containsKey(tag)) {

			for (KeyData key : getTagList().get(tag).keys) {
				if (key.isUniversal()) {
					String[] universalKeys = (String[])attributes.keySet().toArray(new String[0]);
					for (String universalKey : universalKeys) {
						key.changeKey(universalKey);
						addField(attributes, key, fieldConstrains, true);
						key.changeKey(null);
					}

					break;
				}
				addField(attributes, key, fieldConstrains, false);
			}
		}

		JTextArea macroList = new JTextArea(macros);
		macroList.setLineWrap(true);
		macroList.setWrapStyleWord(true);
		JScrollPane listScroller = new JScrollPane(macroList, 20, 31);
		listScroller.setPreferredSize(new Dimension(20, 60));

		fieldConstrains.fill = 1;
		fieldConstrains.gridwidth = 2;
		fieldConstrains.weightx = (fieldConstrains.weighty = 0.5D);
		fieldConstrains.insets = new Insets(5, 10, 5, 10);
		if (fieldConstrains.gridx > 0) {
			fieldConstrains.gridx = 0;
			fieldConstrains.gridy += 1;
		}
		add(listScroller, fieldConstrains);
		this.macroTextArea = macroList;

		updateUI();

		this.tagNode = selection;
	}

	private void addField(HashMap<String, String> data, KeyData key, GridBagConstraints fieldConstrains, boolean editableKey) {
		String keyString = key.toString();

		boolean found = false;
		String dataString = null;
		if ((data != null) && (data.containsKey(keyString))) {
			dataString = (String)data.get(keyString);
			found = true;
		}

		JPanel panel = new JPanel(new GridBagLayout());
		JComponent field = key.getComponent(dataString);
		JComponent label;
		if (editableKey)
			label = new JTextField(keyString);
		else {
			if(key.isSeparator()) label = new JLabel("");
			else label = new JCheckBox(keyString, found);
		}
		Dimension size = new Dimension(60, 30);
		field.setPreferredSize(size);
		label.setPreferredSize(size);

		GridBagConstraints panelConstrains = new GridBagConstraints();
		panelConstrains.weightx = 0.2D;
		panelConstrains.fill = 2;
		panelConstrains.gridy = 0;
		panelConstrains.ipadx = 4;

		if (key.isBig()||key.isSeparator()) {
			fieldConstrains.gridwidth = 2;
			field.setPreferredSize(new Dimension(60, 90));
			if (fieldConstrains.gridx != 0) {
				fieldConstrains.gridx = 0;
				fieldConstrains.gridy += 1;
			}

		}

		panel.add(label, panelConstrains);
		if (key.isBig()||key.isSeparator()) {
			fieldConstrains.gridwidth = 2;
			if (fieldConstrains.gridx != 0) {
				fieldConstrains.gridx = 1;
				fieldConstrains.gridy += 1;
			}
			panelConstrains.gridy += 1;
		}
		panel.add(field, panelConstrains);
		add(panel, fieldConstrains);

		this.labels.add(label);
		this.fields.add(field);

		prepareConstrains(fieldConstrains, key);
	}

	private void prepareConstrains(GridBagConstraints fieldConstrains, KeyData key) {
		fieldConstrains.gridx += 1;
		fieldConstrains.gridwidth = 1;
		if ((fieldConstrains.gridx >= 2) || (key.isBig()) || (key.isSeparator())) {
			fieldConstrains.gridx = 0;
			fieldConstrains.gridy += 1;
		}
	}
}