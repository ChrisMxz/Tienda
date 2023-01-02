package com.david.tienda.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Categoria;
import com.david.tienda.servicios.ServicioCategoriaImpl;

@ManagedBean
@ViewScoped
public class CategoriaBean implements Serializable {
	// variables
	private static final long serialVersionUID = 1L;

	private Categoria categoria;
	private List<Categoria> listaCategorias;
	private String textoBuscar;
	private int limite;
	private int filtro;
	private ServicioCategoriaImpl servicioCategoria;

	// metodos
	@PostConstruct
	public void inicia() {
		servicioCategoria = new ServicioCategoriaImpl();
		filtro = 1;
		limite = 100;
		listar();
	}

	public void nuevo() {
		categoria = new Categoria();
	}

	public void listar() {
		// lista segun lo establecido
		buscar();
	}

	public void listarTodo() {
		listaCategorias = servicioCategoria.listarTodo();
	}

	public void refrescar() {
		listar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Refrescado"));
		PrimeFaces.current().ajax().update(":messages", ":opciones", ":categorias");
	}

	public void guardar() {

		String msg = "Guardado";
		if (categoria.getIdCategoria() != null)
			msg = "Actualizado";

		servicioCategoria.guardar(categoria);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages");
		PrimeFaces.current().executeScript("PF('dialogoCategoriasForm').hide()");
		listar();
	}

	public void eliminar() {
		servicioCategoria.eliminar(categoria);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado"));
		PrimeFaces.current().ajax().update(":messages");
		listar();
	}

	public void buscar() {
		listaCategorias = servicioCategoria.listarPor(filtro, textoBuscar, limite);
	}

	public void estableceLimite() {
		String msg = "Limite establecido " + limite + " registros ";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages", ":opciones");
		buscar();
	}

	public void ver() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(categoria.toString()));
		PrimeFaces.current().ajax().update(":messages");
	}

	// getters and setters
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int getFiltro() {
		return filtro;
	}

	public void setFiltro(int filtro) {
		this.filtro = filtro;
	}

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

}
