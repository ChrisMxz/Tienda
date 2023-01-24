package com.david.tienda.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.Valid;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Producto;
import com.david.tienda.servicios.ServicioProducto;
import com.david.tienda.servicios.ServicioProductoImpl;
import com.david.tienda.util.MensajeGrowl;
import com.david.tienda.util.ToXML;

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
	private boolean orden;
	private Long categoria;
	private String textoBuscar;

	// metodos
	@PostConstruct
	public void inicia() {
		servicioProducto = new ServicioProductoImpl();
		bandera = false;
		orden = false; // ascendente
		limite = 100;
		filtro = 2;
		categoria = null;
		listar();
	}

	public void nuevo() {
		producto = new Producto();

	}

	public void listar() {
		// lista segun lo establecido
		buscar();
	}

	public void listarTodo() {
		listaProductos = servicioProducto.listarTodo();
	}

	public void refrescar() {
		listar();
		MensajeGrowl.msgInformacion("Refrescando", "Listado");
		PrimeFaces.current().ajax().update(":opciones", ":productos");
	}

	public void guardar() {
		if (!bandera) {
			String msg = "Guardado";
			if (producto.getIdProducto() != null)
				msg = "Actualizado";

			servicioProducto.guardar(producto);

			MensajeGrowl.msgInformacion(msg, "Producto " + msg);
			PrimeFaces.current().executeScript("PF('dialogoProductosForm').hide()");
			listar();
		} else {
			MensajeGrowl.msgAdvertencia("Verifica tus datos", "Revisa el formulario");
		}
	}

	public void eliminar() {
		try {
			servicioProducto.eliminar(producto);
			listar();
			MensajeGrowl.msgInformacion("Eliminado", "Producto Eliminado");
		} catch (Exception e) {
			System.err.println("ERROR producto: "+e.getMessage());
			MensajeGrowl.msgError("Error", "Error al eliminar el producto");
		}

	}

	public void buscar() {
		try {
			listaProductos = servicioProducto.filtrarPor(filtro, categoria, textoBuscar, limite, orden);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			MensajeGrowl.msgError("Error", "Error al listar Productos");
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

	public void exportar() {
		try {
			ToXML.convierte(producto);
			ToXML.descarga("producto" + producto.getIdProducto());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			MensajeGrowl.msgError("Error", "Error al exportar en XML");
		}
	}

	public void btnEditar(Producto x) {
		this.producto = x;
	}

	public void btnEliminar(Producto x) {
		this.producto = x;
		eliminar();
	}

	public void btnExportar(Producto x) {
		this.producto = x;
		exportar();
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

	public Long getCategoria() {
		return categoria;
	}

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public boolean isOrden() {
		return orden;
	}

	public void setOrden(boolean orden) {
		this.orden = orden;
	}

}
