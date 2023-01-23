package com.david.tienda.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.Valid;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Usuario;
import com.david.tienda.servicios.ServicioUsuario;
import com.david.tienda.servicios.ServicioUsuarioImpl;
import com.david.tienda.util.MensajeGrowl;
import com.david.tienda.util.ToXML;

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
	private boolean orden;
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
		MensajeGrowl.msgInformacion("Actualizado", "Lista actualizada");
		// textoBuscar=null;
		PrimeFaces.current().ajax().update(":usuarios");
	}

	public void guardar() {
		try {
			if (!bandera) {
				String msg = "Guardado";
				if (usuario.getIdUsuario() != null)
					msg = "Actualizado";

				servicioUsuario.guardar(usuario);

				MensajeGrowl.msgInformacion(msg, "Usuario " + msg);
				PrimeFaces.current().executeScript("PF('dialogoForm').hide()");
				listar();
			} else {
				MensajeGrowl.msgAdvertencia("Verifica", "Verifica tus datos");
				PrimeFaces.current().ajax().update(":usuarios");
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			MensajeGrowl.msgError("Error", "Error al guardar usuario");
		}

	}

	public void eliminar() {
		try {
			servicioUsuario.eliminar(usuario);
			listar();
			MensajeGrowl.msgInformacion("Eliminado", "Usuario Eliminado");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			MensajeGrowl.msgError("Error", "Error al Eliminar usuario");
		}

	}

	public void buscar() {
		listaUsuarios = servicioUsuario.listarPor(filtro, textoBuscar, limite, orden);
	}

	public void estableceLimite() {
		String msg = "Limite establecido " + limite + " registros ";
		MensajeGrowl.msgInformacion(msg, "Hecho");
		PrimeFaces.current().ajax().update(":opciones:menu-opciones");
		buscar();
	}

	public void estableceOrden() {
		String msg = "Orden ascendente ";
		if (!orden)
			msg = "Orden descendente ";
		MensajeGrowl.msgInformacion(msg, "Hecho");
		PrimeFaces.current().ajax().update(":opciones:menu-opciones");
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
		try {
			ToXML.convierte(usuario);
			ToXML.descarga("usuario" + usuario.getIdUsuario());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			MensajeGrowl.msgError("Error", "Error al exportar en XML");
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

	public boolean isOrden() {
		return orden;
	}

	public void setOrden(boolean orden) {
		this.orden = orden;
	}

}
