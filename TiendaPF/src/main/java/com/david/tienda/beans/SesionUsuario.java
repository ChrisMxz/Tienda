package com.david.tienda.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.david.tienda.entidades.Pedido;
import com.david.tienda.entidades.Usuario;
import com.david.tienda.servicios.ServicioPedido;
import com.david.tienda.servicios.ServicioPedidoImpl;

@ManagedBean
@SessionScoped
public class SesionUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private boolean bandera;
	private Pedido pedido;

	@PostConstruct
	public void inicia() {
		System.out.println("Inicia beanSesion");
		this.pedido = new Pedido();
		bandera = false;
	}

	@PreDestroy
	public void termina() {
		System.out.println("termina beanSesion");
		bandera = false;
		usuario = null;
		pedido = null;
	}

	// solo para el usuario logeado
	public void nuevoPedido() {
		if (this.pedido != null) {
			this.pedido = new Pedido();
			ServicioPedido servicio = new ServicioPedidoImpl();
			pedido.setCliente(usuario);
			servicio.guardar(pedido);
		}

	}

	// solo para el usuario logeado
	public void nuevoPedidoUsuario(Usuario u) {
		if (this.pedido != null) {
			this.pedido = new Pedido();
			ServicioPedido servicio = new ServicioPedidoImpl();
			pedido.setCliente(u);
			servicio.guardar(pedido);
		}

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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}
