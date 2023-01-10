package com.david.tienda.entidades;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item {

	// atributos---------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item")
	private Long idItem;

	@NotNull
	@Column(name = "pedido_id")
	private Long idPedido;

	@JoinColumn(name = "producto_id", referencedColumnName = "id_producto")
	@ManyToOne
	private Producto producto;

	@NotNull
	private int cantidad;

	@Max(1000000)
	private double subTotal;

	// constructores----------------
	public Item() {
		super();
		this.producto = new Producto();
	}

	public Item(Long idItem, Long idPedido, Producto producto, int cantidad, double subTotal) {
		super();
		this.idItem = idItem;
		this.idPedido = idPedido;
		this.producto = producto;
		this.cantidad = cantidad;
		this.subTotal = subTotal;
	}

	// setters and getters----------

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idItem);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(idItem, other.idItem);
	}

	@Override
	public String toString() {
		return "Item [idItem=" + idItem + ", idPedido=" + idPedido + ", producto=" + producto + ", cantidad=" + cantidad
				+ ", subTotal=" + subTotal + "]";
	}

}
