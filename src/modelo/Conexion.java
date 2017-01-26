package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para establecer la conexión con la base de datos
 * @author neslycastro
 *
 */
public class Conexion {

	private Connection conexion;
	private String url = "jdbc:mysql://localhost/dbgymtest";
	private String usuario = "root";
	private String contraseña = "root";


	/**
	 * Conexion a la base de datos
	 * @return conexion
	 */
	public Connection getConexion() {
		return conexion;
	}

	/**
	 * Conexión a la base de datos
	 * @param conexion
	 */
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	/**
	 * Establece una conexión con la base de datos
	 */
	public void establecerConexion(){

		try {
			//Cargar el Driver - la clase del driver
			Class.forName("com.mysql.jdbc.Driver");

			//Inicializa/Crea un objeto tipo conexión con la clase DriverManager
			conexion = DriverManager.getConnection(url,usuario,contraseña);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion(){
		try {
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
