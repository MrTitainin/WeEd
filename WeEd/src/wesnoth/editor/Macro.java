package wesnoth.editor;

import javax.xml.bind.annotation.XmlAttribute;

public class Macro {
	@XmlAttribute
	public String name;
	@XmlAttribute
	public int context;
	@XmlAttribute
	public KeyData[] entries;
	
}
