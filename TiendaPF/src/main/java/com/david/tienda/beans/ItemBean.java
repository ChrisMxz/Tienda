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
	// private Item itemSeleccionado;
	private int cantidadProductos;
	private double subTotal;
	private ServicioItem servicioItem;
	private ServicioProducto sproductoProducto;

	@PostConstruct
	public void iniciar() {
		servicioItem = new ServicioItemImpl();
		sproductoProducto = new ServicioProductoImpl();
		nuevo();
	}

	public void calcularSubtotal() {
		double subtotal;
		subtotal = cantidadProductos * item.getProducto().getPrecio();
		this.item.setSubTotal(subtotal);
	}

	public void agregarCarrito() {
		guardar();
	}

	public void nuevo() {
		item = new Item();
		cantidadProductos = 0;
		subTotal = 0.0;
	}

	public void guardar() {
		Optional<Producto> p = sproductoProducto.porId(item.getProducto());
		int cantidadNueva;
		String msg = "Item guardado";

		if (cantidadProductos == 0) {
			cantidadNueva = item.getCantidad() + p.get().getCantidad();
			p.get().setCantidad(cantidadNueva); // ++stock al procducto
			sproductoProducto.guardar(p.get()); // guardamos producto
			eliminar();
			msg = "Item Descartado";
		}

		if (cantidadProductos < p.get().getCantidad() && cantidadProductos > 0) { // guardando
			cantidadNueva = p.get().getCantidad() - cantidadProductos; // nueva cantidad que tendra el producto
			p.get().setCantidad(cantidadNueva); // --stock al procducto
			sproductoProducto.guardar(p.get()); // guardando el cambio
			item.setCantidad(cantidadProductos);
			item.setSubTotal(subTotal);
			servicioItem.guardar(item);// guardando el item
			msg = "Item guardado";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages");

	}

	public void eliminar() {
		servicioItem.eliminar(item);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getCantidadProductos() {
		return cantidadProductos;
	}

	public void setCantidadProductos(int cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

}
