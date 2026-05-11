package com.symphonize.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.symphonize.dto.EmployeeDetailsDto;

public class XmlUtil {
	
	// To convert java object to xml string
	public  static String convertToXml(Object obj) {

		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// To remove XML header
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);

			return writer.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// To convert XML String to Java Object
	public static EmployeeDetailsDto convertXmlToObject(String xml) {

		try {

			JAXBContext context = JAXBContext.newInstance(EmployeeDetailsDto.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			return (EmployeeDetailsDto) unmarshaller.unmarshal(reader);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
