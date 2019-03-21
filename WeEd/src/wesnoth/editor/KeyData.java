package wesnoth.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class KeyData{
	@XmlAttribute
	private String key;
	@XmlAttribute
	private byte type;
	@XmlAttribute
	private String def;
	@XmlElements(@XmlElement(name="value"))
	private LinkedList<String> possibleValues;
	@XmlAttribute
	private boolean translatable;
	
	protected static final byte BOOLEAN = 0;
	protected static final byte KEY = 1;
	protected static final byte OPTIONS = 2;
	protected static final byte INTEGER = 10;
	protected static final byte FLOAT = 11;
	protected static final byte COORDINATES = 12;
	protected static final byte RANGE = 13;
	protected static final byte STRING = 20;
	protected static final byte LONG = 21;
	protected static final byte FILE = 22;
	protected static final byte SEPARATOR = 23;
	protected static final byte SPECIAL = 19;
	
	KeyData(String key, byte type, LinkedList<String> possibleValues, String def, boolean translatable)
	{
		this.key = key;
		this.type = type;
		this.def = def;
		this.possibleValues = possibleValues;
		this.translatable = translatable;
	}
	
	KeyData(String k, byte t, LinkedList<String> p, String d) {
		this(k, t, p, d, false);
	}
	
	KeyData(String k, byte t, LinkedList<String> p) {
		this(k, t, p, null, false);
	}
	
	KeyData(String k, byte t, boolean tr) {
		this(k, t, null, null, tr);
	}
	
	KeyData(String k, byte t, String d) {
		this(k, t, null, d, false);
	}
	
	KeyData(String k, byte t) {
		this(k, t, null, null, false);
	}
	
	public JComponent getComponent(String data) {
  		switch (this.type) {
  		case 10:
  			JSpinner spinner = new JSpinner();
  			try {
  				spinner.setValue(Integer.valueOf(Integer.parseInt(data)));
  			} catch (Exception e) {
  				if (this.def != null)
  					spinner.setValue(Integer.valueOf(Integer.parseInt(this.def.toString())));
  				else {
  					spinner.setValue(Integer.valueOf(0));
  				}
  			}
  			return spinner;
  		case 21:
  			JTextArea text = new JTextArea();
  			text.setText(data);
  			text.setLineWrap(true);
  			text.setWrapStyleWord(true);
  			JScrollPane scroll = new JScrollPane(text, 20, 31);
  			return scroll;
  		case 1:
  		case 20:
  		case 22:
  			JTextField field = new JTextField();
  			field.setText(data);
  			return field;
  		case 0:
  			JCheckBox tick = new JCheckBox();
  			tick.setSelected(parseBoolean(data));
  			return tick;
  		case 2:
  			JComboBox<String> combo = new JComboBox<String>();
  			combo.addItem(this.def);
  			
  			String[] list = (String[])this.possibleValues.toArray(new String[0]);
  			for (String s : list) {
  				combo.addItem(s);
  				if ((data != null) && (s.equals(data.toLowerCase()))) {
  					combo.setSelectedItem(s);
  				}
  			}
  			return combo;
  		case 23:
  			JLabel seplabel = new JLabel(key);
  			return seplabel;
  		case 3:
  		case 4:
  		case 5:
  		case 6:
  		case 7:
  		case 8:
  		case 9:
  		case 11:
  		case 12:
  		case 13:
  		case 14:
  		case 15:
  		case 16:
  		case 17:
  		case 18:
  		case 19: } return null;
  	}
  	
  	public String parseString(String str, BufferedReader br) throws IOException {
  		if (str != null) str = str.trim();
  		else return "";
  		
  		if (this.type == 21) {
  			String line = str;
  			while (!terminated(line)) {
  				line = br.readLine();
  				str = str + line;
  			}
  		} else {
  			if ((str != null) && (this.translatable) && (str.startsWith("_"))) {
  				System.out.println("Translatable found");
  				str = str.substring(1).trim();
  			}
  			
  			if ((str != null) && (str.startsWith("\""))) {
  				str = str.substring(1, str.lastIndexOf("\""));
  			}
  			
  		}
  		
  		System.out.println("String parsed: " + str);
  		return str;
  	}
  	
  	private boolean terminated(String str) {
  		String[] parts = str.split("/+");
  		
  		String last = parts[(parts.length - 1)].trim();
  		
  		if (last.isEmpty()) {
  			return false;
  		}
  		if (last.startsWith("_")) {
  			last = last.substring(1).trim();
  		}
  		if (last.startsWith("\"")) {
  			return last.endsWith("\"");
  		}
  		return true;
  	}
  	
  	private boolean parseBoolean(String str) {
  		if (str == null) {
  			return false;
  		}
  		if ((Boolean.parseBoolean(str)) || (str.startsWith("yes"))) {
  			return true;
  		}
  		return false;
  	}
  	
  	protected boolean isUniversal() {
  		return this.key == null;
  	}

  	protected boolean isBig() {
  		return this.type == 21;
  	}
  	
  	protected boolean getTranslatable() {
  		return this.translatable;
  	}
  	
  	protected boolean isEnquoted() {
  		return this.type >= 20;
  	}
  	
  	protected void changeKey(String newKey) {
  		this.key = newKey;
  	}
  	
  	public String toString() {
  		return this.key;
  	}
  	public boolean isSeparator(){
  		if (type==SEPARATOR) return true;
  		else return false;
  	}
  	public String getKey(){
  		return key;
  	}
  	public void setKey(String key) {
  		this.key=key;
  	}
  	public class TypeMapping{
  		public byte id;
  		public String name;
  		public TypeMapping(byte id,String name) {
  			this.id=id;
  			this.name=name;
  		}
  	}
  	public Object[] toArray() {
  		return new Object[] {key,type};
  	}
  	public TypeMapping[] getTypeNames() {
  		return new TypeMapping[]{
  				new TypeMapping(INTEGER,"number (integer)"),
  				new TypeMapping(FLOAT,"number (float)"),
  				new TypeMapping(LONG,"number (long)"),
  				new TypeMapping(BOOLEAN,"yes/no (boolean)"),
  				new TypeMapping(KEY,"keyword (string)"),
  				new TypeMapping(OPTIONS,"keyword (from list)"),
  				new TypeMapping(STRING,"text (string)"),
  				new TypeMapping(FILE,"path (string)"),
  				new TypeMapping(COORDINATES,"special (string)"),
  				new TypeMapping(RANGE,"special (string)"),
  				new TypeMapping(SPECIAL,"special (string)")
  		};
  	}
}