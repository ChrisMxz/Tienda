package com.david.tienda.test;

import java.util.ArrayList;
import java.util.Scanner;

import com.david.tienda.entidades.Pedido;
import com.david.tienda.servicios.ServicioPedido;
import com.david.tienda.servicios.ServicioPedidoImpl;
import com.david.tienda.util.ToXML;

import java.util.List;

public class TestXML {

	private static List<Pedido> listaPedidos = new ArrayList<>();
	private static Pedido pedido = new Pedido();
	private static ServicioPedido servicio = new ServicioPedidoImpl();

	public static void main(String[] args) {
		listar();
		buscar();
		ToXML.convierte(pedido);
	}

	public static void listar() {
		// lista de pedidos
		try {
			listaPedidos = servicio.listarTodo();
			for (Pedido p : listaPedidos) {
				System.out.println(p.getIdPedido() + "-" + p.getCliente().getNombre());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void buscar() {

		try (Scanner s = new Scanner(System.in)) {
			System.out.print("Ingrese el id: ");

			Long cantidad = (long) s.nextInt();
			s.nextLine();
			pedido = servicio.porId(cantidad).get();

		} catch (Exception e) {
			System.out.println("Error");
		}
	}

}
