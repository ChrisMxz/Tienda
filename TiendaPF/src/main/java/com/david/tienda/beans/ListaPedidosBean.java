package com.david.tienda.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Pedido;
import com.david.tienda.servicios.ServicioPedido;
import com.david.tienda.servicios.ServicioPedidoImpl;
import com.david.tienda.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class ListaPedidosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sesionUsuario}")
	private SesionUsuario sesionUsuario;

	@ManagedProperty(value = "#{pedidoBean}")
	private PedidoBean pedidoBean;

	private Pedido pedidoSeleccionado;
	private List<Pedido> listaPedidos;
	private ServicioPedido servicioPedido;

	private int filtro;
	private int limite;
	private boolean orden;
	private String estatus;
	private String textoBuscar;

	// metodos
	@PostConstruct
	public void inicia() {
		servicioPedido = new ServicioPedidoImpl();
		listaPedidos = new ArrayList<>();

		orden = false; // descendente
		limite = 100;
		filtro = 1;
		textoBuscar = null;
		listar();
	}

	public void listar() {
		// lista segun lo establecido
		buscar();
	}

	public void listarTodo() {
		listaPedidos = servicioPedido.listarTodo();
	}

	public void refrescar() {
		listar();
		MensajeGrowl.msgInformacion("Actualizado", "Actualizando vista");
		PrimeFaces.current().ajax().update(":opciones", ":pedidos");
	}

	public void buscar() {
		if (sesionUsuario.getUsuario().getNivel() == 1) {
			filtro = 2; // por id cliente
			textoBuscar = sesionUsuario.getUsuario().getIdUsuario().toString();
		}
		listaPedidos.clear();
		listaPedidos = servicioPedido.filtrarPor(filtro, estatus, null, textoBuscar, limite, orden);
	}

	public void estableceLimite() {
		String msg = "Limite establecido " + limite + " registros ";
		MensajeGrowl.msgInformacion("Limite", msg);
		PrimeFaces.current().ajax().update(":opciones", ":pedidos");
		buscar();
	}

	public void estableceOrden() {
		String msg = "Orden ascendente ";
		if (!orden)
			msg = "Orden descendente ";

		MensajeGrowl.msgInformacion("Mostrando lista", msg);
		PrimeFaces.current().ajax().update(":opciones", ":pedidos");
		buscar();
	}

	public void btnVerPedido(Pedido x) {
		sesionUsuario.setPedido(x);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../carrito/formularioPedido.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void btnEliminarPedido(Pedido p) {
		sesionUsuario.setPedido(p);
		pedidoBean.eliminar();
		sesionUsuario.setPedido(null);
		listar();
	}

	// Getters an setters

	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
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

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public SesionUsuario getSesionUsuario() {
		return sesionUsuario;
	}

	public void setSesionUsuario(SesionUsuario sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}

	public Pedido getPedidoSeleccionado() {
		return pedidoSeleccionado;
	}

	public void setPedidoSeleccionado(Pedido pedidoSeleccionado) {
		this.pedidoSeleccionado = pedidoSeleccionado;
	}

	public PedidoBean getPedidoBean() {
		return pedidoBean;
	}

	public void setPedidoBean(PedidoBean pedidoBean) {
		this.pedidoBean = pedidoBean;
	}

}
