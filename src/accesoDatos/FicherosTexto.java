package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		int clave;

		try {
			fDep = new File("Ficheros/datos/depositos.txt");
			FileReader fr = new FileReader(fDep);
			BufferedReader bf = new BufferedReader(fr);
			String line;
			String[] temp = null;
			while ((line = bf.readLine()) != null) {
				temp = line.split(";");
				Deposito depositoAux = new Deposito(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
				clave = Integer.parseInt(temp[1]);

				depositosCreados.put(clave, depositoAux);

			}
		} catch (IOException e) {
			System.out.print("Error de Entrada/Salida");
			return null;
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();
		String clave;

		try {
			fDis = new File("Ficheros/datos/dispensadores.txt");
			FileReader fr = new FileReader(fDis);
			BufferedReader bf = new BufferedReader(fr);
			String line;
			String[] temp = null;
			while ((line = bf.readLine()) != null) {
				temp = line.split(";");
				Dispensador dispensadorAux = new Dispensador(temp[0], temp[1],Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
				clave = temp[0];
				dispensadoresCreados.put(clave, dispensadorAux);

			}
		} catch (IOException e) {
			System.out.print("Error de Entrada/Salida");
			return null;
		};

		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		boolean todoOK = true;


		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;

		return todoOK;
	}

} // Fin de la clase