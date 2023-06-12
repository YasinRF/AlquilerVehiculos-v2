package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import javax.naming.OperationNotSupportedException;


public enum Accion {
	SALIR("Salir") {
		@Override
		public void ejecutar() {
			vista.terminar();
		}
	},
	
	
	INSERTAR_CLIENTE("Insertar un Cliente") {
		@Override
		public void ejecutar() {
			vista.insertarCliente();
		}
	},
	INSERTAR_VEHICULO("Insertar un Vehículo") {
		@Override
		public void ejecutar() {
			vista.insertarVehiculo();
		}
	},
	INSERTAR_ALQUILER("Insertar un Alquiler") {
		@Override
		public void ejecutar() {
			vista.insertarAlquiler();
		}
	},
	
	
	BUSCAR_CLIENTE("Buscar un Cliente") {
		@Override
		public void ejecutar() {
			vista.buscarCliente();
		}
	},
	BUSCAR_VEHICULO("Buscar un Vehículo") {
		@Override
		public void ejecutar() {
			vista.buscarVehiculo();
		}
	},
	BUSCAR_ALQUILER("Buscar un Alquiler") {
		@Override
		public void ejecutar() {
			vista.buscarAlquiler();
		}
	},
	
	
	MODIFICAR_CLIENTE("Modificarun Cliente") {
		@Override
		public void ejecutar() {
			vista.modificarCliente();
		}
	},
	
	
	DEVOLVER_ALQUILER_CLIENTE("Devolver Alquiler de un Cliente") {
		@Override
		public void ejecutar() {
			vista.devolverAlquilerCliente();
		}
	},
	DEVOLVER_ALQUILER_VEHICULO("Devolver Alquiler de un Vehículo") {
		@Override
		public void ejecutar() {
			vista.devolverAlquilerVehiculo();
		}
	},
	
	
	BORRAR_CLIENTE("Borrar un Cliente") {
		@Override
		public void ejecutar() {
			vista.borrarCliente();

		}
	},
	BORRAR_VEHICULO("Borrar un Vehículo") {
		@Override
		public void ejecutar() {
			vista.borrarVehiculo();
		}
	},
	BORRAR_ALQUILER("Borrar un Alquiler") {
		@Override
		public void ejecutar() {
			vista.borrarAlquiler();
		}
	},
	
	
	LISTAR_CLIENTES("Listar Clientes") {
		@Override
		public void ejecutar() {
			vista.listarClientes();
		}
	},
	LISTAR_VEHICULOS("Listar Vehículos") {
		@Override
		public void ejecutar() {
			vista.listarVehiculos();
		}
	},
	LISTAR_ALQUILERES("Listar Alquileres") {
		@Override
		public void ejecutar() {
			vista.listarAlquileres();
		}
	},
	LISTAR_ALQUILERES_CLIENTE("Listar los Alquileres de un Cliente") {
		@Override
		public void ejecutar() {
			vista.listarAlquileresCliente();
		}
	},
	LISTAR_ALQUILERES_VEHICULO("Listar los Alquileres de un Vehículo") {
		@Override
		public void ejecutar() {
			vista.listarAlquileresVehiculo();

		}
	},
	
	
	MOSTRAR_ESTADISTICAS_MENSUALES("Mostrar las Estadísticas Mensuales") {
		@Override
		public void ejecutar() {
			vista.mostrarEstadisticasMensualesTipoVehiculo();
		}
	};

	private String texto;
	private static VistaTexto vista;

	private Accion(String texto) {
		this.texto = texto;
	}

	public abstract void ejecutar();

	static void setVista(VistaTexto vista) {
		Accion.vista = vista;
	}

	private static boolean esOrdinalValido(int ordinal) {
		boolean ordinalCorrecto = true;
		if (ordinal < 0 || ordinal >= values().length) {
			ordinalCorrecto = false;
		}
		return ordinalCorrecto;
	}

	public static Accion get(int ordinal) throws OperationNotSupportedException {
		if (!esOrdinalValido(ordinal)) {
			throw new OperationNotSupportedException("El ordinal pasado no es correcto");
		}
		return values()[ordinal];
	}

	@Override
	public String toString() {
		return String.format("%d-%s", ordinal(), texto);
	}
}