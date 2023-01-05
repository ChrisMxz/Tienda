package com.david.tienda.util;

import java.sql.Connection;
import java.util.Optional;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtils {
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	// test
	public static boolean verificasesion() {
		HttpServletRequest request = (HttpServletRequest) getRequest();
		HttpSession session = request.getSession(false);
		if (session != null) {
			return true;
		}
		return false;
	}

	public static FacesContext MsgFacesContext() { // Me ayuda a obtner el contexto actual para agregar un mensaje FLASH
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		return facesContext;
	}

	public static Optional<String> getUsername(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if (username != null) {
			return Optional.of(username);
		}
		return Optional.empty();
	}

	public static Optional<Boolean> getBandera(HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean bandera = (boolean) session.getAttribute("bandera");
		if (bandera != false) {
			return Optional.of(bandera);
		}
		return Optional.empty();

	}

	public static Connection getconexion() { // para pool
		return (Connection) SessionUtils.getRequest().getAttribute("conn");
	}
}
