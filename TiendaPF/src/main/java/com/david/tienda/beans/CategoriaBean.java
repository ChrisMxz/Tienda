package com.david.tienda.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Categoria;
import com.david.tienda.servicios.ServicioCategoriaImpl;
import com.david.tienda.util.MensajeGrowl;

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
	private boolean orden;
	private ServicioCategoriaImpl servicioCategoria;

	// metodos
	@PostConstruct
	public void inicia() {
		servicioCategoria = new ServicioCategoriaImpl();
		filtro = 1;
		limite = 100;
		orden = false; // ascendente
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
		MensajeGrowl.msgInformacion("Refrescando", "Listado");
		PrimeFaces.current().ajax().update(":messages", ":opciones", ":categorias");
	}

	public void guardar() {

		String msg = "Guardado";
		if (categoria.getIdCategoria() != null)
			msg = "Actualizado";

		servicioCategoria.guardar(categoria);

		MensajeGrowl.msgInformacion(msg, categoria.getNombre());
		PrimeFaces.current().executeScript("PF('dialogoCategoriasForm').hide()");
		listar();
	}

	public void eliminar() {
		String msg;
		try {
			msg = "Categoria " + categoria.getNombre();
			servicioCategoria.eliminar(categoria);
			MensajeGrowl.msgInformacion("Eliminado", msg);
			listar();
		} catch (Exception e) {
			System.err.println("ERROR categoria: "+e.getMessage());
			MensajeGrowl.msgError("Error", "Error al eliminar el categoria");
		}

	}

	public void buscar() {
		try {
			listaCategorias = servicioCategoria.listarPor(filtro, textoBuscar, limite, orden);
		} catch (Exception e) {
			MensajeGrowl.msgError("Error", "Error al listar categorias");
		}

	}

	public void estableceLimite() {
		String msg = "Limite establecido " + limite + " registros ";
		MensajeGrowl.msgInformacion(msg, "Registros " + limite);
		PrimeFaces.current().ajax().update(":opciones");
		buscar();
	}

	public void estableceOrden() {
		String msg = "Orden ascendente ";
		if (!orden)
			msg = "Orden descendente ";
		MensajeGrowl.msgInformacion(msg, "Mostrando lista " + msg);
		PrimeFaces.current().ajax().update(":opciones");
		buscar();
	}

	public void btnEditar(Categoria x) {
		this.categoria = x;
	}

	public void btnEliminar(Categoria x) {
		this.categoria = x;
		eliminar();
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

	public boolean isOrden() {
		return orden;
	}

	public void setOrden(boolean orden) {
		this.orden = orden;
	}

}
