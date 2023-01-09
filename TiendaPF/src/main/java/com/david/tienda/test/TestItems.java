package com.david.tienda.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.david.tienda.entidades.Item;
import com.david.tienda.entidades.Producto;
import com.david.tienda.servicios.ServicioItem;
import com.david.tienda.servicios.ServicioItemImpl;
import com.david.tienda.servicios.ServicioProducto;
import com.david.tienda.servicios.ServicioProductoImpl;

public class TestItems {

	public TestItems() {

	}

	public static void main(String[] args) {

		ServicioItem servicio = new ServicioItemImpl();
		List<Item> items = new ArrayList<>();

		items = servicio.listarPorPedido(1L, true);
		for (Item item : items) {
			System.out.println(item + "/n");
		}

		items.clear();

		insertar(1L, servicio);

		items = servicio.listarPorPedido(1L, true);
		for (Item item : items) {
			System.out.println(item + "/n");
		}

		items.clear();

	}

	public static void insertar(Long idPedido, ServicioItem servicio) {
		Optional<Producto> p = Optional.ofNullable(new Producto());
		ServicioProducto sProducto = new ServicioProductoImpl();
		Item item = new Item();
		int cantidad;
		double subtotal;
		
		p = sProducto.porId(95);
		item.setProducto(p.get());
		
		try (Scanner s = new Scanner(System.in)) {
			System.out.println("Ingrese la cantidad: ");

			cantidad = s.nextInt();
			s.nextLine();
			subtotal = p.get().getPrecio() * cantidad;

		} catch (Exception e) {
			return;
		}

		
		

		item.setIdPedido(idPedido);
		item.setCantidad(cantidad);
		item.setSubTotal(subtotal);

		servicio.guardar(item);
		System.out.println("Guardado");
	}

}
