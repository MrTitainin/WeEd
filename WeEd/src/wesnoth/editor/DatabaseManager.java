package wesnoth.editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
		
	}
	public DatabaseManager(boolean b) {
		try {
			readDatabase();
		} catch (JAXBException e) { e.printStackTrace(); }
	}
	
	public static void readDatabase() throws JAXBException{
		if(ctx==null)bindContext();
		Unmarshaller databaseum = ctx.createUnmarshaller();
		database = (DatabaseManager) databaseum.unmarshal(Paths.get("./"+databasePath).toFile());
		printDatabase();
	}
	public static void updateDatabase() throws JAXBException{
		if(ctx==null)bindContext();
		Marshaller databasem = ctx.createMarshaller();
		databasem.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		Path p = Paths.get("./"+databasePath);
		try {
			Files.createFile(p);
		} catch (IOException e) {}
		databasem.marshal(database, p.toFile());
	}
	public static void bindContext() throws JAXBException{
		ctx = JAXBContext.newInstance(DatabaseManager.class);
	}
	public void importOldDatabase(HashMap<String, KeyData[]> oldFormat) {
		tags.ensureCapacity(oldFormat.size());
		for(Entry<String,KeyData[]> c:oldFormat.entrySet()) {
			tags.add(new Tag(c.getKey(),c.getValue()));
		}
	}
	public static void printDatabase() {
		try {
			if(ctx==null)bindContext();
			Marshaller databasem = ctx.createMarshaller();
			databasem.marshal(database,System.out);
		} catch (JAXBException e) { e.printStackTrace(); }
	}
}
