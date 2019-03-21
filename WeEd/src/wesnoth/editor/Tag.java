package wesnoth.editor;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Tag {
	public class Category implements Listconvertable{
		String header;
		int contextID;
		public Category(String header,int contextID) {
			this.header=header;
			this.contextID=contextID;
		}
		@Override
		public String toList() {
			return header;
		}
	}
	@XmlAttribute(name="tag")
	public String name;
	@XmlAttribute
	public int context;
	@XmlElements(@XmlElement)
	public ArrayList<KeyData> keys = new ArrayList<KeyData>();
	public ArrayList<Category> categories = new ArrayList<Category>(); 
}
