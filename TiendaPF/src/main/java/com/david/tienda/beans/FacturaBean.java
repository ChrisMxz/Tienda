package com.david.tienda.beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.david.tienda.entidades.Factura;
import com.david.tienda.servicios.ServicioFactura;
import com.david.tienda.servicios.ServicioFacturaImpl;
import com.david.tienda.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class FacturaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Factura factura;
	private ServicioFactura servicioFactura;

	@PostConstruct
	public void iniciar() {
		servicioFactura = new ServicioFacturaImpl();
	}

	public void nuevo() {
		factura = new Factura();
	}

	public void guardar() {
		servicioFactura.guardar(factura);

		if (factura.getFolio() > 0)
			MensajeGrowl.msgInformacion("Factura", "Actualizado");
		else
			MensajeGrowl.msgInformacion("Factura", "Guardada");

	}

	public void eliminar() {
		servicioFactura.eliminar(factura);
		MensajeGrowl.msgInformacion("Factura", "Eliminada");
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
