package com.david.tienda.servicios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Producto;
import com.david.tienda.util.ConexionBD;

public class ServicioProductoImpl extends ConexionBD implements ServicioProducto, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Producto> listarTodo() {
		// TODO Auto-generated method stub
		String consulta = "SELECT p FROM Producto p";
		em = getEntityManager();
		return em.createQuery(consulta, Producto.class).getResultList();
	}

	@Override
	public List<Producto> listarPor(int filtro, String texto, int limite, boolean orden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Producto> porId(Long t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Producto.class, t));
	}

	@Override
	public Long contar() {
		String consulta = "SELECT count(p) FROM Producto p ";
		em = getEntityManager();
		return em.createQuery(consulta, Long.class).getSingleResult();
	}

	@Override
	public void guardar(Producto t) {
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
	public void eliminar(Producto t) {
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
	public List<Producto> filtrarPor(int filtro, Long categoria, String texto, int limite, boolean orden) {
		String consulta = "SELECT p FROM Producto p";
		Long id = null;
		em = getEntityManager();
		String ord;
		List<Producto> productos = null;

		if (orden)
			ord = "asc";
		else
			ord = "desc";

		// existe un texto que buscar
		if (texto != null && !texto.isEmpty()) {
			// Busqueda por id
			if (filtro == 1) {
				consulta = consulta + " where p.idProducto like:id";
				try {
					id = Long.parseLong(texto);

				} catch (Exception e) {

				}
			}

			// Busqueda por nombre
			if (filtro == 2)
				consulta = consulta + " where p.nombre like:texto";
			// Busqueda por sku
			if (filtro == 3)
				consulta = consulta + " where p.sku like:texto";
			// Aplicando filtro de categoria
			if (categoria != null) {
				consulta = consulta + " and p.categoria.idCategoria like:idCat";
			}

			// aplicando orden
			consulta = consulta + " ORDER BY p.idProducto " + ord;

			// busqueda por id y categoria
			if (filtro == 1 && categoria != null) {
				productos = em.createQuery(consulta, Producto.class).setParameter("id", id)
						.setParameter("idCat", categoria).setMaxResults(limite).getResultList();
			} else if (filtro == 1 && categoria == null) {
				productos = em.createQuery(consulta, Producto.class).setParameter("id", id).setMaxResults(limite)
						.getResultList();
			}

			// busqueda por otro parametro
			if (filtro != 1 && categoria != null) {
				productos = em.createQuery(consulta, Producto.class).setParameter("texto", "%" + texto + "%")
						.setParameter("idCat", categoria).setMaxResults(limite).getResultList();
			} else if (filtro != 1 && categoria == null) {
				productos = em.createQuery(consulta, Producto.class).setParameter("texto", "%" + texto + "%")
						.setMaxResults(limite).getResultList();
			}

		} else { // No hay texto que buscar
			if (categoria != null) {
				consulta = consulta + " where p.categoria.idCategoria like:idCat ORDER BY p.idProducto " + ord;
				productos = em.createQuery(consulta, Producto.class).setParameter("idCat", categoria)
						.setMaxResults(limite).getResultList();
			} else {
				// aplicando orden
				consulta = consulta + " ORDER BY p.idProducto " + ord;
				// todos los productos sin filtro
				productos = em.createQuery(consulta, Producto.class).setMaxResults(limite).getResultList();
			}

		}

		return productos;
	}

	@Override
	public Optional<Producto> porId(Producto t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Producto.class, t.getIdProducto()));
	}

}
