package wesnoth.editor;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Tag implements Listconvertable{
	/*public class Category{
		String header;
		int contextID;
		public Category(String header,int contextID) {
			this.header=header;
			this.contextID=contextID;
		}
	}*/
	@XmlAttribute(name="tag")
	public String name;
	@XmlAttribute
	public int context;
	@XmlElements(@XmlElement)
	public ArrayList<KeyData> keys = new ArrayList<KeyData>();
	public Tag() {
		
	}
	public Tag(String name,KeyData[] keys) {
		this.name=name;
		this.keys.ensureCapacity(keys.length);
		for(KeyData k:keys) this.keys.add(k);
	}
	//public ArrayList<Category> categories = new ArrayList<Category>(); 
	@Override
	public String toList() {
		return name;
	}
}
