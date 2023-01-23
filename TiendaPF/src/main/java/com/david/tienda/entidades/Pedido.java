package com.david.tienda.entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.david.tienda.servicios.ServicioItem;
import com.david.tienda.servicios.ServicioItemImpl;

@Entity
@Table(name = "pedidos")
@XmlRootElement
public class Pedido {

	// atributos---------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Long idPedido;

	@NotNull
	@Size(max = 10)
	private String estatus;

	@JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
	@ManyToOne
	@Valid
	private Usuario cliente;

	@CreationTimestamp
	private LocalDateTime fechaPedido;
	@UpdateTimestamp
	private LocalDateTime fechaModificacion;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	private List<Item> listaItems;

	private int cantidadProductos;
	private double descuento;
	private double iva;

	@Max(1000000)
	private double subTotal;

	@Max(1000000)
	private double total;

	private String formaPago;

	private String metodoPago;

	// constructores----------------
	public Pedido() {
		super();
		this.cliente = new Usuario();
		listaItems = new ArrayList<>();
	}

	public Pedido(Long idPedido, String estatus, Usuario cliente, LocalDateTime fechaPedido, List<Item> listaItems,
			int cantidadProductos, double descuento, double iva, double subTotal, double total) {
		super();
		this.idPedido = idPedido;
		this.estatus = estatus;
		this.cliente = cliente;
		this.fechaPedido = fechaPedido;
		// this.listaItems = listaItems;
		this.cantidadProductos = cantidadProductos;
		this.descuento = descuento;
		this.iva = iva;
		this.subTotal = subTotal;
		this.total = total;
	}

	// setters and getters----------

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDateTime fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public List<Item> getListaItems() {
		ServicioItem s = new ServicioItemImpl();
		return s.listarPorPedido(idPedido, true);
	}

	public void setListaItems(List<Item> listaItems) {
		this.listaItems = listaItems;
	}

	public int getCantidadProductos() {
		return cantidadProductos;
	}

	public void setCantidadProductos(int cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPedido);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(idPedido, other.idPedido);
	}

	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", estatus=" + estatus + ", cliente=" + cliente + ", fechaPedido="
				+ fechaPedido + ", fechaModificacion=" + fechaModificacion + ", cantidadProductos=" + cantidadProductos
				+ ", descuento=" + descuento + ", iva=" + iva + ", subTotal=" + subTotal + ", total=" + total + "]";
	}

}
