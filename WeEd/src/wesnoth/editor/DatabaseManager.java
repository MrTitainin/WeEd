package wesnoth.editor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class DatabaseManager {
	public static String databasePath = "database.mem";
	private static JAXBContext macro;
	private static JAXBContext tag;
	public static void readDatabase() throws JAXBException{
		Unmarshaller database = macro.createUnmarshaller();
	}
	public static void updateDatabase() throws JAXBException{
		Marshaller database = tag.createMarshaller();
	}
	public static void bindContext() {
		try {
			macro = JAXBContext.newInstance(Macro.class);
			tag = JAXBContext.newInstance(Macro.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
