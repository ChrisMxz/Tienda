package com.david.tienda.beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.david.tienda.entidades.Usuario;
import com.david.tienda.servicios.ServicioUsuario;
import com.david.tienda.servicios.ServicioUsuarioImpl;
import com.david.tienda.util.SessionUtils;

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

				HttpSession session = (HttpSession) SessionUtils.getRequest().getSession();
				session.setAttribute("username", user);
				session.setAttribute("bandera", true);
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

	public String logout() {
		System.out.println(">logout");
		Optional<String> username = SessionUtils.getUsername(SessionUtils.getRequest());
		if (username.isPresent()) {
			HttpSession session = SessionUtils.getRequest().getSession();
			session.removeAttribute("username");
			session.removeAttribute("bandera");

			session.invalidate();
		}
		return "/index.xhtml?faces-redirect=true";
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
