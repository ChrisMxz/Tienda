package com.david.tienda.servicios;

import java.util.List;
import java.util.Optional;

import com.david.tienda.entidades.Item;

public interface ServicioItem extends Servicio<Item> {

	Optional<Item> porId(int t);

	List<Item> listarPorPedido(Long idPedido, boolean orden);
}