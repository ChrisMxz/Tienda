package com.david.tienda.servicios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Categoria;
import com.david.tienda.entidades.Usuario;
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
	public List<Categoria> listarPor(int filtro, String texto, int limite) {
		String consulta = "SELECT c FROM Categoria c";
		em = getEntityManager();
		List<Categoria> categorias = null;

		// existe un texto que buscar
		if (!texto.isEmpty()) {
			// Busqueda por id
			if (filtro == 1)
				consulta = consulta + " where c.idCategoria like:id";
			// Busqueda por nombre/descripcion
			if (filtro == 2)
				consulta = consulta + " where c.descripcion like:texto";

			if (filtro != 1) {
				categorias = em.createQuery(consulta, Categoria.class).setParameter("texto", "%" + texto + "%")
						.setMaxResults(limite).getResultList();
			} else {
				try {
					Long id;
					id = Long.parseLong(texto);
					categorias = em.createQuery(consulta, Categoria.class).setParameter("id", id).setMaxResults(limite)
							.getResultList();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} else {
			categorias = em.createQuery(consulta, Categoria.class).setMaxResults(limite).getResultList();
		}

		return categorias;
	}

	@Override
	public Optional<Categoria> porId(Categoria t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Categoria.class, t.getIdCategoria()));
	}

	public Optional<Usuario> porId(int t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Usuario.class, t));
	}

	@Override
	public Long contar() {
		String consulta = "SELECT count(p) FROM Categoria p ";
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
