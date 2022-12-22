package com.david.tienda.entidades;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "direcciones")
public class Direccion {
	// atributos---------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_direccion")
	private Long id_direccion;
	
	@Size(max = 50)
	private String pais;
	
	@NotNull
	@Size(max = 50)
	private String entidadFederativa;
	
	@NotNull
	@Size(max = 50)
	private String colonia;
	
	@NotNull
	@Size(max = 5)
	private String codigoPostal;
	
	@NotNull
	@Size(max = 100)
	private String calle;

	@Size(max = 30)
	private String numInterior;

	@Size(max = 30)
	private String numExterior;

	@Size(max = 500)
	private String descripcion;

	// constructores----------------
	public Direccion() {

	}

	public Direccion(Long id_direccion, String pais, String entidadFederativa, String colonia, String codigoPostal,
			String calle, String numInterior, String numExterior, String descripcion) {
		super();
		this.id_direccion = id_direccion;
		this.pais = pais;
		this.entidadFederativa = entidadFederativa;
		this.colonia = colonia;
		this.codigoPostal = codigoPostal;
		this.calle = calle;
		this.numInterior = numInterior;
		this.numExterior = numExterior;
		this.descripcion = descripcion;
	}

	// setters and getters----------

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public String getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId_direccion() {
		return id_direccion;
	}

	public void setId_direccion(Long id_direccion) {
		this.id_direccion = id_direccion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_direccion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direccion other = (Direccion) obj;
		return Objects.equals(id_direccion, other.id_direccion);
	}

	@Override
	public String toString() {
		return "Direccion [id_direccion=" + id_direccion + ", pais=" + pais + ", entidadFederativa=" + entidadFederativa
				+ ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + ", calle=" + calle + ", numInterior="
				+ numInterior + ", numExterior=" + numExterior + ", descripcion=" + descripcion + "]";
	}

}
