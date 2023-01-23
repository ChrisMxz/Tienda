package com.david.tienda.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ToXML {

	public static void convierte(Object x) {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(x.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			marshaller.marshal(x, new FileWriter(
					"C:\\Users\\David\\Documents\\C2K\\TiendaProyecto\\TiendaPF\\src\\main\\webapp\\resources\\docs\\archivo.xml"));

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void descarga(String nombre) {

		File file = new File(
				"C:\\Users\\David\\Documents\\C2K\\TiendaProyecto\\TiendaPF\\src\\main\\webapp\\resources\\docs\\archivo.xml");
		String fileName = nombre + ".xml";
		String contentType = "text/xml";
		int contentLength = (int) file.length();

		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();

		response.reset();
		response.setContentType(contentType);
		response.setContentLength(contentLength);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		OutputStream output;
		try {
			output = response.getOutputStream();
			Files.copy(file.toPath(), output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fc.responseComplete();
	}

}
