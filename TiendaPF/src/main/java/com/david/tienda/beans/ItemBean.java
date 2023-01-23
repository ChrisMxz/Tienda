package com.david.tienda.beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Item;
import com.david.tienda.entidades.Producto;
import com.david.tienda.servicios.ServicioItem;
import com.david.tienda.servicios.ServicioItemImpl;
import com.david.tienda.servicios.ServicioProducto;
import com.david.tienda.servicios.ServicioProductoImpl;
import com.david.tienda.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class ItemBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sesionUsuario}")
	private SesionUsuario sesionUsuario;
	private Item item;
	private ServicioItem servicioItem;
	private int cantidad;
	private double total;

	@PostConstruct
	public void iniciar() {
		servicioItem = new ServicioItemImpl();
		nuevo();
	}

	public void agregarCarrito() {
		guardar();
	}

	public void nuevo() {
		item = new Item();
		item.setIdPedido(sesionUsuario.getPedido().getIdPedido());
		cantidad = 0;
		total = 0;
	}

	public void guardar() {
		String msg = "";
		int num;

		if (item.getIdPedido() == null) {
			msg = "Item sin pedido asociado";
			return;
		}

		if (item.getIdItem() == null) {// no encontro el item
			msg = "Guardado";
			verificaItemExistente();
			num = cantidad;// el numero de prooductos que se quitaran del stock
		} else {
			Optional<Item> busqueda = servicioItem.porId(item.getIdItem());
			num = cantidad - busqueda.get().getCantidad(); // calculando la diferencia
			item.setCantidad(cantidad);
			item.setSubTotal(total);
			msg = "Actualizado";
		}
		modificarCantidadProducto(num);
		servicioItem.guardar(item);
		cantidad = 0;
		total = 0;
		MensajeGrowl.msgInformacion(msg, "Item " + msg);
		PrimeFaces.current().executeScript("PF('dialogoItemsForm').hide()");
	}

	public void verificaItemExistente() {
		Item x = servicioItem.porPedidoProducto(item.getIdPedido(), item.getProducto().getIdProducto());
		if (x != null) {
			item = x;
			item.setCantidad(item.getCantidad() + cantidad);
			item.setSubTotal(item.getCantidad() * item.getProducto().getPrecio());
		} else {
			item.setCantidad(cantidad);
			item.setSubTotal(cantidad * item.getProducto().getPrecio());
		}

	}

	public void eliminar() {
		modificarCantidadProducto(-item.getCantidad());
		servicioItem.eliminar(item);
	}

	public void calcularSubtotal() {
		total = cantidad * item.getProducto().getPrecio();
	}

	public int obtenerCantidad() {
		return item.getCantidad() + item.getProducto().getCantidad();
	}

	public void modificarCantidadProducto(int valor) {
		ServicioProducto servicio = new ServicioProductoImpl();
		Optional<Producto> p = servicio.porId(item.getProducto());
		int stock;
		if (p.isPresent()) {
			stock = p.get().getCantidad();
			p.get().setCantidad(stock - valor);
			servicio.guardar(p.get());
			item.setProducto(p.get());
		}

	}

	public void cargaCantidad() {
		cantidad = item.getCantidad();
		total = item.getSubTotal();
	}

	public void btnEditar(Item x) {
		this.item = x;
		cargaCantidad();
		PrimeFaces.current().resetInputs(":formulario-items");
		PrimeFaces.current().executeScript("PF('dialogoItemsForm').show()");
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public SesionUsuario getSesionUsuario() {
		return sesionUsuario;
	}

	public void setSesionUsuario(SesionUsuario sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}
	
	

}
