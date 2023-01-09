package com.david.tienda.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.david.tienda.entidades.Item;
import com.david.tienda.servicios.ServicioItem;
import com.david.tienda.servicios.ServicioItemImpl;

@ManagedBean
@ViewScoped
public class ItemBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Item item;
	private ServicioItem servicioItem;

	@PostConstruct
	public void iniciar() {
		servicioItem = new ServicioItemImpl();
	}

	public void nuevo() {
		item = new Item();
	}

	public void guardar() {
		String msg = "Asignado";

		if (item.getIdItem() != null)
			msg = "Actualizado";

		servicioItem.guardar(item);
		System.out.println("item " + msg);
	}

	public void eliminar() {
		servicioItem.eliminar(item);
		System.out.println("item eliminado");
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
