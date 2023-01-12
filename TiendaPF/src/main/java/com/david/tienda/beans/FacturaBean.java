package com.david.tienda.beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Factura;
import com.david.tienda.servicios.ServicioFactura;
import com.david.tienda.servicios.ServicioFacturaImpl;

@ManagedBean
@ViewScoped
public class FacturaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Factura factura;
	private ServicioFactura servicioFactura;

	@PostConstruct
	public void iniciar() {
		servicioFactura = new ServicioFacturaImpl();
		nuevo();
	}

	public void nuevo() {
		factura = new Factura();
	}

	public void guardar() {

		servicioFactura.guardar(factura);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Guardado"));
		PrimeFaces.current().ajax().update(":messages");
	}

	public void eliminar() {
		servicioFactura.eliminar(factura);
	}

	public Factura buscar() {
		Optional<Factura> f = servicioFactura.porId(factura);
		return f.get();
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

}
