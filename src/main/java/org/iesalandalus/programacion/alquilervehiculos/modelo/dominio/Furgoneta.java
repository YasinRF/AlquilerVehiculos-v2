package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Furgoneta extends Vehiculo {
	
	private static final int FACTOR_PMA = 100;
	private static final int FACTOR_PLAZAS = 1;
	
	private int pma;
	private int plazas;
	
	
	// CREAMOS LOS CONSTRUCTORES Y EL CONSTRUCTOR COPIA
	
	public Furgoneta (String marca, String modelo, int pma, int plazas, String matricula) {
		super(marca, modelo, matricula);
		setPma(pma);
		setPlazas(plazas);
	}
	
	public Furgoneta(Furgoneta furgoneta) {
		super(furgoneta);
		if(furgoneta == null) {
			throw new NullPointerException("ERROR: No es posible copiar un vehículo nulo.");
		}
		pma = furgoneta.getPma();
		plazas = furgoneta.getPlazas();
	}
	
	// GETTERS Y SETTERS
	
	public int getPma() {
		return pma;
	}

	private void setPma(int pma) {
		if(pma<101 || pma>10000) {
			throw new IllegalArgumentException("ERROR: El PMA no es correcto.");
		}
		this.pma = pma;
	}

	public int getPlazas() {
		return plazas;
	}

	private void setPlazas(int plazas) {
		if(plazas<2 || plazas>9) {
			throw new IllegalArgumentException("ERROR: Las plazas no son correctas.");
		}
		this.plazas = plazas;
	}
	
	// CREAMOS EL TOSTRING
	
	@Override
	public int getFactorPrecio() {
		int precioFurgoneta = (pma/FACTOR_PMA) + (plazas * FACTOR_PLAZAS);
		return precioFurgoneta;
	}

	@Override
	public String toString() {
		return String.format("%s %s (%d kg, %d plazas) - %s",getMarca(),getModelo(), pma, plazas, getMatricula());	}
	
	
}
