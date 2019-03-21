package wesnoth.editor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Macro implements Listconvertable{
	@XmlAttribute
	public String name;
	@XmlAttribute
	public int context;
	@XmlElements(@XmlElement(name="entry"))
	public KeyData[] entries;
	@Override
	public String toList() {
		return name;
	}
	
}
