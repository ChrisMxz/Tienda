package com.david.tienda.servicios;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class DatosFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	protected List<String> entidadesFederativas;
	protected List<String> municipios;
	protected List<String> colonias;
	protected List<String> codigosPostales;
	protected List<String> formasdePago;
	protected List<String> tiposdeComprobante;
	protected List<String> metodosdePago;
	protected List<String> regimenFiscal;
	protected List<String> usoCFDI;
	protected List<String> clavesdeProducto;
	protected List<String> clavesdeUnidad;
	protected List<String> impuesto;

	@PostConstruct
	public void inicia() {
		cargaTodo();
	}

	public void cargaTodo() {
		System.out.println("--Inicia cargar de datos necesarios--");

		entidadesFederativas = new ArrayList<>();
		municipios = new ArrayList<>();
		colonias = new ArrayList<>();
		codigosPostales = new ArrayList<>();

		formasdePago = new ArrayList<>();
		tiposdeComprobante = new ArrayList<>();
		metodosdePago = new ArrayList<>();
		regimenFiscal = new ArrayList<>();
		usoCFDI = new ArrayList<>();
		clavesdeProducto = new ArrayList<>();
		clavesdeUnidad = new ArrayList<>();
		impuesto = new ArrayList<>();

		// cangando los datos
		entidadesFederativas = cargarDatos(25, 5, 37, false, 2, 0, "Estados");
		municipios = cargarDatos(27, 5, 0, false, 2, 0, "Municipios");
		cargaColonias();
		cargaCodigosPostales();

		formasdePago = cargarDatos(0, 6, 28, true, 0, 1, "Formas de pago");
		tiposdeComprobante = cargarDatos(2, 5, 0, true, 0, 1, "Tipo comprobante");
		metodosdePago = cargarDatos(4, 6, 0, true, 0, 1, "Metodo Pago");
		regimenFiscal = cargarDatos(10, 6, 0, true, 0, 1, "Regimen fiscal");
		usoCFDI = cargarDatos(12, 6, 30, true, 0, 1, "Uso de CFDI");
		clavesdeProducto = cargarDatos(13, 5, 0, true, 0, 1, "Claves de Productos");
		clavesdeUnidad = cargarDatos(14, 6, 0, true, 0, 1, "Claves de unidad");
		impuesto = cargarDatos(16, 5, 0, true, 0, 1, "Impuestos");

		System.out.println("--Datos Cargados--");

	}

	@PreDestroy
	public void desecha() {
		System.out.println("--ELIMINANDO los datos necesarios--");
		entidadesFederativas.clear();
		municipios.clear();
		colonias.clear();
		codigosPostales.clear();
		formasdePago.clear();
		tiposdeComprobante.clear();
		metodosdePago.clear();
		regimenFiscal.clear();
		usoCFDI.clear();
		clavesdeProducto.clear();
		clavesdeUnidad.clear();
		impuesto.clear();
	}

	public void cargaColonias() {
		colonias = cargarDatos(22, 5, 0, false, 2, 0, "Colonias 1");
		colonias.addAll(cargarDatos(23, 5, 0, false, 2, 0, "Colonias 2"));
		colonias.addAll(cargarDatos(24, 5, 0, false, 2, 0, "Colonias 3"));
	}

	public void cargaCodigosPostales() {
		codigosPostales = cargarDatos(22, 5, 0, false, 1, 0, "Codigos postales 1");
		codigosPostales.addAll(cargarDatos(23, 5, 0, false, 1, 0, "Codigos postales 2"));
		codigosPostales.addAll(cargarDatos(24, 5, 0, false, 1, 0, "Codigos postales 3"));
	}

	private List<String> cargarDatos(int pagina, int inicia, int fin, boolean compuesto, int col, int col2,
			String msg) {
		List<String> lista = new ArrayList<>();
		String nombreArchivo = "catCFDI_V_4_09112022.xls";
		String rutaArchivo = "C:\\" + nombreArchivo;
		int numFilas;
		String dato = null, txt = null;

		try (FileInputStream file = new FileInputStream(new File(rutaArchivo))) {
			// leer archivo excel
			Workbook wb = WorkbookFactory.create(file);
			// obtener la hoja que se va leer 25(Estado)
			HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(pagina);

			// obtener las filas de la hoja excel
			numFilas = fin;

			// solo si se requieren todos (int = 0)
			if (fin == 0)
				numFilas = sheet.getLastRowNum();

			// comienza desde la fila del titulo
			for (int i = inicia; i < numFilas; i++) {
				Row fila = sheet.getRow(i);

				txt = verifica(fila, col);
				dato = txt;

				if (compuesto) {
					txt = verifica(fila, col2);
					dato = dato + " - " + txt;
				}

				lista.add(dato);

			}

			System.out.println("Cargados: " + msg);

			file.close();

		} catch (Exception e) {
			System.err.println("Error al cargar " + msg + " : " + e);
			e.getMessage();
		}

		return lista;
	}

	public String verifica(Row fila, int col) {
		String dato = null;
		// dato1
		try {
			dato = fila.getCell(col).getStringCellValue();
		} catch (Exception e) {

			try {
				Long x = (long) fila.getCell(col).getNumericCellValue();
				dato = String.valueOf(x);

			} catch (Exception ex) {

			}
		}

		return dato;

	}

	public List<String> completaEstados(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.entidadesFederativas.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaMunicipios(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.municipios.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaColonias(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.colonias.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaCodigosPostales(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.codigosPostales.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaFormaPago(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.formasdePago.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaTipoComprobante(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.tiposdeComprobante.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaMetodosPago(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.metodosdePago.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaRegimenFiscal(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.regimenFiscal.stream().filter(t -> t.toLowerCase().startsWith(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaUsoCFDI(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.usoCFDI.stream().filter(t -> t.toLowerCase().contains(textoLowerCase)).collect(Collectors.toList());
	}

	public List<String> completaClavesProducto(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.clavesdeProducto.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaClavesUnidad(String texto) {
		String textoLowerCase = texto.toLowerCase();
		return this.clavesdeUnidad.stream().filter(t -> t.toLowerCase().contains(textoLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completaImpuesto(String texto) {

		return this.impuesto.stream().filter(t -> t.contains(texto)).collect(Collectors.toList());
	}

	public List<String> getEntidadesFederativas() {
		return entidadesFederativas;
	}

	public void setEntidadesFederativas(List<String> entidadesFederativas) {
		this.entidadesFederativas = entidadesFederativas;
	}

	public List<String> getFormasdePago() {
		return formasdePago;
	}

	public void setFormasdePago(List<String> formasdePago) {
		this.formasdePago = formasdePago;
	}

	public List<String> getTiposdeComprobante() {
		return tiposdeComprobante;
	}

	public void setTiposdeComprobante(List<String> tiposdeComprobante) {
		this.tiposdeComprobante = tiposdeComprobante;
	}

	public List<String> getMetodosdePago() {
		return metodosdePago;
	}

	public void setMetodosdePago(List<String> metodosdePago) {
		this.metodosdePago = metodosdePago;
	}

	public List<String> getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(List<String> regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	public List<String> getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(List<String> usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	public List<String> getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(List<String> impuesto) {
		this.impuesto = impuesto;
	}

	public List<String> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<String> municipios) {
		this.municipios = municipios;
	}

	public List<String> getColonias() {
		return colonias;
	}

	public void setColonias(List<String> colonias) {
		this.colonias = colonias;
	}

	public List<String> getCodigosPostales() {
		return codigosPostales;
	}

	public void setCodigosPostales(List<String> codigosPostales) {
		this.codigosPostales = codigosPostales;
	}

	public List<String> getClavesdeProducto() {
		return clavesdeProducto;
	}

	public void setClavesdeProducto(List<String> clavesdeProducto) {
		this.clavesdeProducto = clavesdeProducto;
	}

	public List<String> getClavesdeUnidad() {
		return clavesdeUnidad;
	}

	public void setClavesdeUnidad(List<String> clavesdeUnidad) {
		this.clavesdeUnidad = clavesdeUnidad;
	}

}
