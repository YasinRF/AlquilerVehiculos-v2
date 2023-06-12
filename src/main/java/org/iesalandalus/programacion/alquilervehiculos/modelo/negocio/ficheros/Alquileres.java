package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	private static final File FICHERO_ALQUILERES = new File(String.format("%s%s%s", "datos", File.separator, "alquileres.xml"));
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "alquileres";
	private static final String ALQUILER = "alquiler";
	private static final String CLIENTE = "cliente";
	private static final String VEHICULO = "vehiculo";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";

	private List<Alquiler> coleccionAlquileres;
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

		List<Alquiler> alquileresCliente = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				alquileresCliente.add(alquiler);
			}
		}
		return alquileresCliente;
	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {

		List<Alquiler> alquileresVehiculo = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				alquileresVehiculo.add(alquiler);
			}
		}
		return alquileresVehiculo;
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
					throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
				}
				if (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler)) {
					throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
				}
			}
		}

	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}

		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);

	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Iterator<Alquiler> iter = get(cliente).iterator();
		Alquiler alquilerAbierto = null;
		while (iter.hasNext() && alquilerAbierto == null) {
			Alquiler alquiler = iter.next();
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto = alquiler;
			}
		}
		return alquilerAbierto;
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Iterator<Alquiler> iteradorVehiculo = get(vehiculo).iterator();
		Alquiler alquilerAbierto = null;
		while (iteradorVehiculo.hasNext() && alquilerAbierto == null) {
			Alquiler alquiler = iteradorVehiculo.next();
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto = alquiler;
			}
		}
		return alquilerAbierto;
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		Alquiler alquilerDev = getAlquilerAbierto(cliente);
		if (alquilerDev == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		alquilerDev.devolver(fechaDevolucion);

	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Alquiler alquilerDev = getAlquilerAbierto(vehiculo);
		if (alquilerDev == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquilerDev.devolver(fechaDevolucion);

	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}

		int posicionDeAlquiler = coleccionAlquileres.indexOf(alquiler);
		Alquiler alquilerBuscado = null;
		if (posicionDeAlquiler != -1) {
			alquilerBuscado = coleccionAlquileres.get(posicionDeAlquiler);
		}
		return alquilerBuscado;
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
	public void comenzar() {
		Document documentoXml = UtilidadesXml.leerXmlDeFichero(FICHERO_ALQUILERES);
		if (documentoXml != null) {
			leerDom(documentoXml);
			System.out.println("El documento de alquileres ha sido leído correctamente.");
		} else {
			System.out.println("Error: El documento de alquileres no ha sido leído correctamente.");
		}
	}

	private void leerDom(Document documentoXml) {

		NodeList nodosDeAlquileres = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < nodosDeAlquileres.getLength(); i++) {
			Node alquiler = nodosDeAlquileres.item(i);
			if (alquiler.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getAlquiler((Element) alquiler));
				} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
					System.out.println("Error al insertar de alquiler Nº " + i + "," + e.getMessage());
				}
			}
		}
	}

	private Alquiler getAlquiler(Element elemento) throws OperationNotSupportedException {

		Alquiler alquiler = null;
		Cliente cliente = null;
		Vehiculo vehiculo = null;
		String dni = elemento.getAttribute(CLIENTE);
		cliente = Cliente.getClienteConDni(dni);
		try {
			cliente = Clientes.getInstancia().buscar(cliente);
		} catch (NullPointerException e) {
			System.out.print(e.getMessage());
		}
		String fechaAlquiler = elemento.getAttribute(FECHA_ALQUILER);
		String matricula = elemento.getAttribute(VEHICULO);
		vehiculo = Vehiculo.getVehiculoConMatricula(matricula);
		try {
			vehiculo = Vehiculos.getInstancia().buscar(vehiculo);
		} catch (NullPointerException e) {
			System.out.print(e.getMessage());
		}
		alquiler = new Alquiler(cliente, vehiculo, LocalDate.parse(fechaAlquiler, FORMATO_FECHA));

		if (elemento.hasAttribute(FECHA_DEVOLUCION)) {
			String fechaDevolucion = elemento.getAttribute(FECHA_DEVOLUCION);
			alquiler.devolver(LocalDate.parse(fechaDevolucion, FORMATO_FECHA));
		}
		return alquiler;
	}

	@Override
	public void terminar() {
		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_ALQUILERES);

	}

	private Document crearDom() {
		Document documentoXml = UtilidadesXml.crearConstructorDocumentoXml().newDocument();
		Element elementoAlquileres = documentoXml.createElement(RAIZ);
		documentoXml.appendChild(elementoAlquileres);

		for (Alquiler alquiler : coleccionAlquileres) {
			Element elementoAlquiler = getElemento(documentoXml, alquiler);
			documentoXml.getDocumentElement().appendChild(elementoAlquiler);
		}
		return documentoXml;
	}

	private Element getElemento(Document documentoXml, Alquiler alquiler) {

		Element elemento = documentoXml.createElement(ALQUILER);

		elemento.setAttribute(CLIENTE, alquiler.getCliente().getDni());
		elemento.setAttribute(VEHICULO, alquiler.getVehiculo().getMatricula());
		elemento.setAttribute(FECHA_ALQUILER, alquiler.getFechaAlquiler().format(FORMATO_FECHA));
		
		if (alquiler.getFechaDevolucion() != null) {
			elemento.setAttribute(FECHA_DEVOLUCION, alquiler.getFechaDevolucion().format(FORMATO_FECHA));
		}
		return elemento;
	}

}