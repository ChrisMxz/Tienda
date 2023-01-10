package com.david.tienda.convertidores;

import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.david.tienda.entidades.Usuario;
import com.david.tienda.servicios.ServicioUsuario;
import com.david.tienda.servicios.ServicioUsuarioImpl;

@RequestScoped
@ManagedBean(name = "clienteConverter")
public class ClienteConverter implements Converter {

	private ServicioUsuario servicio = new ServicioUsuarioImpl();

	public Usuario getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null) {
			return null;
		}
		Optional<Usuario> UsuarioOptional = Optional.empty();
		try {
			UsuarioOptional = servicio.porId(Long.parseLong(id));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (UsuarioOptional.isPresent()) {
			return UsuarioOptional.get();
		}
		return null;

	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object Usuario) {
		if (Usuario == null) {
			return "0";
		}
		return (((Usuario) Usuario)).getIdUsuario().toString();
	}

	public ServicioUsuario getServicio() {
		return servicio;
	}

	public void setServicio(ServicioUsuario servicio) {
		this.servicio = servicio;
	}

}
