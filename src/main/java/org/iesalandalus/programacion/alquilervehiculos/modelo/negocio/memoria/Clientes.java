package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Clientes implements IClientes {
	
	//Array list

	private List<Cliente> coleccionClientes;
	
	private static final File FICHERO_CLIENTES = new File(String.format("datos%sclientes.xml", File.separator));
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String TELEFONO = "telefono";
	private static Clientes instancia;
	
	
	static Clientes getInstancia() {
		if (instancia == null) {
			instancia = new Clientes();
		}
		return instancia;

	}
	
	private Clientes() {
		coleccionClientes = new ArrayList<>();
	}
	
	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);
	}


	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}

		if (coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}

		coleccionClientes.add(cliente);
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		int indice = coleccionClientes.indexOf(cliente);
		Cliente clienteBuscado = null; 
		if (indice != -1) {
			clienteBuscado = coleccionClientes.get(indice);
		}
		return clienteBuscado;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (!coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
		coleccionClientes.remove(cliente);
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		Cliente clienteMod = buscar(cliente);
		if (clienteMod == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		} else {
			if (nombre != null && !nombre.trim().isEmpty()) {
				clienteMod.setNombre(nombre);
			}

			if (telefono != null && !telefono.trim().isEmpty()) {
				clienteMod.setTelefono(telefono);
			}
		}

	}
	@Override
	public void comenzar() {
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_CLIENTES);
		if (documento == null) {
			System.out.println("ERROR: No se puede leer un documento nulo.");
		} else {
			leerDom(documento);
			System.out.println("ERROR: El documento se ha leído correctamente.");
		}

	}

	private void leerDom(Document documentoXml) {


		NodeList clientesNode = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < clientesNode.getLength(); i++) {
			Node nodoCliente = clientesNode.item(i);
			if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {

				try {
					insertar(getCliente((Element) nodoCliente));
				} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
					System.out.println("ERROR: Hay un error en el cliente " + i + ": " + e.getMessage());
				}
			}
		}

	}

	private Cliente getCliente(Element elemento) {
		String nombre = elemento.getAttribute(NOMBRE);
		String dni = elemento.getAttribute(DNI);
		String telefono = elemento.getAttribute(TELEFONO);
		return new Cliente(nombre, dni, telefono);
	}

	@Override
	public void terminar() {
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_CLIENTES);

	}

	public Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Cliente clientes : getInstancia().get()) {
				Element elementoCliente = getElemento(documentoXml, clientes);
				documentoXml.getDocumentElement().appendChild(elementoCliente);
			}
		}
		return documentoXml;
	}

	public Element getElemento(Document documentoXml, Cliente cliente) {
		Element elemento = documentoXml.createElement(CLIENTE);

		elemento.setAttribute(NOMBRE, cliente.getNombre());
		elemento.setAttribute(DNI, cliente.getDni());
		elemento.setAttribute(TELEFONO, cliente.getTelefono());

		return elemento;
	}

}
