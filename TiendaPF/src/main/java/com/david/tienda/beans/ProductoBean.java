package com.david.tienda.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.Valid;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Producto;
import com.david.tienda.servicios.ServicioProducto;
import com.david.tienda.servicios.ServicioProductoImpl;

@ManagedBean
@ViewScoped
public class ProductoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	private Producto producto;
	private List<Producto> listaProductos;
	private ServicioProducto servicioProducto;
	private boolean bandera;
	private int filtro;
	private int limite;
	private Long categoria;
	private String textoBuscar;

	// metodos
	@PostConstruct
	public void inicia() {
		System.out.println("Inicia Producto Bean");
		servicioProducto = new ServicioProductoImpl();
		bandera = false;
		limite = 100;
		filtro = 1;
		categoria = null;
		listar();
	}

	public void nuevo() {
		producto = new Producto();
	}

	public void listar() {
		// lista segun lo establecido
		listarTodo();
	}

	public void listarTodo() {
		listaProductos = servicioProducto.listarTodo();
	}

	public void refrescar() {
		listar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Refrescado"));
		// textoBuscar=null;
		PrimeFaces.current().ajax().update(":productos");
	}

	public void guardar() {
		if (!bandera) {
			String msg = "Guardado";
			if (producto.getIdProducto() != null)
				msg = "Actualizado";

			servicioProducto.guardar(producto);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
			PrimeFaces.current().ajax().update(":formulario-productos:msg");
			PrimeFaces.current().executeScript("PF('dialogoForm').hide()");
			listar();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Verifica tus datos"));
			PrimeFaces.current().ajax().update(":productos");
		}
	}

	public void eliminar() {
		servicioProducto.eliminar(producto);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado"));
		PrimeFaces.current().ajax().update(":productos:messages");
		listar();
	}

	public void buscar() {

		listaProductos = servicioProducto.filtrarPor(filtro, categoria, textoBuscar, limite);

	}

	public void estableceLimite() {
		String msg = "Limite establecido " + limite + " registros ";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":productos:messages", ":opciones:menu-opciones");
		buscar();
	}

	// getters and setters
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	public int getFiltro() {
		return filtro;
	}

	public void setFiltro(int filtro) {
		this.filtro = filtro;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public long getCategoria() {
		return categoria;
	}

	public void setCategoria(long categoria) {
		this.categoria = categoria;
	}

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

}
