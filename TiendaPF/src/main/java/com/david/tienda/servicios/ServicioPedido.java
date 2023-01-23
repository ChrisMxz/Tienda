package com.david.tienda.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Pedido;

public interface ServicioPedido extends Servicio<Pedido> {

	List<Pedido> filtrarPor(int filtro, String estatus, LocalDateTime fecha, String texto, int limite, boolean orden);
	

	default Optional<Pedido> porId(Long t) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
