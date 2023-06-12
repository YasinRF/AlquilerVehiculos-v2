package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	public VistaTexto() {
		Accion.setVista(this);
	}

	public void comenzar() {
		Accion accion;
		do {
			Consola.mostrarMenuAcciones();
			accion = Consola.elegirAccion();
			accion.ejecutar();
			System.out.println();
		} while (accion != Accion.SALIR);
	}

	public void terminar() {
		System.out.print("Hasta la próxima!!!");
		System.exit(0);
	}

	public void insertarCliente() {
		try {
			Consola.mostrarCabecera("Opcion 1 -> Insertar Cliente");
			System.out.println();
			getControlador().insertar(Consola.leerCliente());
			System.out.print("El cliente se ha insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void insertarVehiculo() {
		try {
			Consola.mostrarCabecera("Opcion 2 -> Insertar Vehículo");
			System.out.println();
			getControlador().insertar(Consola.leerVehiculo());
			System.out.print("El vehiculo se ha insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void insertarAlquiler() {
		try {
			Consola.mostrarCabecera("Opcion 3 -> Insertar Alquiler");
			System.out.println();
			getControlador().insertar(Consola.leerAlquiler());
			System.out.print("El Alquiler se ha insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void buscarCliente() {
		try {
			Consola.mostrarCabecera("Opcion 4 -> Buscar Cliente");
			System.out.println();
			System.out.print(getControlador().buscar(Consola.leerClienteDni()));
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void buscarVehiculo() {
		try {
			Consola.mostrarCabecera("Opcion 5 -> Buscar Vehículo");
			System.out.println();
			System.out.print(getControlador().buscar(Consola.leerVehiculoMatricula()));
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}

	}

	public void buscarAlquiler() {
		try {
			Consola.mostrarCabecera("Opcion 6 -> Buscar Alquiler");
			System.out.println();
			System.out.print(getControlador().buscar(Consola.leerAlquiler()));
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void modificarCliente() {
		try {
			Consola.mostrarCabecera("Opcion 7 -> Modificar Cliente");
			System.out.println();
			getControlador().modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.print("El cliente se ha modificado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void devolverAlquilerCliente() {
		try {
			Consola.mostrarCabecera("Opcion 8 -> Devolver Alquiler");
			System.out.println();
			getControlador().devolver(Consola.leerClienteDni(), Consola.leerFechaDevolucion());
			System.out.print("El Alquiler del cliente se ha devuelto correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void devolverAlquilerVehiculo() {
		try {
			Consola.mostrarCabecera("Opcion 9 -> Devolver Alquiler de un Vehículo");
			System.out.println();
			getControlador().devolver(Consola.leerVehiculoMatricula(), Consola.leerFechaDevolucion());
			System.out.print("El Alquiler del vehiculo se ha devuelto correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void borrarCliente() {
		try {
			Consola.mostrarCabecera("Opcion 10 -> Borrar Cliente");
			System.out.println();
			getControlador().borrar(Consola.leerClienteDni());
			System.out.print("El cliente se ha borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void borrarVehiculo() {
		try {
			Consola.mostrarCabecera("Opcion 11 -> Borrar Vehículo");
			System.out.println();
			getControlador().borrar(Consola.leerVehiculoMatricula());
			System.out.print("El vehiculo se ha borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void borrarAlquiler() {
		try {
			Consola.mostrarCabecera("Opcion 12 -> Borrar Alquiler");
			System.out.println();
			getControlador().borrar(Consola.leerAlquiler());
			System.out.print("El Alquiler se ha borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void listarClientes() {
		Consola.mostrarCabecera("Opcion 13 -> Listar Clientes");
		System.out.println();
		List<Cliente> listaClientes = getControlador().getClientes();
		listaClientes.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
		System.out.print(listaClientes.isEmpty() ? "No hay clientes en la lista" : listaClientes);
	}

	public void listarVehiculos() {
		Consola.mostrarCabecera("Opcion 14 -> Listar Vehículos");
		System.out.println();
		List<Vehiculo> listaVehiculos = getControlador().getVehiculos();
		listaVehiculos.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo)
				.thenComparing(Vehiculo::getMatricula));
		System.out.print(listaVehiculos.isEmpty() ? "No hay vehiculos en la lista" : listaVehiculos);
	}

	public void listarAlquileres() {
		Consola.mostrarCabecera("Opcion 15 -> Listar Alquileres");
		System.out.println();
		List<Alquiler> listaAlquileres = getControlador().getAlquileres();
		Comparator<Cliente> comparadorCliente = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
		listaAlquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente,
				comparadorCliente));
		System.out.print(listaAlquileres.isEmpty() ? "No hay Alquileres en la lista" : listaAlquileres);
	}

	public void listarAlquileresCliente() {

		try {
			Consola.mostrarCabecera("Opcion 16 -> Listar los Alquileres de un Cliente");
			System.out.println();
			List<Alquiler> listaAlquileresCliente = getControlador().getAlquileres(Consola.leerClienteDni());
			Comparator<Cliente> comparadorCliente = Comparator.comparing(Cliente::getNombre)
					.thenComparing(Cliente::getDni);
			listaAlquileresCliente.sort(Comparator.comparing(Alquiler::getFechaAlquiler)
					.thenComparing(Alquiler::getCliente, comparadorCliente));
			System.out.print(
					listaAlquileresCliente.isEmpty() ? "No hay Alquileres para ese cliente" : listaAlquileresCliente);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void listarAlquileresVehiculo() {
		try {
			Consola.mostrarCabecera("Opcion 17 -> Listar los Alquileres de un Vehiculo");
			System.out.println();
			List<Alquiler> listaAlquileresVehiculo = getControlador().getAlquileres(Consola.leerVehiculoMatricula());
			Comparator<Vehiculo> comparadorVehiculo = Comparator.comparing(Vehiculo::getMarca)
					.thenComparing(Vehiculo::getModelo).thenComparing(Vehiculo::getMatricula);
			listaAlquileresVehiculo.sort(Comparator.comparing(Alquiler::getFechaAlquiler)
					.thenComparing(Alquiler::getVehiculo, comparadorVehiculo));
			System.out.print(listaAlquileresVehiculo.isEmpty() ? "No hay Alquileres para ese vehículo"
					: listaAlquileresVehiculo);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {
		Consola.mostrarCabecera("Opcion 18 -> Mostrar estadísticas mensuales del Vehículo");
		System.out.println();
		LocalDate fechdDelMes = Consola.leerMes();

		Map<TipoVehiculo, Integer> ocurrencias = inicializarEstadisticas();
		for (Alquiler alquiler : getControlador().getAlquileres()) {
			if ((alquiler.getFechaAlquiler().getYear() == fechdDelMes.getYear())
					&& (alquiler.getFechaAlquiler().getMonth().equals(fechdDelMes.getMonth()))) {
				ocurrencias.put(TipoVehiculo.get(alquiler.getVehiculo()),
						ocurrencias.get(TipoVehiculo.get(alquiler.getVehiculo())) + 1);
			}
		}
		System.out.print(ocurrencias);
	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		Map<TipoVehiculo, Integer> ocurrencias = new EnumMap<>(TipoVehiculo.class);

		ocurrencias.put(TipoVehiculo.TURISMO, 0);
		ocurrencias.put(TipoVehiculo.AUTOBUS, 0);
		ocurrencias.put(TipoVehiculo.FURGONETA, 0);

		return ocurrencias;

	}

}