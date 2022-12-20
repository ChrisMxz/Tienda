package com.david.tienda.entidades;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "productos")
public class Producto {

	// atributos---------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Long idProducto;

	@Size(max = 8)
	private String concepto;

	@NotNull
	@Size(max = 30)
	private String nombre;

	@JoinColumn(name = "categoria_id", referencedColumnName = "id_categoria")
	@ManyToOne(cascade = CascadeType.ALL)
	@Valid
	private Categoria categoria;

	@Size(max = 500)
	private String descripcion;

	@NotNull
	@Size(min = 4, max = 12)
	private String sku;
	
	@NotNull
	private LocalDateTime fechaRegistro;
	
	@NotNull
	@Size(max = 4)
	private String claveUnidad;
	
	@NotNull
	private int cantidad;
	
	@NotNull
	private double precio;

	// constructores----------------

	public Producto() {
		super();
	}

	public Producto(Long idProducto, String concepto, String nombre, Categoria categoria, String descripcion,
			String sku, LocalDateTime fechaRegistro, String claveUnidad, int cantidad, double precio) {
		super();
		this.idProducto = idProducto;
		this.concepto = concepto;
		this.nombre = nombre;
		this.categoria = categoria;
		this.descripcion = descripcion;
		this.sku = sku;
		this.fechaRegistro = fechaRegistro;
		this.claveUnidad = claveUnidad;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	// setters and getters----------

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getClaveUnidad() {
		return claveUnidad;
	}

	public void setClaveUnidad(String claveUnidad) {
		this.claveUnidad = claveUnidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProducto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(idProducto, other.idProducto);
	}

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", concepto=" + concepto + ", nombre=" + nombre + ", categoria="
				+ categoria + ", descripcion=" + descripcion + ", sku=" + sku + ", fechaRegistro=" + fechaRegistro
				+ ", claveUnidad=" + claveUnidad + ", cantidad=" + cantidad + ", precio=" + precio + "]";
	}

}
