package com.david.tienda.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

public class MensajeGrowl {

	public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		PrimeFaces.current().ajax().update(":messages");
	}

	public static void msgInformacion(String titulo, String detalles) {
		addMessage(FacesMessage.SEVERITY_INFO, titulo, detalles);
	}

	public static void msgAdvertencia(String titulo, String detalles) {
		addMessage(FacesMessage.SEVERITY_WARN, titulo, detalles);
	}

	public static void msgError(String titulo, String detalles) {
		addMessage(FacesMessage.SEVERITY_ERROR, titulo, detalles);
	}

}
