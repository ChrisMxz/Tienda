package com.david.tienda.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.david.tienda.entidades.Pedido;
import com.david.tienda.servicios.ServicioPedido;
import com.david.tienda.servicios.ServicioPedidoImpl;

@ManagedBean
@ViewScoped
public class PedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido Pedido;
	private List<Pedido> listaPedidos;
	private ServicioPedido servicioPedido;
	private boolean bandera;
	private int filtro;
	private int limite;
	private boolean orden;
	private LocalDateTime fecha;
	private String estatus;
	private String textoBuscar;

	// metodos
	@PostConstruct
	public void inicia() {
		servicioPedido = new ServicioPedidoImpl();
		bandera = false;
		orden = true; // ascendente
		limite = 100;
		filtro = 1;
		fecha = null;
		estatus = null;
		listar();
	}

	public void nuevo() {
		Pedido = new Pedido();

	}

	public void listar() {
		// lista segun lo establecido
		buscar();
	}

	public void listarTodo() {
		listaPedidos = servicioPedido.listarTodo();
	}

	public void refrescar() {
		listar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Refrescado"));
		// textoBuscar=null;
		PrimeFaces.current().ajax().update(":messages", ":opciones", ":Pedidos");
	}

	public void guardar() {
		if (!bandera) {
			String msg = "Guardado";
			if (Pedido.getIdPedido() != null)
				msg = "Actualizado";

			servicioPedido.guardar(Pedido);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
			PrimeFaces.current().ajax().update(":messages");
			PrimeFaces.current().executeScript("PF('dialogoPedidosForm').hide()");
			listar();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Verifica tus datos"));
			PrimeFaces.current().ajax().update(":messages");
		}
	}

	public void eliminar() {
		servicioPedido.eliminar(Pedido);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado"));
		PrimeFaces.current().ajax().update(":messages");
		listar();
	}

	public void buscar() {
		listaPedidos = servicioPedido.filtrarPor(filtro, estatus, fecha, textoBuscar, limite, orden);
	}

	public void estableceLimite() {
		String msg = "Limite establecido " + limite + " registros ";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages", ":opciones");
		buscar();
	}

	public void estableceOrden() {
		String msg = "Orden ascendente ";
		if (!orden)
			msg = "Orden descendente ";
		FacesContext.getCurrentInstance().addMessage("Mostrando lista", new FacesMessage(msg));
		PrimeFaces.current().ajax().update(":messages", ":opciones");
		buscar();
	}

	// getters and setters
	public Pedido getPedido() {
		return Pedido;
	}

	public void setPedido(Pedido Pedido) {
		this.Pedido = Pedido;
	}

	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
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

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

}
