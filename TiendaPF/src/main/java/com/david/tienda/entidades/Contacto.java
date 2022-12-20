package com.david.tienda.entidades;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "contactos")
public class Contacto {
	// atributos---------------------

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Contacto")
	private Long idContacto;

	@Size(min = 10, max = 15)
	@Column(name = "telefono1")
	private String telefonoMovil;
	
	@Size(min = 10, max = 15)
	@Column(name = "telefono2")
	private String telefonoLocal;

	@Size(max = 200)
	@Email
	@Column(name = "correo1")
	private String email;

	@Size(max = 200)
	@Email
	@Column(name = "correo1")
	private String email2;

	// constructores----------------
	public Contacto() {
	}

	public Contacto(Long idContacto, String telefonoMovil, String telefonoLocal, String email, String email2) {
		super();
		this.idContacto = idContacto;
		this.telefonoMovil = telefonoMovil;
		this.telefonoLocal = telefonoLocal;
		this.email = email;
		this.email2 = email2;
	}

	// setters and getters----------

	public Long getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getTelefonoLocal() {
		return telefonoLocal;
	}

	public void setTelefonoLocal(String telefonoLocal) {
		this.telefonoLocal = telefonoLocal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idContacto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		return Objects.equals(idContacto, other.idContacto);
	}

	@Override
	public String toString() {
		return "Contacto [idContacto=" + idContacto + ", telefonoMovil=" + telefonoMovil + ", telefonoLocal="
				+ telefonoLocal + ", email=" + email + ", email2=" + email2 + "]";
	}

}
