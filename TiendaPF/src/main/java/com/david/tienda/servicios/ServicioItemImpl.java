package com.david.tienda.servicios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Item;
import com.david.tienda.util.ConexionBD;

public class ServicioItemImpl extends ConexionBD implements ServicioItem, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Item> listarTodo() {
		// TODO Auto-generated method stub
		String consulta = "SELECT i FROM Item i";
		em = getEntityManager();
		return em.createQuery(consulta, Item.class).getResultList();
	}

	@Override
	public Optional<Item> porId(Long t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Item.class, t));
	}

	@Override
	public Item porPedidoProducto(Long pedido, Long producto) {
		String consulta = "SELECT i FROM Item i where i.idPedido like:idped and i.producto.idProducto like:idpro ";
		Item x = null;
		em = getEntityManager();

		try {
			x = em.createQuery(consulta, Item.class).setParameter("idped", pedido).setParameter("idpro", producto)
					.getSingleResult();
		} catch (Exception e) {

		}
		return x;
	}

	@Override
	public Long contar() {
		String consulta = "SELECT count(i) FROM Item i ";
		em = getEntityManager();
		return em.createQuery(consulta, Long.class).getSingleResult();
	}

	@Override
	public void guardar(Item t) {
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
			ex.printStackTrace(System.out);
		}

	}

	@Override
	public void eliminar(Item t) {
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(t));
			em.getTransaction().commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
			ex.printStackTrace(System.out);
		}

	}

	@Override
	public Optional<Item> porId(Item t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Item.class, t.getIdItem()));
	}

	@Override
	public List<Item> listarPor(int filtro, String texto, int limite, boolean orden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> listarPorPedido(Long idPedido, boolean orden) {
		String ord;

		if (orden)
			ord = "asc";
		else
			ord = "desc";

		String consulta = "SELECT i FROM Item i where i.idPedido like:id ORDER BY i.idPedido " + ord;

		em = getEntityManager();
		return em.createQuery(consulta, Item.class).setParameter("id", idPedido).getResultList();
	}

}
