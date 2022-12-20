package com.david.tienda.entidades;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {
	// atributos---------------------

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@NotNull
	@Max(3)
	@Min(1)
	private int nivel;

	@NotEmpty
	@Size(min=4,max = 50)
	private String nombre;
	
	@NotNull
	@Size(min=4,max = 50)
	private String appaterno;
	
	@NotNull
	@Size(min=4,max = 50)
	private String apmaterno;
	
	@NotNull
	@Max(150)
	private int edad;
	
	@Size(min=1,max = 1)
	private String sexo;
	
	@Size(min=12,max = 13)
	private String rfc;
	
	@NotNull
	@Size(min=4,max = 20)
	private String username;
	
	@NotNull
	@Size(min=8,max = 20)
	private String password;
	
	@JoinColumn(name="contacto_id", referencedColumnName = "id_contacto")
    @ManyToOne(cascade = CascadeType.ALL)
	@Valid
	private Contacto contacto;
	
	@JoinColumn(name="direccion_id", referencedColumnName = "id_direccion")
    @ManyToOne(cascade = CascadeType.ALL)
	@Valid
	private Direccion direccion;
	
	@NotNull
	private LocalDateTime fechaRegistro;

	// constructores----------------
	public Usuario() {
	}

	public Usuario(Long idUsuario, int nivel, String nombre, String appaterno, String apmaterno, int edad, String sexo,
			String rfc, String username, String password, Contacto contacto, Direccion direccion,
			LocalDateTime fechaRegistro) {
		super();
		this.idUsuario = idUsuario;
		this.nivel = nivel;
		this.nombre = nombre;
		this.appaterno = appaterno;
		this.apmaterno = apmaterno;
		this.edad = edad;
		this.sexo = sexo;
		this.rfc = rfc;
		this.username = username;
		this.password = password;
		this.contacto = contacto;
		this.direccion = direccion;
		this.fechaRegistro = fechaRegistro;
	}

	// setters and getters----------

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAppaterno() {
		return appaterno;
	}

	public void setAppaterno(String appaterno) {
		this.appaterno = appaterno;
	}

	public String getApmaterno() {
		return apmaterno;
	}

	public void setApmaterno(String apmaterno) {
		this.apmaterno = apmaterno;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario, rfc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(idUsuario, other.idUsuario) && Objects.equals(rfc, other.rfc);
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nivel=" + nivel + ", nombre=" + nombre + ", appaterno="
				+ appaterno + ", apmaterno=" + apmaterno + ", edad=" + edad + ", sexo=" + sexo + ", rfc=" + rfc
				+ ", username=" + username + ", password=" + password + ", contacto=" + contacto + ", direccion="
				+ direccion + ", fechaRegistro=" + fechaRegistro + "]";
	}

}
