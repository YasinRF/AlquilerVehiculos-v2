package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public class ModeloCascada extends Modelo {
	
	public ModeloCascada(FactoriaFuenteDatos factoriaFuenteDatos) {
		super(factoriaFuenteDatos);
	}
	
	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		getCliente().insertar(cliente);
	}
	
	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		getVehiculo().insertar(vehiculo);
	}
	
	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		Cliente cliente = getCliente().buscar(alquiler.getCliente());
		if (cliente == null) {
			throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
		}
		Vehiculo vehiculo = getVehiculo().buscar(alquiler.getVehiculo());
		if (vehiculo == null) {
			throw new OperationNotSupportedException("ERROR: No existe el vehiculo del alquiler.");
		}
		getAlquiler().insertar(new Alquiler(cliente, vehiculo, alquiler.getFechaAlquiler()));
	}
	
	@Override
	public Cliente buscar(Cliente cliente) {
		return new Cliente(getCliente().buscar(cliente));
	}
	
	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		return Vehiculo.copiar(getVehiculo().buscar(vehiculo));
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		return new Alquiler(getAlquiler().buscar(alquiler));
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		getCliente().modificar(cliente, nombre, telefono);
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		getAlquiler().devolver(cliente, fechaDevolucion);
	}
	
	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException{
		getAlquiler().devolver(vehiculo, fechaDevolucion);
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		for (Alquiler alquiler : getAlquiler().get(cliente)) {
			getAlquiler().borrar(alquiler);
		}
		getCliente().borrar(cliente);
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		for (Alquiler alquiler : getAlquiler().get(vehiculo)) {
			getAlquiler().borrar(alquiler);
		}
		getVehiculo().borrar(vehiculo);
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		getAlquiler().borrar(alquiler);
	}

	@Override
	public List<Cliente> getListaClientes() {
		List<Cliente> lista = new ArrayList<>();
		for (Cliente cliente : getCliente().get()) {
			lista.add(new Cliente(cliente));
		}
		return lista;
	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		List<Vehiculo> lista = new ArrayList<>();
		for (Vehiculo vehiculo : getVehiculo().get()) {
			lista.add(Vehiculo.copiar(getVehiculo().buscar(vehiculo)));
		}
		return lista;
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : getAlquiler().get()) {
			lista.add(new Alquiler(alquiler));
		}
		return lista;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : getAlquiler().get(cliente)) {
			lista.add(new Alquiler(alquiler));
		}
		return lista;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : getAlquiler().get(vehiculo)) {
			lista.add(new Alquiler(alquiler));
		}
		return lista;
	}

}
