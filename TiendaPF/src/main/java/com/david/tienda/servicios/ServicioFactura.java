package com.david.tienda.servicios;

import com.david.tienda.entidades.Factura;

public interface ServicioFactura extends Servicio<Factura> {
	Factura porIdPedido(Long id);
}
