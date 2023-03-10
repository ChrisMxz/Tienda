package com.david.tienda.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Factura;
import com.david.tienda.servicios.ServicioFactura;
import com.david.tienda.servicios.ServicioFacturaImpl;
import com.david.tienda.util.MensajeGrowl;
import com.david.tienda.util.ToXML;

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
		factura.setEmpresa("Tienda PF");
		factura.setImpuesto("2 - IVA");
		factura.setRegimenFiscal("612 - Personas Físicas con Actividades Empresariales y Profesionales");
		factura.setUsoCFDI("G03 - Gastos en general.");
		factura.setTipoComprobante("I - Ingreso");
		factura.setRfcEmisor("MITP19012022");
		factura.setCodigoPostalEmisor("500000");
	}

	public void guardar() {
		servicioFactura.guardar(factura);

		if (factura.getFolio() > 0)
			MensajeGrowl.msgInformacion("Actualizado", "Factura Actualizada");
		else
			MensajeGrowl.msgInformacion("Guardada", " Factura Guardada");

	}

	public void btnguardar() {
		guardar();
		PrimeFaces.current().ajax().update(":formulario-facturas");
	}

	public void eliminar() {
		servicioFactura.eliminar(factura);
		MensajeGrowl.msgInformacion("Eliminada", "Factura Eliminada");
	}

	public Factura buscar(Long id) {
		return servicioFactura.porIdPedido(id);
	}

	public void exportarFacturaXML() {
		try {
			ToXML.convierte(factura);
			ToXML.descarga("factura(" + factura.getFolio() + ")");
		} catch (Exception e) {
			MensajeGrowl.msgError("Error", "No se pudo descargar el archivo");
		}
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

}
