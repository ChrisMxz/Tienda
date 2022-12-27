package com.david.tienda.servicios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Usuario;
import com.david.tienda.util.ConexionBD;

public class ServicioUsuarioImpl extends ConexionBD implements ServicioUsuario, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Usuario> listarTodo() {
		// TODO Auto-generated method stub
		String consulta = "SELECT u FROM Usuario u";
		em = getEntityManager();
		return em.createQuery(consulta, Usuario.class).getResultList();
	}

	@Override
	public List<Usuario> listarPor(int filtro, String texto, int limite) {
		String consulta = "SELECT u FROM Usuario u ";
		em = getEntityManager();
		List<Usuario> usuarios = null;

		// existe un texto que buscar
		if (texto != null) {
			// Busqueda por id
			if (filtro == 1)
				consulta = consulta + "where u.idUsuario like:id";
			// Busqueda por nombre
			if (filtro == 2)
				consulta = consulta + "where u.nombre like:texto";
			// Busqueda por apellido paterno
			if (filtro == 3)
				consulta = consulta + "where u.appaterno like:texto";
			// Busqueda por apellido materno
			if (filtro == 4)
				consulta = consulta + "where u.apmaterno like:texto";
			// Busqueda por correo1
			if (filtro == 5)
				consulta = consulta + "where u.contacto.email like:texto";

			// aplicando limite de consulta

			consulta = consulta + limite;

			if (filtro != 1) {
				usuarios = em.createQuery(consulta, Usuario.class).setParameter("texto", "%" + texto + "%")
						.getResultList();
			} else {
				try {
					int id;
					id = Integer.parseInt(texto);
					usuarios = em.createQuery(consulta, Usuario.class).setParameter("id", id).getResultList();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} else {
			usuarios = em.createQuery(consulta, Usuario.class).getResultList();
		}

		return usuarios;

	}

	@Override
	public Optional<Usuario> porId(int t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Usuario.class, t));
	}

	@Override
	public Long contar() {
		String consulta = "SELECT count(u) FROM Usuarios u ";
		em = getEntityManager();
		return em.createQuery(consulta, Long.class).getSingleResult();
	}

	@Override
	public void guardar(Usuario t) {

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
	public void eliminar(Usuario t) {
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
	public Optional<Usuario> login(String user, String pass) {
		return Optional.ofNullable(porUsername(user)).filter(u -> u.getPassword().equals(pass));
	}

	@Override
	public Usuario porUsername(String user) {
		Usuario s = null;
		String consulta = "SELECT u FROM Usuario u where u.username=: user";
		em = getEntityManager();

		try {
			s = em.createQuery(consulta, Usuario.class).setParameter("user", user).getSingleResult();
		} catch (Exception e) {

		}
		return s;
	}

	@Override
	public Optional<Usuario> porId(Usuario t) {
		em = getEntityManager();
		return Optional.ofNullable(em.find(Usuario.class, t.getIdUsuario()));
	}

	@Override
	public Usuario porRFC(String rfc) {
		Usuario s = null;
		String consulta = "SELECT u FROM Usuario u where u.rfc=: rfc";
		em = getEntityManager();

		try {
			s = em.createQuery(consulta, Usuario.class).setParameter("rfc", rfc).getSingleResult();
		} catch (Exception e) {

		}
		return s;
	}

}
