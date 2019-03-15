package wesnoth.editor;

import javax.xml.bind.annotation.XmlAttribute;

public class Tag {
	@XmlAttribute(name="tag")
	public String name;
	@XmlAttribute
	public int context;
}
