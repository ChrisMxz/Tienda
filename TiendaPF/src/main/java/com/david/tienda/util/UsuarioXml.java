	package com.david.tienda.util;

import java.io.FileWriter;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.david.tienda.entidades.Usuario;

public class UsuarioXml {

	private Usuario u;
	Document doc;
	XMLOutputter xml = new XMLOutputter();
	String xmlContenido = null;

	public void crearXML() {
		Element Usuario = new Element("usuario");
		doc = new Document(Usuario);

		// datos usuario
		Element idUsuario = new Element("id");
		idUsuario.setText(String.valueOf(u.getIdUsuario()));

		Element nivel = new Element("nivel");
		nivel.setText(String.valueOf(u.getNivel()));

		Element nombre = new Element("nombre");
		nombre.setText(u.getNombre());

		Element rfc = new Element("rfc");
		rfc.setText(u.getRfc());

		Element app = new Element("apellido-paterno");
		app.setText(u.getAppaterno());

		Element apm = new Element("apellido-materno");
		apm.setText(u.getApmaterno());

		Element edad = new Element("edad");
		edad.setText(String.valueOf(u.getEdad()));

		Element sexo = new Element("sexo");
		sexo.setText(u.getSexo());

		Element usuario = new Element("usuario");
		usuario.setText(u.getUsername());

		Element pass = new Element("contraseña");
		pass.setText(u.getUsername());

		Usuario.addContent(idUsuario);
		Usuario.addContent(nivel);
		Usuario.addContent(nombre);
		Usuario.addContent(app);
		Usuario.addContent(apm);
		Usuario.addContent(edad);
		Usuario.addContent(sexo);
		Usuario.addContent(usuario);
		Usuario.addContent(pass);

		// elementos hijos

		// direccion

		Element Direccion = new Element("direccion");

		Element idDireccion = new Element("id");
		idDireccion.setText(String.valueOf(u.getDireccion().getId_direccion()));

		Element estado = new Element("entidad-federariva");
		estado.setText(u.getDireccion().getEntidadFederativa());

		Element municipio = new Element("municipio");
		municipio.setText(u.getDireccion().getMunicipio());

		Element colonia = new Element("colonia");
		colonia.setText(u.getDireccion().getColonia());

		Element cp = new Element("codigo-postal");
		cp.setText(u.getDireccion().getCodigoPostal());

		Element calle = new Element("calle");
		calle.setText(u.getDireccion().getCalle());

		Element numExt = new Element("numero-exterior");
		numExt.setText(u.getDireccion().getNumExterior());

		Element numInt = new Element("numero-interior");
		numInt.setText(u.getDireccion().getNumInterior());

		Element descripcion = new Element("descripcion");
		descripcion.setText(u.getDireccion().getDescripcion());

		Direccion.addContent(idDireccion);
		Direccion.addContent(estado);
		Direccion.addContent(municipio);
		Direccion.addContent(colonia);
		Direccion.addContent(cp);
		Direccion.addContent(calle);
		Direccion.addContent(numExt);
		Direccion.addContent(numInt);
		Direccion.addContent(descripcion);

		Usuario.addContent(Direccion);

		// contacto

		Element Contacto = new Element("contacto");

		Element idContacto = new Element("id");
		idContacto.setText(String.valueOf(u.getContacto().getIdContacto()));

		Element tel1 = new Element("telefono-1");
		tel1.setText(u.getContacto().getTelefonoMovil());

		Element tel2 = new Element("telefono-2");
		tel2.setText(u.getContacto().getTelefonoLocal());

		Element correo1 = new Element("correo-1");
		correo1.setText(u.getContacto().getEmail());

		Element correo2 = new Element("correo-2");
		correo2.setText(u.getContacto().getEmail2());

		Contacto.addContent(idContacto);
		Contacto.addContent(tel1);
		Contacto.addContent(tel2);
		Contacto.addContent(correo1);
		Contacto.addContent(correo2);

		Usuario.addContent(Contacto);

		xml.setFormat(Format.getPrettyFormat());
		try {
			FileWriter x = new FileWriter("Usuario.xml");
			xml.output(doc, x);
			descarga();
			x.close();
			System.out.println("xml Usuario generado");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void descarga() {

		String fileName = "prueba.pdf";
		String contentType = "application/pdf";

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setContentType(contentType);

		try {
			ServletOutputStream out = response.getOutputStream();
			xml.output(doc, out);
			response.getOutputStream().flush();
			System.out.println("Descargado");

		} catch (IOException e) {

			e.printStackTrace();
		}

		FacesContext.getCurrentInstance().responseComplete();

	}

	public Usuario getU() {
		return u;
	}

	public void setU(Usuario u) {
		this.u = u;
	}

}
