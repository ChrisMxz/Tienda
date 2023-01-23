package com.david.tienda.servicios;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Pedido;
import com.david.tienda.util.ConexionBD;

public class ServicioPedidoImpl extends ConexionBD implements ServicioPedido, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Pedido> listarTodo() {
		// TODO Auto-generated method stub
		String consulta = "SELECT p FROM Pedido p";
		em = getEntityManager();
		return em.createQuery(consulta, Pedido.class).getResultList();
	}

	@Override
	public List<Pedido> listarPor(int tipo, String texto, int limite, boolean orden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Pedido> porId(Long t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Pedido.class, t));
	}

	@Override
	public Long contar() {
		String consulta = "SELECT count(p) FROM Pedido p ";
		em = getEntityManager();
		return em.createQuery(consulta, Long.class).getSingleResult();
	}

	@Override
	public void guardar(Pedido t) {
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
	public void eliminar(Pedido t) {
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
	public List<Pedido> filtrarPor(int tipo, String estatus, LocalDateTime fecha, String texto, int limite,
			boolean orden) {
		String consulta = "SELECT p FROM Pedido p";
		Long id = null;
		em = getEntityManager();
		String ord;
		List<Pedido> Pedidos = null;

		if (orden)
			ord = "asc";
		else
			ord = "desc";

		// existe un texto que buscar
		if (texto != null && !texto.isEmpty()) {
			// Busqueda por id
			if (tipo == 1) {
				consulta = consulta + " where p.idPedido like:id";
				id = convertir(texto);
			}
			// Busqueda por Cliente id
			if (tipo == 2) {
				consulta = consulta + " where p.cliente.idUsuario like:id";
				id = convertir(texto);
			}

			// filtro por estatus
			if (estatus != null)
				consulta = consulta + " and p.estatus like:estatus";

			// filtro por fecha
			if (estatus != null)
				consulta = consulta + " and p.fechaPedido like:fechaPedido";

			// aplicando orden
			consulta = consulta + " ORDER BY p.idPedido " + ord;

			// Ejecutando consulta

			if (estatus != null && fecha != null) {
				try {
					Pedidos = em.createQuery(consulta, Pedido.class).setParameter("id", id)
							.setParameter("estatus", estatus).setParameter("fechaPedido", fecha).setMaxResults(limite)
							.getResultList();
				} catch (Exception e) {
					System.out.println("Error al buscar pedido (2 filtros): " + e);
				}

			} else if (estatus != null) {
				try {
					Pedidos = em.createQuery(consulta, Pedido.class).setParameter("id", id)
							.setParameter("estatus", estatus).setMaxResults(limite).getResultList();
				} catch (Exception e) {
					System.out.println("Error al buscar pedido (filtro estatus): " + e);
				}

			} else if (fecha != null) {
				try {
					Pedidos = em.createQuery(consulta, Pedido.class).setParameter("id", id)
							.setParameter("estatus", estatus).setMaxResults(limite).getResultList();
				} catch (Exception e) {
					System.out.println("Error al buscar pedido (filtro fecha): " + e);
				}

			} else { // sin filtros
				try {
					Pedidos = em.createQuery(consulta, Pedido.class).setParameter("id", id).setMaxResults(limite)
							.getResultList();
				} catch (Exception e) {
					System.out.println("Error al buscar (txt) pedido (sin Filtro): " + e);
				}
			}

		} else { // No hay texto que buscar

			// Ejecutando consulta

			if (estatus != null && fecha != null) {
				consulta = consulta
						+ " where p.estatus like:estatus and p.fechaPedido like:fechaPedido ORDER BY p.idPedido " + ord;
				try {
					Pedidos = em.createQuery(consulta, Pedido.class).setParameter("estatus", estatus)
							.setParameter("fechaPedido", fecha).setMaxResults(limite).getResultList();
				} catch (Exception e) {
					System.out.println("Error al buscar(todo) pedido (2 filtros): " + e);
				}

			} else if (estatus != null) {
				consulta = consulta + " where p.estatus like:estatus ORDER BY p.idPedido " + ord;
				try {
					Pedidos = em.createQuery(consulta, Pedido.class).setParameter("estatus", estatus)
							.setMaxResults(limite).getResultList();
				} catch (Exception e) {
					System.out.println("Error al buscar(todo) pedido (filtro estatus): " + e);
				}

			} else if (fecha != null) {
				consulta = consulta + " where p.fechaPedido like:fechaPedido ORDER BY p.idPedido " + ord;
				try {
					Pedidos = em.createQuery(consulta, Pedido.class).setParameter("fechaPedido", fecha)
							.setMaxResults(limite).getResultList();
				} catch (Exception e) {
					System.out.println("Error al buscar(todo) pedido (filtro fecha): " + e);
				}

			} else {
				consulta = consulta + " ORDER BY p.idPedido " + ord;
				try {
					Pedidos = em.createQuery(consulta, Pedido.class).setMaxResults(limite).getResultList();
				} catch (Exception e) {
					System.out.println("Error al consultar todo (sin Filtro): " + e);
				}

			}

		}

		return Pedidos;
	}

	private static Long convertir(String texto) {
		Long id = null;
		try {
			id = Long.parseLong(texto);

		} catch (Exception e) {

		}
		return id;
	}

	@Override
	public Optional<Pedido> porId(Pedido t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Pedido.class, t.getIdPedido()));
	}

}
