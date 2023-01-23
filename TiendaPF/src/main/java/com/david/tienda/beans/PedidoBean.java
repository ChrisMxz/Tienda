package com.david.tienda.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Factura;
import com.david.tienda.entidades.Item;
import com.david.tienda.entidades.Pedido;
import com.david.tienda.servicios.ServicioPedido;
import com.david.tienda.servicios.ServicioPedidoImpl;
import com.david.tienda.util.MensajeGrowl;
import com.david.tienda.util.ToXML;

@ManagedBean
@ViewScoped
public class PedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sesionUsuario}")
	private SesionUsuario sesionUsuario;

	@ManagedProperty(value = "#{itemBean}")
	private ItemBean itemBean;

	@ManagedProperty(value = "#{facturaBean}")
	private FacturaBean facturaBean;

	private ServicioPedido servicioPedido;

	private boolean editable;

	// metodos
	@PostConstruct
	public void inicia() {
		servicioPedido = new ServicioPedidoImpl();
		editable = true;
		verificaEstatus();
	}

	public void nuevo() throws IOException {
		Pedido pedido = new Pedido();
		sesionUsuario.setPedido(pedido);
		sesionUsuario.getPedido().setEstatus("pendiente");
		sesionUsuario.getPedido().setIva(0.16);
		editable = true;
		if (sesionUsuario.getUsuario().getNivel() == 1) {
			sesionUsuario.getPedido().setCliente(sesionUsuario.getUsuario());
			guardar();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../carrito/formularioPedido.xhtml");
		} else {
			PrimeFaces.current().executeScript("PF('dialogoCrearPedidoForm').show()");
		}

	}

	public void guardar() {
		contarProductos();
		calcularTotal();
		servicioPedido.guardar(sesionUsuario.getPedido());
	}

	public void btnGuardarPedido() {
		guardar();

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../carrito/formularioPedido.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void eliminar() {
		try {
			for (Item i : sesionUsuario.getPedido().getListaItems()) {
				itemBean.setItem(i);
				itemBean.eliminar();
			}

			servicioPedido.eliminar(sesionUsuario.getPedido());
			MensajeGrowl.msgInformacion("Pedido", "Eliminado");

		} catch (Exception e) {
			MensajeGrowl.msgError("Error Eliminar", "A ocurrido un error al eliminar el pedido");
		}

	}

	public void contarProductos() {
		int cuenta = 0;
		for (Item i : sesionUsuario.getPedido().getListaItems()) {
			cuenta = cuenta + i.getCantidad();
		}
		sesionUsuario.getPedido().setCantidadProductos(cuenta);
	}

	public void calcularTotal() {
		double cuenta = 0;
		for (Item i : sesionUsuario.getPedido().getListaItems()) {
			cuenta = cuenta + i.getSubTotal();
		}
		sesionUsuario.getPedido().setSubTotal(cuenta);
		sesionUsuario.getPedido().setTotal(cuenta + (cuenta * sesionUsuario.getPedido().getIva()));
	}

	public void verificaEstatus() {

		if (sesionUsuario.getPedido().getIdPedido() != null) {
			if (sesionUsuario.getPedido().getEstatus().equalsIgnoreCase("hecho"))
				editable = false;
			else
				editable = true;
		} else {
			editable = true;
		}
	}

	public void btnEliminar() {
		eliminar();
		Pedido p = new Pedido();
		sesionUsuario.setPedido(p);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../pedidos/pedidos.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void contextItemVerPedido(Pedido x) {
		verificaEstatus();
	}

	public void contextUsuarioNuevoPedido() throws IOException {
		nuevo();
	}

	public void btnEliminarItem(Item x) {
		itemBean.setItem(x);
		itemBean.eliminar();
		guardar();
	}

	public void btnAgregarItem() {
		itemBean.guardar();
		guardar();
	}

	public void pagar() {
		sesionUsuario.getPedido().setEstatus("hecho");
		guardar();
		verificaEstatus();
		MensajeGrowl.msgInformacion("Pago", "Aplicado correctamente");
		PrimeFaces.current().ajax().update(":formulario-pedido", ":opciones", ":detalles-pedidos");
		PrimeFaces.current().executeScript("PF('dialogoPagoForm').hide()");
	}

	public void devCambiaEstatus() {
		guardar();
		verificaEstatus();
		PrimeFaces.current().ajax().update(":formulario-pedido", ":opciones", ":detalles-pedidos");
		MensajeGrowl.msgInformacion("Estatus", sesionUsuario.getPedido().getEstatus());
	}

	public void opcionGenerarFactura() {

		try {
			Factura f = facturaBean.buscar(sesionUsuario.getPedido().getIdPedido());
			facturaBean.nuevo();

			if (f != null) // verificamos si existe
				facturaBean.setFactura(f);
			else // no existe
				facturaBean.getFactura().setPedido(sesionUsuario.getPedido());

			PrimeFaces.current().executeScript("PF('dialogoFacturasForm').show()");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			MensajeGrowl.msgError("Error Factura", "Al consultar factura por id pedido");
		}

	}

	public void exportarPedidoXML() {
		try {
			ToXML.convierte(sesionUsuario.getPedido());
			ToXML.descarga("pedido" + sesionUsuario.getPedido().getIdPedido());
		} catch (Exception e) {
			MensajeGrowl.msgError("Error", "No se pudo descargar el archivo");
		}
	}

	// Getters an setters

	public SesionUsuario getSesionUsuario() {
		return sesionUsuario;
	}

	public void setSesionUsuario(SesionUsuario sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public ItemBean getItemBean() {
		return itemBean;
	}

	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}

	public FacturaBean getFacturaBean() {
		return facturaBean;
	}

	public void setFacturaBean(FacturaBean facturaBean) {
		this.facturaBean = facturaBean;
	}

}
