package com.david.tienda.beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Item;
import com.david.tienda.entidades.Producto;
import com.david.tienda.servicios.ServicioItem;
import com.david.tienda.servicios.ServicioItemImpl;
import com.david.tienda.servicios.ServicioProducto;
import com.david.tienda.servicios.ServicioProductoImpl;

@ManagedBean
@ViewScoped
public class ItemBean implements Serializable {
	private static final long serialVersionUID = 1L;

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
		cantidad = 0;
		total = 0;
	}

	public void guardar() {
		String msg = "";

		if (item.getIdPedido() == null) {
			msg = "Item sin pedido asociado";
			return;
		}

		if (item.getIdItem() == null) {// no encontro el item
			msg = "Item Guardado";
			modificarCantidadProducto(cantidad);
		} else {

			Optional<Item> busqueda = servicioItem.porId(item.getIdItem());
			int dif = cantidad - busqueda.get().getCantidad(); // calculando la diferencia
			modificarCantidadProducto(dif);
			msg = "Item Actualizado";
		}
		item.setCantidad(cantidad);
		item.setSubTotal(total);
		servicioItem.guardar(item);
		cantidad = 0;
		total = 0;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages");
		PrimeFaces.current().executeScript("PF('dialogoItemsForm').hide()");
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

}
