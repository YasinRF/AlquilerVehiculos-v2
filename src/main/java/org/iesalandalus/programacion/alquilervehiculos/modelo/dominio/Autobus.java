package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Autobus extends Vehiculo{
	
	private static final int FACTOR_FLAZAS = 2;
	
	private int plazas;
	
	
	// CREAMOS LOS CONSTRUCTORES Y EL CONSTRUCTOR COPIA
	
	public Autobus (String marca, String modelo, int plazas , String matricula) {
		super(marca, modelo, matricula);
		setPlazas(plazas);
		
	}
	public Autobus (Autobus autobus) {
		super(autobus);
		if(autobus == null) {
			throw new NullPointerException("ERROR: No es posible copiar un vehículo nulo.");
		}
		plazas = autobus.getPlazas();
	}
	
	// GETTERS Y SETTERS

	public int getPlazas() {
		return plazas;
	}


	private void setPlazas(int plazas) {
		if(plazas<7 || plazas>100) {
			throw new IllegalArgumentException("ERROR: Las plazas no son correctas.");
		}
		this.plazas = plazas;
	}
	
	// CREAMOS EL TOSTRING
	
	@Override
	public int getFactorPrecio() {
		int precioAutobus = plazas * FACTOR_FLAZAS;
		return precioAutobus;
	}
	@Override
	public String toString() {
		return String.format("%s %s (%s plazas) - %s",getMarca(),getModelo(), plazas, getMatricula());
	}
	
}
