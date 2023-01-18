package com.david.tienda.entidades;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "facturas")
public class Factura {

	// atributos---------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "folio")
	private Long folio;

	private String empresa;

	private String usoCFDI;

	private String tipoComprobante;

	private String regimenFiscal;

	private String impuesto;

	private String rfcEmisor;

	private String codigoPostalEmisor;

	@CreationTimestamp
	private LocalDateTime fechaExpedicion;

	// constructores----------------
	public Factura() {
		super();
	}

	public Factura(Long folio, String empresa, String usoCFDI, String tipoComprobante, String regimenFiscal,
			String impuesto, String rfcEmisor, String codigoPostalEmisor, LocalDateTime fechaExpedicion) {
		super();
		this.folio = folio;
		this.empresa = empresa;
		this.usoCFDI = usoCFDI;
		this.tipoComprobante = tipoComprobante;
		this.regimenFiscal = regimenFiscal;
		this.impuesto = impuesto;
		this.rfcEmisor = rfcEmisor;
		this.codigoPostalEmisor = codigoPostalEmisor;
		this.fechaExpedicion = fechaExpedicion;
	}

	// setters and getters----------

	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public String getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	public String getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getCodigoPostalEmisor() {
		return codigoPostalEmisor;
	}

	public void setCodigoPostalEmisor(String codigoPostalEmisor) {
		this.codigoPostalEmisor = codigoPostalEmisor;
	}

	public LocalDateTime getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(LocalDateTime fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(folio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		return Objects.equals(folio, other.folio);
	}

	@Override
	public String toString() {
		return "Factura [folio=" + folio + ", usoCFDI=" + usoCFDI + ", tipoComprobante=" + tipoComprobante
				+ ", regimenFiscal=" + regimenFiscal + ", impuesto=" + impuesto + ", rfcEmisor=" + rfcEmisor
				+ ", codigoPostalEmisor=" + codigoPostalEmisor + ", fechaExpedicion=" + fechaExpedicion + "]";
	}

}
