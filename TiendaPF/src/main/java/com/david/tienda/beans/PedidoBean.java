package com.david.tienda.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Item;
import com.david.tienda.entidades.Pedido;
import com.david.tienda.servicios.ServicioPedido;
import com.david.tienda.servicios.ServicioPedidoImpl;
import com.david.tienda.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class PedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sesionUsuario}")
	private SesionUsuario sesionUsuario;

	@ManagedProperty(value = "#{itemBean}")
	private ItemBean itemBean;

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
		listaPedidos = new ArrayList<>();
		bandera = false;
		orden = false; // descendente
		limite = 100;
		filtro = 1;
		fecha = null;
		editable = true;
		formularioActivo = false;
		textoBuscar = null;
		listar();
	}

	public void nuevo() {
		pedido = new Pedido();
		pedido.setEstatus("pendiente");
		pedido.setIva(0.16);
		editable = true;
		if (sesionUsuario.getUsuario().getNivel() == 1) {
			pedido.setCliente(sesionUsuario.getUsuario());
			guardar();
			cambiaVista();
		} else {
			PrimeFaces.current().executeScript("PF('dialogoCrearPedidoForm').show()");
		}
	}

	public void listar() {
		// lista segun lo establecido
		buscar();
	}

	public void cambiaVista() {
		formularioActivo = !formularioActivo;
		PrimeFaces.current().ajax().update(":formulario-pedido", ":opciones", ":pedidos", ":detalles-pedidos");
	}

	public void listarTodo() {
		listaPedidos = servicioPedido.listarTodo();
	}

	public void refrescar() {
		listar();
		MensajeGrowl.msgInformacion("Actualizado", "Actualizando vista");
		PrimeFaces.current().ajax().update(":formulario-pedido", ":opciones", ":pedidos", ":detalles-pedidos");
	}

	public void guardar() {

		if (!bandera) {
			String msg = "Guardado";
			if (pedido.getIdPedido() != null)
				msg = "Actualizado";

			contarProductos();
			calcularTotal();
			servicioPedido.guardar(pedido);
			MensajeGrowl.msgInformacion("Pedido", msg);
			PrimeFaces.current().executeScript("PF('dialogoPedidosForm').hide()");
			listar();
		} else {
			MensajeGrowl.msgInformacion("Verificar", "Verifica tus datos");
		}
	}

	public void eliminar() {

		for (Item i : pedido.getListaItems()) {
			itemBean.setItem(i);
			itemBean.eliminar();
		}

		servicioPedido.eliminar(pedido);
		MensajeGrowl.msgInformacion("Pedido", "Eliminado");
		listar();
	}

	public void buscar() {
		if (sesionUsuario.getUsuario().getNivel() == 1) {
			filtro = 2; // por id cliente
			textoBuscar = sesionUsuario.getUsuario().getIdUsuario().toString();
		}
		listaPedidos.clear();
		listaPedidos = servicioPedido.filtrarPor(filtro, estatus, fecha, textoBuscar, limite, orden);
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

	public void contarProductos() {
		int cuenta = 0;
		for (Item i : pedido.getListaItems()) {
			cuenta = cuenta + i.getCantidad();
		}
		pedido.setCantidadProductos(cuenta);
	}

	public void calcularTotal() {
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

	public void btnEliminar() {
		eliminar();
		cambiaVista();
	}

	public void contextItemVerPedido() {
		verificaEstatus();
		cambiaVista();
	}

	public void contextUsuarioNuevoPedido() {
		nuevo();
		System.out.println(pedido);
		guardar();
	}

	public void btnEliminarItem() {
		itemBean.eliminar();
		guardar();
	}

	public void btnAgregarItem() {
		itemBean.guardar();
		guardar();
	}

	public void pagar() {
		pedido.setEstatus("hecho");
		guardar();
		verificaEstatus();
		MensajeGrowl.msgInformacion("Pago", "Aplicado correctamente");
		PrimeFaces.current().ajax().update(":formulario-pedido", ":opciones", ":pedidos", ":detalles-pedidos");
	}

	public void devCambiaEstatus() {
		guardar();
		verificaEstatus();
		PrimeFaces.current().ajax().update(":formulario-pedido", ":opciones", ":pedidos", ":detalles-pedidos");
		MensajeGrowl.msgInformacion("Estatus", pedido.getEstatus());
	}

	// Getters an setters

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

	public ItemBean getItemBean() {
		return itemBean;
	}

	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}

}
