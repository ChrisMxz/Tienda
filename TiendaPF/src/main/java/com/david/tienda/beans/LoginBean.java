package com.david.tienda.beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.david.tienda.entidades.Usuario;
import com.david.tienda.servicios.ServicioUsuario;
import com.david.tienda.servicios.ServicioUsuarioImpl;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {
	// variables
	private static final long serialVersionUID = 1L;

	private String user;
	private String pass;
	private ServicioUsuario servicioUsuario;

	// metodos

	@PostConstruct
	public void inicia() {
		servicioUsuario = new ServicioUsuarioImpl();
	}

	public String login() {

		try {
			Optional<Usuario> u = servicioUsuario.login(user, pass);
			if (u.isPresent()) {
				SesionUsuario sesion = new SesionUsuario();
				sesion.setUsuario(u.get());

				System.out.println(">Sesion: " + sesion.getUsuario());

				return "/index.xhtml?faces-redirect=true";
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Usuario o contraseña inconrrectos!",
						null);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("msg", msg);

			}

		} catch (Exception e) {
			System.out.println("fallo en login bean: " + e);
		}
		return null;
	}

	public void logout() {
	}

	// getters and setters

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
