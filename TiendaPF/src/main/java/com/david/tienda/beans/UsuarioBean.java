package com.david.tienda.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.Valid;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Usuario;
import com.david.tienda.servicios.ServicioUsuario;
import com.david.tienda.servicios.ServicioUsuarioImpl;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	// variables
	private static final long serialVersionUID = 1L;

	@Valid
	private Usuario usuario;
	private List<Usuario> listaUsuarios;
	private ServicioUsuario servicioUsuario;
	private boolean bandera;
	// metodos
	@PostConstruct
	public void inicia() {
		servicioUsuario = new ServicioUsuarioImpl();
		bandera=false;
		listar();
	}

	public void nuevo() {
		usuario = new Usuario();
	}

	public void listar() {
		// lista segun lo establecido
		listarTodo();
	}

	public void listarTodo() {
		listaUsuarios = servicioUsuario.listarTodo();
	}

	public void refrescar() {
		listar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Refrescado"));
		// textoBuscar=null;
		PrimeFaces.current().ajax().update(":usuarios");
	}

	public void guardar() {
		if (!bandera) {
			String msg = "Guardado";
			if (usuario.getIdUsuario() != null)
				msg = "Actualizado";

			servicioUsuario.guardar(usuario);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
			PrimeFaces.current().ajax().update(":formulario-usuarios:msg");
			PrimeFaces.current().executeScript("PF('dialogoForm').hide()");
			listar();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ya esta registrado el usuario"));
			PrimeFaces.current().ajax().update(":usuarios");
		}
	}

	public void eliminar() {
		servicioUsuario.eliminar(usuario);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado"));
		PrimeFaces.current().ajax().update(":usuarios:messages");
		listar();
	}

	public void validaUsuario() {

		Optional<Usuario> u = java.util.Optional.empty();
		u = Optional.ofNullable(servicioUsuario.porUsername(usuario.getUsername()));

		if (u.isPresent()) {
			bandera = true;
		}else {
			bandera = false;
		}

	}

	// getters and setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}	

}
