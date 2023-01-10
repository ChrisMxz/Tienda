package com.david.tienda.servicios;

import java.util.Optional;

import com.david.tienda.entidades.Usuario;

public interface ServicioUsuario extends Servicio<Usuario> {
	
	Optional<Usuario> login(String user, String pass);
	Usuario porUsername(String user);
	Usuario porRFC(String rfc);
	
	default Optional<Usuario> porId(Long t) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}
