package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			// e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> coleccionDepositos = new HashMap<Integer, Deposito>();
		int clave;

		String query = "SELECT * FROM `depositos`";
		try {
			PreparedStatement pstmt = conn1.prepareStatement(query);

			ResultSet rset = pstmt.executeQuery(query);

			while (rset.next()) {
				Deposito depositoAux = new Deposito(rset.getString("nombre"), Integer.parseInt(rset.getString("valor")),
						Integer.parseInt(rset.getString("cantidad")));
				clave = Integer.parseInt(rset.getString("valor"));

				coleccionDepositos.put(clave, depositoAux);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		return coleccionDepositos;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> coleccionDispensadores = new HashMap<String, Dispensador>();
		String clave;
		String query = "SELECT * FROM `dispensadores`";
		try {
			PreparedStatement pstmt = conn1.prepareStatement(query);

			ResultSet rset = pstmt.executeQuery(query);

			while (rset.next()) {
				Dispensador dispensadorAux = new Dispensador(rset.getString("clave"), rset.getString("nombre"),
						Integer.parseInt(rset.getString("precio")), Integer.parseInt(rset.getString("cantidad")));
				clave = rset.getString("clave");
				coleccionDispensadores.put(clave, dispensadorAux);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		return coleccionDispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		
		for (Entry<Integer, Deposito> entry : depositos.entrySet()) {

			String query = "UPDATE depositos SET cantidad = ? WHERE valor = ?;";
			try {
				PreparedStatement pstmt = conn1.prepareStatement(query);
				pstmt.setInt(1, entry.getValue().getCantidad());
				pstmt.setInt(2, entry.getValue().getValor());
				pstmt.executeUpdate();

			} catch (Exception e) {
				todoOK = false;
				System.out.println(e.getMessage());
				
			}

		}

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;

		for (Entry<String, Dispensador> entry : dispensadores.entrySet()) {

			String query = "UPDATE dispensadores SET cantidad = ? WHERE clave = ?;";
			try {
				PreparedStatement pstmt = conn1.prepareStatement(query);
				pstmt.setInt(1, entry.getValue().getCantidad());
				pstmt.setString(2, entry.getValue().getClave());
				pstmt.executeUpdate();


			} catch (Exception e) {
				todoOK = false;
				System.out.println(e.getMessage());

			}

		}

		return todoOK;
	}

} // Fin de la clase