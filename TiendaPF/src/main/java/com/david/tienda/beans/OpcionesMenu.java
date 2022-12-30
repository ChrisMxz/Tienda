package com.david.tienda.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;

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

	// metodos
	public void tabCambia() {
		categoriaTab = !categoriaTab;
	}

	public void tabCierra() {
		categoriaTab = false;
		PrimeFaces.current().ajax().update(":opciones");
	}

	public void mostrarCategorias() {
		categoriaTab = false;
		PrimeFaces.current().ajax().update(":opciones");

		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent componente = viewRoot.findComponent("tab");
		TabView tabView = (TabView) componente;
		tabView.setActiveIndex(1);
		PrimeFaces.current().ajax().update(":contenedor:tab");
	}

	public void nuevo() {
		if (categoriaTab) {
			System.out.println("nueva categoria");
			categoriaBean.nuevo();
			PrimeFaces.current().ajax().update(":formulario-categorias");
			PrimeFaces.current().executeScript("PF('dialogoCategoriasForm').show()");
			PrimeFaces.current().resetInputs(":formulario-categorias");

		} else {
			System.out.println("nuevo producto");
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
		if (categoriaTab) {
			categoriaBean.setLimite(limite);
			categoriaBean.estableceLimite();
		} else {
			productoBean.setLimite(limite);
			productoBean.estableceLimite();
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

}
