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
	private boolean orden;
	private String msgOpc;

	@PostConstruct
	public void inicia() {
		limite = 100;
		orden = true;
		categoriaTab = false;
		msgOpc = "Categorias";
	}

	// metodos
	public void tabCambia() {
		categoriaTab = !categoriaTab;
		if (categoriaTab) {// esta en categorias
			msgOpc = "Productos";
			limite = categoriaBean.getLimite();
			orden = categoriaBean.isOrden();
		} else {// esta en Productos
			msgOpc = "Categorias";
			limite = productoBean.getLimite();
			orden = productoBean.isOrden();
		}

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

		if (categoriaTab) {// esta en categorias
			categoriaBean.setLimite(limite);
			categoriaBean.estableceLimite();
		} else {// esta en Productos
			productoBean.setLimite(limite);
			productoBean.estableceLimite();
		}

	}

	public void estableceOrden() {
		if (categoriaTab) {// esta en categorias
			categoriaBean.setOrden(orden);
			categoriaBean.estableceOrden();
		} else {// esta en Productos
			productoBean.setOrden(orden);
			productoBean.estableceOrden();
		}

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

	public boolean isOrden() {
		return orden;
	}

	public void setOrden(boolean orden) {
		this.orden = orden;
	}

}
