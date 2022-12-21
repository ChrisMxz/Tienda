package com.david.tienda.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ConexionBD {
	protected static EntityManager em;
	private static EntityManagerFactory emf;
	private static final String PU = "ProyectoTienda";

	public ConexionBD() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PU);
		}
	}

	protected EntityManager getEntityManager() {
		if (em == null) {
			em = emf.createEntityManager();
		}
		return em;
	}
}
