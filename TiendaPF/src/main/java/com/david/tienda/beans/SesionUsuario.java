package com.david.tienda.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.david.tienda.entidades.Usuario;

@ManagedBean
@SessionScoped
public class SesionUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private boolean bandera;

	@PostConstruct
	public void inicia() {
		System.out.println("Inicia beanSesion");
		bandera = false;
	}

	@PreDestroy
	public void termina() {
		System.out.println("termina beanSesion");
		bandera = false;
		usuario = null;

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

}
