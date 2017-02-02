package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * Clase Modelo de Cliente.
 * @author neslycastro
 */

public class Cliente {

	private IntegerProperty id;

	private StringProperty nombre;

	private IntegerProperty cedula;

	private StringProperty foto;

	private StringProperty genero;

	private StringProperty direccion;

	private StringProperty correo;

	private IntegerProperty telefono;

	private SimpleObjectProperty<Date> fecha_nacimiento;

	private StringProperty observaciones;

	/**
	 * Constructor de Cliente Básico
	 * @param id
	 * @param nombre
	 * @param cedula
	 */
	public Cliente(String nombre, Integer cedula) {
		super();
		//this.id = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.cedula = new SimpleIntegerProperty(cedula);
	}

	/**
	 * Constructor del Cliente Avanzado I
	 * @param id
	 * @param nombre
	 * @param cedula
	 * @param dirección
	 * @param telefono
	 * @param correo
	 * @param genero
	 * @param observaciones
	 * @param fecha_nacimiento
	 */
	public Cliente(Integer id, String nombre, Integer cedula, Date fecha_nacimiento,
			String direccion, Integer telefono, String correo, String genero,
			String observaciones) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.cedula = new SimpleIntegerProperty(cedula);
		this.fecha_nacimiento = new SimpleObjectProperty(fecha_nacimiento);
		this.direccion = new SimpleStringProperty(direccion);
		this.telefono = new SimpleIntegerProperty(telefono);
		this.correo = new SimpleStringProperty(correo);
		this.genero = new SimpleStringProperty(genero);
		this.observaciones = new SimpleStringProperty(observaciones);

	}

	/**
	 * Constructor del Cliente Avanzado Sin ID II
	 * @param id
	 * @param nombre
	 * @param cedula
	 * @param dirección
	 * @param telefono
	 * @param correo
	 * @param genero
	 * @param observaciones
	 * @param fecha_nacimiento
	 */
	public Cliente(String nombre, Integer cedula, Date fecha_nacimiento,
			String direccion, Integer telefono, String correo, String genero,
			String observaciones) {
		super();
		this.nombre = new SimpleStringProperty(nombre);
		this.cedula = new SimpleIntegerProperty(cedula);
		this.fecha_nacimiento = new SimpleObjectProperty(fecha_nacimiento);
		this.direccion = new SimpleStringProperty(direccion);
		this.telefono = new SimpleIntegerProperty(telefono);
		this.correo = new SimpleStringProperty(correo);
		this.genero = new SimpleStringProperty(genero);
		this.observaciones = new SimpleStringProperty(observaciones);

	}

	/**
	 * id del Cliente
	 */
	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}

	public IntegerProperty idProperty(){
		return id;
	}

	/**
	 * Nombre del Cliente
	 */
	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre = new SimpleStringProperty(nombre);
	}

	public StringProperty nombreProperty(){
		return nombre;

	}

	/**
	 * Cédula del Cliente
	 */
	public int getCedula() {
		return cedula.get();
	}

	public void setCedula(int cedula) {
		this.cedula = new SimpleIntegerProperty(cedula);
	}

	public IntegerProperty cedulaProperty(){
		return cedula;

	}

	/**
	 * Foto del Cliente
	 */
	public String getFoto() {
		return foto.get();
	}

	public void setFoto(String foto) {
		this.foto = new SimpleStringProperty(foto);
	}

	public StringProperty fotoProperty(){
		return foto;
	}

	/**
	 * Genero del Cliente
	 */
	public String getGenero() {
		return genero.get();
	}

	public void setGenero(String genero) {
		this.genero = new SimpleStringProperty(genero);
	}

	public StringProperty generoProperty(){
		return genero;
	}

	/**
	 * Dirección del Cliente
	 */
	public String getDireccion() {
		return direccion.get();
	}

	public void setDireccion(String direccion) {
		this.direccion = new SimpleStringProperty(direccion);
	}

	public StringProperty direccionProperty(){
		return direccion;
	}

	/**
	 * Correo del Cliente
	 */
	public String getCorreo() {
		return correo.get();
	}

	public void setCorreo(String correo) {
		this.correo = new SimpleStringProperty(correo);
	}

	public StringProperty correoProperty(){
		return correo;
	}

	/**
	 * Teléfono del Cliente
	 */

	public Integer getTelefono() {
		return telefono.get();
	}

	public void setTelefono(Integer telefono) {
		this.telefono = new SimpleIntegerProperty(telefono);
	}

	public IntegerProperty telefonoProperty(){
		return telefono;
	}

	/**
	 * fecha_nacimiento del Cliente
	 * @return
	 */
	public Date getFecha_nacimiento() {
		return fecha_nacimiento.get();
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = new SimpleObjectProperty<Date>(fecha_nacimiento);
	}

	public SimpleObjectProperty<Date> fecha_nacimientoProperty(){
		return fecha_nacimiento;
	}

	/**
	 * Observaciones del Cliente
	 */
	public String getObservaciones() {
		return observaciones.get();
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = new SimpleStringProperty(observaciones);
	}

	public StringProperty observacionesProperty(){
		return observaciones;
	}

	/**
	 * Llenar Clientes en combobox
	 * Obtiene los clientes de la DB y por cada cliente crea
	 * un objeto tipo Cliente que los almacena en una ObservableList
	 * para cargarla eventualmente en un combobox
	 * @return número de registros encontrados en la tabla Cliente
	 */
	public static int llenarInformacion(Connection conexion, ObservableList<Cliente> listaClientes){

		try {
			//Incializar un objeto tipo Statement
			Statement statemet = conexion.createStatement();
			//Ejecuta un query y almacena en una coleccion
			ResultSet resultado = statemet.executeQuery("select id,"
					+ " nombre,"
					+ "cedula, "
					+ "fecha_nacimiento, "
					+ "direccion,"
					+ " telefono,"
					+ " correo,"
					+ " genero,"
					+ " observaciones from cliente;");
			//Creo objetos tipo Cliente a partir de los datos recuperados del DB
			//Mientras el apuntador encuentre registros agrega el registro en un objeto tipo Cliente
			while(resultado.next()){
				listaClientes.add(
						new Cliente(resultado.getInt("id"),
								resultado.getString("nombre"),
								resultado.getInt("cedula"),
								resultado.getDate("fecha_nacimiento"),
								resultado.getString("direccion"),
								resultado.getInt("telefono"),
								resultado.getString("correo"),
								resultado.getString("genero"),
								resultado.getString("observaciones")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaClientes.size();

	}

	/**
	 * Insertar registros o clientes nuevos en la DB dinámicamente
	 * o sea con consulta parametrizada
	 * @param conexion
	 */
	public  void guardarRegistro(Connection conexion){

		try {
			//Genero la consulta parametrizada como argumento de un objeto PreparedStatement
			PreparedStatement instruccion = conexion.prepareStatement("insert into cliente"
					+ "(nombre,"
					+ "cedula,"
					+ "fecha_nacimiento, "
					+ "direccion, "
					+ "telefono, "
					+ "correo, "
					+ "genero, "
					+ "observaciones) value(?,?,?,?,?,?,?,?)");

			//Definimos los parametros
			instruccion.setString(1,nombre.get());
			instruccion.setInt(2, cedula.get());
			instruccion.setDate(3, fecha_nacimiento.get());
			instruccion.setString(4, direccion.get());
			instruccion.setInt(5, telefono.get());
			instruccion.setString(6, correo.get());
			instruccion.setString(7, genero.get());
			instruccion.setString(8, observaciones.get());

			//Ejecutas la consulta
			instruccion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}




	/**
	 * Actualizar registros o clientes nuevos en la DB dinámicamente
	 * o sea con consulta parametrizada
	 * @param conexion
	 */
	public  void actualizarRegistro(Connection conexion){

		try {
			//Genero la consulta parametrizada como argumento de un objeto PreparedStatement
			PreparedStatement instruccion =
					conexion.prepareStatement("update cliente set "
							+ "nombre = ?, "
							+ "cedula = ?, "
							+ "fecha_nacimiento = ?, "
							+ "direccion = ?, "
							+ "telefono = ?, "
							+ "correo = ?, "
							+ "genero = ?, "
							+ "observaciones = ? where cedula = ?");

			//Definimos los parametros
			instruccion.setString(1,nombre.get());
			instruccion.setInt(2, cedula.get());
			instruccion.setDate(3, fecha_nacimiento.get());
			instruccion.setString(4, direccion.get());
			instruccion.setInt(5, telefono.get());
			instruccion.setString(6, correo.get());
			instruccion.setString(7, genero.get());
			instruccion.setString(8, observaciones.get());
			instruccion.setInt(9, cedula.get());

			//Ejecutas la consulta
			instruccion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Eliminar una instancia de tipo Cliente de la DB
	 * @param conexion
	 */
	public  void eliminarRegistro(Connection conexion){

		try {

			//Realizo la consulta parametrizada Sql para eliminar registro
			PreparedStatement instruccion = conexion.prepareStatement(
					"delete from cliente where cedula = ?");

			//Defino los parámetros de la consulta
			instruccion.setInt(1, cedula.get());
			//Ejecuto la consulta
			instruccion.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Método sobrescrito para mostrar toda la info del objeto cliente
	 */
	@Override
	public String toString (){
		String clienteString = id.get() + "-" + nombre.get() + "-" + cedula.get()
		+ fecha_nacimiento.get() + direccion.get() + "-" + telefono.get() + "-"
		+ correo.get() + genero.get() + "-" + observaciones.get();
		return clienteString;
	}


}


