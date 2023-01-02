package com.david.tienda.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
public class OpcionesMenu implements Serializable {
	// variables
	private static final long serialVersionUID = 1L;
	// variables
	@ManagedProperty(value = "#{categoriaBean}")
	private CategoriaBean categoriaBean;
	@ManagedProperty(value = "#{productoBean}")
	private ProductoBean productoBean;

	private boolean categoriaTab;
	private int limite;
	private String msgOpc;

	@PostConstruct
	public void inicia() {
		limite = 100;
		categoriaTab = false;
		msgOpc = "Categorias";
	}

	// metodos
	public void tabCambia() {
		categoriaTab = !categoriaTab;
		if (categoriaTab)
			msgOpc = "Productos";
		else
			msgOpc = "Categorias";

		PrimeFaces.current().ajax().update(":productos");
		PrimeFaces.current().ajax().update(":categorias");
		PrimeFaces.current().ajax().update(":opciones");

	}

	public void nuevo() {
		if (categoriaTab) {
			categoriaBean.nuevo();
			PrimeFaces.current().ajax().update(":formulario-categorias");
			PrimeFaces.current().executeScript("PF('dialogoCategoriasForm').show()");
			PrimeFaces.current().resetInputs(":formulario-categorias");

		} else {
			productoBean.nuevo();
			PrimeFaces.current().ajax().update(":formulario-productos");
			PrimeFaces.current().executeScript("PF('dialogoProductosForm').show()");
			PrimeFaces.current().resetInputs(":formulario-productos");

		}

	}

	public void refrescar() {
		if (categoriaTab)
			categoriaBean.refrescar();
		else
			productoBean.refrescar();

	}

	public void estableceLimite() {
		categoriaBean.setLimite(limite);
		productoBean.setLimite(limite);
		productoBean.estableceLimite();
	}

	public void listarTodo() {
		if (categoriaTab)
			categoriaBean.listarTodo();
		else
			productoBean.listarTodo();
	}

	// getters and setters

	public boolean isCategoriaTab() {
		return categoriaTab;
	}

	public void setCategoriaTab(boolean categoriaTab) {
		this.categoriaTab = categoriaTab;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public CategoriaBean getCategoriaBean() {
		return categoriaBean;
	}

	public void setCategoriaBean(CategoriaBean categoriaBean) {
		this.categoriaBean = categoriaBean;
	}

	public ProductoBean getProductoBean() {
		return productoBean;
	}

	public void setProductoBean(ProductoBean productoBean) {
		this.productoBean = productoBean;
	}

	public String getMsgOpc() {
		return msgOpc;
	}

	public void setMsgOpc(String msgOpc) {
		this.msgOpc = msgOpc;
	}

}
