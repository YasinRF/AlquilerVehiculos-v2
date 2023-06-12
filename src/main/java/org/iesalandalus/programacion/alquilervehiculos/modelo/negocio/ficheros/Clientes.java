package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Clientes implements IClientes {

	private static final File FICHERO_CLIENTES = new File(String.format("%s%s%s", "datos", File.separator, "clientes.xml"));
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String TELEFONO = "telefono";

	private List<Cliente> coleccionClientes;
	private static Clientes instancia;
	private Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	static Clientes getInstancia() {
		if (instancia == null) {
			instancia = new Clientes();
		}
		return instancia;
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
		return indice == -1 ? null : coleccionClientes.get(indice);
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
		Cliente clienteBusc = buscar(cliente);
		if (clienteBusc==null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		} else {
			if (nombre != null && !nombre.isBlank()) {
				clienteBusc.setNombre(nombre);
			}
			if (telefono != null && !telefono.isBlank()) {
				clienteBusc.setTelefono(telefono);
			}
		}

	}

	@Override
	public void comenzar() {
		Document documentoXml = UtilidadesXml.leerXmlDeFichero(FICHERO_CLIENTES);
        if (documentoXml != null) {
            leerDom(documentoXml);
            System.out.println("El documento de clientes ha sido leído correctamente.");
		}else {
			System.out.println("Error: El documento de clientes no ha sido leído correctamente.");
		}
	}

	private void leerDom(Document documentoXml) {
		NodeList nodosClientes  = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < nodosClientes.getLength(); i++) {
			Node cliente = nodosClientes.item(i);
			if (cliente.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getCliente((Element) cliente));
				} catch (Exception e) {
					System.out.println("Error al insertar de cliente: Nº "+i+", " + e.getMessage());
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
		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_CLIENTES);
	}

	private Document crearDom() {
	    Document documentoXml = UtilidadesXml.crearConstructorDocumentoXml().newDocument();
		Element elementoClientes = documentoXml.createElement(RAIZ);
		documentoXml.appendChild(elementoClientes);
		for (Cliente cliente : coleccionClientes) {
		    Element elementoCliente = getElemento(documentoXml, cliente);
		    documentoXml.getDocumentElement().appendChild(elementoCliente);
		}

	    return documentoXml;

	}

	private Element getElemento(Document documentoXml, Cliente cliente) {
		Element elementoCliente = documentoXml.createElement(CLIENTE);
		elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
		elementoCliente.setAttribute(DNI, cliente.getDni());
		elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());

		return elementoCliente;
	}

}