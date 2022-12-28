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
import com.david.tienda.util.UsuarioXml;

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
	private int filtro;
	private int limite;
	private String textoBuscar;

	// metodos
	@PostConstruct
	public void inicia() {
		servicioUsuario = new ServicioUsuarioImpl();
		bandera = false;
		limite = 100;
		filtro = 1;
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Verifica tus datos"));
			PrimeFaces.current().ajax().update(":usuarios");
		}
	}

	public void eliminar() {
		servicioUsuario.eliminar(usuario);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado"));
		PrimeFaces.current().ajax().update(":usuarios:messages");
		listar();
	}

	public void buscar() {
		listaUsuarios = servicioUsuario.listarPor(filtro, textoBuscar, limite);
	}

	public void estableceLimite() {
		String msg = "Limite establecido " + limite + " registros ";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":usuarios:messages", ":opciones:menu-opciones");
		buscar();
	}

	public void validaUsuario() {

		Optional<Usuario> u = java.util.Optional.empty();
		u = Optional.ofNullable(servicioUsuario.porUsername(usuario.getUsername()));

		if (u.isPresent() && (usuario.getIdUsuario() != u.get().getIdUsuario())) {
			bandera = true;

		} else {
			bandera = false;
		}

	}

	public void validaRFC() {

		Optional<Usuario> u = java.util.Optional.empty();
		u = Optional.ofNullable(servicioUsuario.porRFC(usuario.getRfc()));

		if (u.isPresent() && (usuario.getIdUsuario() != u.get().getIdUsuario())) {
			bandera = true;

		} else {
			bandera = false;
		}

	}

	public void exportar() {
		String msg = "Exportado";
		UsuarioXml xml = new UsuarioXml();
		xml.setU(usuario);
		xml.crearXML();

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":usuarios");
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

	public int getFiltro() {
		return filtro;
	}

	public void setFiltro(int filtro) {
		this.filtro = filtro;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

}
