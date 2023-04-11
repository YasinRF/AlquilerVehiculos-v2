package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	// ArrayList

	private List<Alquiler> coleccionAlquileres;
	
	private static final File FICHEROS_ALQUILERES = new File(String.format("%s%s%s", "datos",File.separator,"alquileres.xml"));
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
	private static final String RAIZ = "alquileres";
	private static final String ALQUILER = "alquiler";
	private static final String VEHICULO = "vehiculo";
	private static final String CLIENTE = "cliente";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";
	private static Alquileres instancia;

	private Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}
	
	static Alquileres getInstancia() {
		if (instancia == null) {
			instancia = new Alquileres();
		}
		return instancia;

	}

	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				lista.add(alquiler);
			}
		}
		return lista;
	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				lista.add(alquiler);
			}
		}
		return lista;
	}

	@Override
	public int getCantidad() {
		return coleccionAlquileres.size();
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler alquiler : get()) {
			if (alquiler.getCliente().equals(cliente)) {
				if (alquiler.getFechaDevolucion() == null) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				}
				if (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				}
			}

			if (alquiler.getVehiculo().equals(vehiculo)) {
				if (alquiler.getFechaDevolucion() == null) {
					throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
				}
				if (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler)) {
					throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
				}
			}
		}
		

	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Iterator<Alquiler> iteradorCliente = get(cliente).iterator();
		Alquiler alquilerAbierto1 = null;
		while (iteradorCliente.hasNext()) {
			Alquiler alquiler = iteradorCliente.next();
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto1 = alquiler;
			}
		}
		return alquilerAbierto1;
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Alquiler clienteAbierto = getAlquilerAbierto(cliente);
		if (clienteAbierto == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		clienteAbierto.devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Iterator<Alquiler> iteradorVehiculo = get(vehiculo).iterator();
		Alquiler alquilerAbierto2 = null;
		while (iteradorVehiculo.hasNext()) {
			Alquiler alquiler = iteradorVehiculo.next();
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto2 = alquiler;
			}
		}
		return alquilerAbierto2;
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Alquiler vehiculoAbierto = getAlquilerAbierto(vehiculo);
		if (vehiculoAbierto == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		vehiculoAbierto.devolver(fechaDevolucion);

	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {

		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!coleccionAlquileres.contains(alquiler)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquiler);
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		int indice = coleccionAlquileres.indexOf(alquiler);
		Alquiler alquilerBuscado = null; 
		if (indice != -1) {
			alquilerBuscado = coleccionAlquileres.get(getCantidad());
		}
		return alquilerBuscado;
	}
	
	@Override
	public void comenzar() {

		Document documento = UtilidadesXml.leerXmlDeFichero(FICHEROS_ALQUILERES);

		if (documento != null) {

			System.out.println("El fichero XML se ha leido correctamente");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero: %s. %n", FICHEROS_ALQUILERES);
		}

	}
	
	
	private void leerDom(Document documentoXml) {

		NodeList alquileres = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < alquileres.getLength(); i++) {
			Node alquiler = alquileres.item(i);
			if (alquiler.getNodeType() == Node.ELEMENT_NODE) {

				try {
					insertar(getAlquiler((Element) alquiler)); 
				} catch (OperationNotSupportedException | NullPointerException e) {

					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	private Alquiler getAlquiler(Element elemento) {
		String cliente = elemento.getAttribute(CLIENTE);
		Cliente buscar_cliente = Clientes.getInstancia().buscar(Cliente.getClienteConDni(cliente));
		String fechaAlquiler = elemento.getAttribute(FECHA_ALQUILER);
		LocalDate localDate = LocalDate.parse(fechaAlquiler, FORMATO_FECHA);
		String fecha_devolucion = elemento.getAttribute(FECHA_DEVOLUCION);
		LocalDate formato_fechaDevolucion = LocalDate.parse(fecha_devolucion, FORMATO_FECHA);
		String vehiculo = elemento.getAttribute(VEHICULO);
		Vehiculo buscar_vehiculo = Vehiculos.getInstancia().buscar(Vehiculo.getVehiculoConMatricula(vehiculo));
		
		if (buscar_vehiculo == null) {
			throw new NullPointerException("ERROR: El vehiculo buscado no puede ser nulo.");
		}
		
		if (buscar_cliente == null) {
			throw new NullPointerException("ERROR: El cliente buscado no puede ser nulo.");
		}
		  
		return new Alquiler(buscar_cliente, buscar_vehiculo, localDate);
	}
	
	
	
	@Override
	public void terminar() {

		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHEROS_ALQUILERES);

	}
	
	private Document crearDom() {

		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Alquiler alquiler : coleccionAlquileres) {
				Element elementoAlquiler = getElemento(documentoXml, alquiler);
				documentoXml.getDocumentElement().appendChild(elementoAlquiler);
			}
		}
		return documentoXml;

	}
	
	
	private Element getElemento(Document documentoXml, Alquiler alquiler) {

		Element elementoAlquiler = documentoXml.createElement(ALQUILER);
		elementoAlquiler.setAttribute(CLIENTE, alquiler.getCliente().getDni());
		elementoAlquiler.setAttribute(VEHICULO, alquiler.getVehiculo().getMatricula());
		elementoAlquiler.setAttribute(FECHA_ALQUILER, alquiler.getFechaAlquiler().format(FORMATO_FECHA));
		return elementoAlquiler;

	}

}
