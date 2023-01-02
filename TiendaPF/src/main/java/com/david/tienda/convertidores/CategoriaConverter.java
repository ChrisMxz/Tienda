package com.david.tienda.convertidores;

import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.david.tienda.entidades.Categoria;
import com.david.tienda.servicios.ServicioCategoriaImpl;

@RequestScoped
@ManagedBean(name = "categoriaConverter")
public class CategoriaConverter implements Converter {

	private ServicioCategoriaImpl servicio = new ServicioCategoriaImpl();

	public Categoria getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null) {
			return null;
		}
		Optional<Categoria> categoriaOptional = Optional.empty();
		try {
			categoriaOptional = Optional.ofNullable(servicio.porId(Long.valueOf(id)));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (categoriaOptional.isPresent()) {
			return categoriaOptional.get();
		}
		return null;

	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object categoria) {
		if (categoria == null) {
			return "0";
		}
		return (((Categoria) categoria)).getIdCategoria().toString();
	}

}
