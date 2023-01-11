package com.david.tienda.beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
	@ManagedProperty(value = "#{pedidoBean}")
	private PedidoBean pedidoBean;

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
	}

	public void guardar() {
		String msg = "";

		if (item.getIdPedido() == null) {
			msg = "Item sin pedido asociado";
			return;
		}

		if (item.getIdItem() == null) {// no encontro el item
			msg = "Item Guardado";
			modificarCantidadProducto(item.getCantidad());
		} else {

			Optional<Item> busqueda = servicioItem.porId(item.getIdItem());
			int dif = this.item.getCantidad() - busqueda.get().getCantidad(); // calculando la diferencia
			modificarCantidadProducto(dif);
			msg = "Item Actualizado";
		}
		servicioItem.guardar(item);
		pedidoBean.guardar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages");
	}

	public void eliminar() {
		servicioItem.eliminar(item);
	}

	public void calcularSubtotal() {
		item.setSubTotal(item.getCantidad() * item.getProducto().getPrecio());
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public PedidoBean getPedidoBean() {
		return pedidoBean;
	}

	public void setPedidoBean(PedidoBean pedidoBean) {
		this.pedidoBean = pedidoBean;
	}

}
