package com.david.tienda.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.david.tienda.entidades.Usuario;

@ManagedBean
@SessionScoped
public class SesionUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	@PostConstruct
	public void inicia() {
		System.out.println("Inicia beanSesion");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
