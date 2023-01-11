package com.david.tienda.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Item;
import com.david.tienda.entidades.Pedido;
import com.david.tienda.servicios.ServicioPedido;
import com.david.tienda.servicios.ServicioPedidoImpl;

@ManagedBean
@ViewScoped
public class PedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sesionUsuario}")
	private SesionUsuario sesionUsuario;

	private Pedido pedido;
	private List<Pedido> listaPedidos;
	private ServicioPedido servicioPedido;
	private boolean bandera;
	private int filtro;
	private int limite;
	private boolean orden;
	private LocalDateTime fecha;
	private String estatus;
	private String textoBuscar;
	private boolean formularioActivo;
	private boolean editable;

	// metodos
	@PostConstruct
	public void inicia() {
		servicioPedido = new ServicioPedidoImpl();
		bandera = false;
		orden = true; // ascendente
		limite = 100;
		filtro = 1;
		fecha = null;
		estatus = null;
		editable = true;
		formularioActivo = false;
		listar();
	}

	public void nuevo() {
		pedido = new Pedido();
		editable = true;
		if (sesionUsuario.getUsuario().getNivel() == 1) {
			pedido.setCliente(sesionUsuario.getUsuario());
			guardar();
		}
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
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Refrescado"));
		// textoBuscar=null;
		PrimeFaces.current().ajax().update(":messages", ":opciones", ":Pedidos");
	}

	public void guardar() {

		if (!bandera) {
			String msg = "Pedido Guardado";
			if (pedido.getIdPedido() != null)
				msg = "Pedido Actualizado";

			contarProductos();
			calcularSubtotal();
			pedido.setEstatus("pendiente");
			servicioPedido.guardar(pedido);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
			PrimeFaces.current().ajax().update(":messages");
			PrimeFaces.current().executeScript("PF('dialogoPedidosForm').hide()");
			listar();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Verifica tus datos"));
			PrimeFaces.current().ajax().update(":messages");
		}
	}

	public void eliminar() {
		servicioPedido.eliminar(pedido);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado"));
		PrimeFaces.current().ajax().update(":messages");
		listar();
	}

	public void buscar() {
		if (sesionUsuario.getUsuario().getNivel() == 1) {
			filtro = 2; // por id cliente
			textoBuscar = sesionUsuario.getUsuario().getIdUsuario().toString();
		}
		listaPedidos = servicioPedido.filtrarPor(filtro, estatus, fecha, textoBuscar, limite, orden);
	}

	public void estableceLimite() {
		String msg = "Limite establecido " + limite + " registros ";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages", ":opciones");
		buscar();
	}

	public void estableceOrden() {
		String msg = "Orden ascendente ";
		if (!orden)
			msg = "Orden descendente ";
		FacesContext.getCurrentInstance().addMessage("Mostrando lista", new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages", ":opciones");
		buscar();
	}

	public void contarProductos() {
		int cuenta = 0;
		for (Item i : pedido.getListaItems()) {
			cuenta = cuenta + i.getCantidad();
		}
		pedido.setCantidadProductos(cuenta);
	}

	public void calcularSubtotal() {
		double cuenta = 0;
		for (Item i : pedido.getListaItems()) {
			cuenta = cuenta + i.getSubTotal();
		}
		pedido.setSubTotal(cuenta);
		pedido.setTotal(cuenta + (cuenta * pedido.getIva()));
	}

	public void agregarItemaLista(Item x) {
		this.pedido.getListaItems().add(x);
	}

	public void verificaEstatus() {

		if (pedido.getEstatus().equalsIgnoreCase("hecho"))
			editable = false;
		else
			editable = true;
	}

	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
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

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Pedido getpedido() {
		return pedido;
	}

	public void setpedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public SesionUsuario getSesionUsuario() {
		return sesionUsuario;
	}

	public void setSesionUsuario(SesionUsuario sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}

	public boolean isFormularioActivo() {
		return formularioActivo;
	}

	public void setFormularioActivo(boolean formularioActivo) {
		this.formularioActivo = formularioActivo;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}
