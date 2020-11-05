package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import models.Channel;

public class XMLManager {

	private static String FILE="Chanel.xml";

	public static boolean SetChanel(Channel c){
		boolean valid=false;
		JAXBContext context;
		FileWriter fw;
		BufferedWriter bw;
		Marshaller ms;
		try {
			fw=new FileWriter(FILE);
			bw=new BufferedWriter(fw);
			try {
				context=JAXBContext.newInstance(Channel.class);
				ms=context.createMarshaller();
				ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				ms.marshal(c, bw);
				bw.close();
				fw.close();
				valid=true;
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return valid;
	}

	public static Channel GetChannel(){
		Channel c=null;
		JAXBContext context;
		Unmarshaller us;
		try {
			context=JAXBContext.newInstance(Channel.class);
			us=context.createUnmarshaller();
			c=(Channel) us.unmarshal(new File(FILE));
		} catch (JAXBException e) {
			e.printStackTrace();
			c=new Channel();
			SetChanel(c);
		}
		return c;
	}

}
