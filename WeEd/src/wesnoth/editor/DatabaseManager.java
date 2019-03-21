package wesnoth.editor;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="WMLdatabase")
public class DatabaseManager {
	public static String databasePath = "database.mem";
	
	private static JAXBContext ctx;
	public static DatabaseManager database = new DatabaseManager();
	
	@XmlElements(@XmlElement(name="tag"))
	public ArrayList<Tag> tags = new ArrayList<Tag>();
	@XmlElements(@XmlElement(name="macro"))
	public ArrayList<Macro> macros = new ArrayList<Macro>();
	
	public DatabaseManager() {
		bindContext();
	}
	
	public static void readDatabase() throws JAXBException{
		Unmarshaller databaseum = ctx.createUnmarshaller();
		database = (DatabaseManager) databaseum.unmarshal(new File(DatabaseManager.class.getResource(databasePath).getFile()));
	}
	public static void updateDatabase() throws JAXBException{
		Marshaller databasem = ctx.createMarshaller();
		databasem.marshal(database, new File(DatabaseManager.class.getResource(databasePath).getFile()));
	}
	public static void bindContext() {
		try {
			ctx = JAXBContext.newInstance(DatabaseManager.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
