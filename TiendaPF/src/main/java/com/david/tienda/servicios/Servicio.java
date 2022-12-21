package com.david.tienda.servicios;

import java.util.List;
import java.util.Optional;

public interface Servicio<T> {
	
	List<T> listarTodo();

	List<T> listarPor(int filtro, String texto, int limite);

	Optional<T> porId(T t);

	Long contar();

	void guardar(T t);

	void eliminar(T t);

}
