package com.david.tienda.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.david.tienda.entidades.Pedido;

import com.david.tienda.servicios.ServicioPedido;
import com.david.tienda.servicios.ServicioPedidoImpl;

public class TestPedidos {

	public static void main(String[] args) {

		ServicioPedido servicio = new ServicioPedidoImpl();
		List<Pedido> pedidos = new ArrayList<>();

		// filtrarPor(tipo,estatus,fecha,texto,limite,orden)

		// todo (por id)
		LocalDateTime fecha = LocalDateTime.of(2023, 01, 9, 04, 25);

		pedidos = servicio.filtrarPor(1, null, null, null, 10, true);
		System.out.println(pedidos);

		pedidos.clear();

		// filtrando por hecho
		pedidos = servicio.filtrarPor(1, "pendiente", null, null, 10, true);
		System.out.println(pedidos);
		pedidos.clear();

		// filtrando por fecha
		pedidos = servicio.filtrarPor(1, null, fecha, null, 10, true);
		System.out.println(pedidos);
		pedidos.clear();

		// filtrando por cliente
		pedidos = servicio.filtrarPor(2, null, null, "1", 10, true);
		System.out.println(pedidos);
		pedidos.clear();

	}

}
