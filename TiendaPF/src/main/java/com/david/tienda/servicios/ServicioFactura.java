package com.david.tienda.servicios;

import java.util.Optional;

import com.david.tienda.entidades.Factura;

public interface ServicioFactura extends Servicio<Factura> {
	Optional<Factura> porIdPedido(Long id);
}
