package com.david.tienda.servicios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Factura;
import com.david.tienda.util.ConexionBD;

public class ServicioFacturaImpl extends ConexionBD implements ServicioFactura, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Factura> listarTodo() {
		// TODO Auto-generated method stub
		String consulta = "SELECT f FROM Factura f";
		em = getEntityManager();
		return em.createQuery(consulta, Factura.class).getResultList();
	}

	@Override
	public List<Factura> listarPor(int tipo, String texto, int limite, boolean orden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long contar() {
		String consulta = "SELECT count(f) FROM Factura f ";
		em = getEntityManager();
		return em.createQuery(consulta, Long.class).getSingleResult();
	}

	@Override
	public void guardar(Factura t) {
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}

	}

	@Override
	public void eliminar(Factura t) {
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(t));
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}

	}

	@Override
	public Optional<Factura> porId(Factura t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Factura.class, t.getFolio()));
	}

	@Override
	public Factura porIdPedido(Long id) {
		String consulta = "SELECT f FROM Factura f where f.pedido.idPedido=: id";
		Factura f = null;
		try {
			f = em.createQuery(consulta, Factura.class).setParameter("id", id).getSingleResult();
		} catch (Exception e) {
		}
		return f;
	}

}
