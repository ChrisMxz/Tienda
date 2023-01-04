package com.david.tienda.servicios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Categoria;
import com.david.tienda.util.ConexionBD;

public class ServicioCategoriaImpl extends ConexionBD implements Servicio<Categoria>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Categoria> listarTodo() {
		// TODO Auto-generated method stub
		String consulta = "SELECT c FROM Categoria c";
		em = getEntityManager();
		return em.createQuery(consulta, Categoria.class).getResultList();
	}

	@Override
	public List<Categoria> listarPor(int filtro, String texto, int limite, boolean orden) {
		String consulta = "SELECT c FROM Categoria c";
		em = getEntityManager();
		List<Categoria> categorias = new ArrayList<>();
		String ord;

		if (orden)
			ord = "asc";
		else
			ord = "desc";

		// no hay texto que buscar

		if (texto != null && !texto.isEmpty()) {

			// Busqueda por id
			if (filtro == 1) {
				consulta = consulta + " where c.idCategoria like:id ORDER BY c.idCategoria " + ord;
				try {
					Long id;
					id = Long.parseLong(texto);
					categorias = em.createQuery(consulta, Categoria.class).setParameter("id", id).setMaxResults(limite)
							.getResultList();
				} catch (Exception e) {

				}
			}

			// Busqueda por nombre/descripcion
			if (filtro == 2) {
				consulta = consulta + " where c.nombre like:texto ORDER BY c.idCategoria " + ord;

				categorias = em.createQuery(consulta, Categoria.class).setParameter("texto", "%" + texto + "%")
						.setMaxResults(limite).getResultList();
			}

		} else {
			consulta = consulta + " ORDER BY c.idCategoria " + ord;
			categorias = em.createQuery(consulta, Categoria.class).setMaxResults(limite).getResultList();
		}

		return categorias;

	}

	@Override
	public Optional<Categoria> porId(Categoria t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Categoria.class, t.getIdCategoria()));
	}

	public Categoria porId(Long t) {
		em = getEntityManager();
		return em.find(Categoria.class, t);
	}

	@Override
	public Long contar() {
		String consulta = "SELECT count(c) FROM Categoria c ";
		em = getEntityManager();
		return em.createQuery(consulta, Long.class).getSingleResult();
	}

	@Override
	public void guardar(Categoria t) {
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
	public void eliminar(Categoria t) {
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(t));
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}

	}

}
