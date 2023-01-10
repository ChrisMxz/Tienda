package com.david.tienda.convertidores;

import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.david.tienda.entidades.Producto;
import com.david.tienda.servicios.ServicioProducto;
import com.david.tienda.servicios.ServicioProductoImpl;

@RequestScoped
@ManagedBean(name = "productoConverter")
public class ProductoConverter implements Converter {

	private ServicioProducto servicio = new ServicioProductoImpl();

	public Producto getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null) {
			return null;
		}
		Optional<Producto> ProductoOptional = Optional.empty();
		try {
			ProductoOptional = servicio.porId(Long.parseLong(id));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ProductoOptional.isPresent()) {
			return ProductoOptional.get();
		}
		return null;

	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object Producto) {
		if (Producto == null) {
			return "0";
		}
		return (((Producto) Producto)).getIdProducto().toString();
	}

	public ServicioProducto getServicio() {
		return servicio;
	}

	public void setServicio(ServicioProducto servicio) {
		this.servicio = servicio;
	}

}
