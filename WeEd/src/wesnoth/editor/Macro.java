package wesnoth.editor;

import javax.xml.bind.annotation.XmlAttribute;

public class Macro implements Listconvertable{
	@XmlAttribute
	public String name;
	@XmlAttribute
	public int context;
	@XmlAttribute
	public KeyData[] entries;
	@Override
	public String toList() {
		return name;
	}
	
}
