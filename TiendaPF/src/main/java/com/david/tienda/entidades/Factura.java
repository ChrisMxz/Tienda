package com.david.tienda.entidades;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "facturas")
public class Factura {

	// atributos---------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "folio")
	private Long folio;
	
	@NotNull
	@Column(name = "pedido_id")
	private Long pedidoId;
	// pendiente xml almacenado

	// constructores----------------
	public Factura() {
		super();
	}

	public Factura(Long folio, Long pedidoId) {
		super();
		this.folio = folio;
		this.pedidoId = pedidoId;
	}

	// setters and getters----------

	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
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
		return "Factura [folio=" + folio + ", pedidoId=" + pedidoId + "]";
	}

}
