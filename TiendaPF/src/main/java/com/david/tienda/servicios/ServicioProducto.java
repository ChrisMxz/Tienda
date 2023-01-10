package com.david.tienda.servicios;

import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Producto;

public interface ServicioProducto extends Servicio<Producto> {

	List<Producto> filtrarPor(int filtro, Long categoria, String texto, int limite, boolean orden);
	

	default Optional<Producto> porId(int t) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


	Optional<Producto> porId(Long t);

}
