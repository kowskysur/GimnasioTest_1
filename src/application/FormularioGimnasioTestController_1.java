package application;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Cliente;
import modelo.Conexion;

public class FormularioGimnasioTestController_1 implements Initializable {

	//Componente GUI
	@FXML private ComboBox<Cliente> cmbCliente;
	@FXML private TableView<Cliente> tblViewCliente;
	@FXML private TableColumn<Cliente, String> clmnId;
	@FXML private TableColumn<Cliente, String> clmnNombre;
	@FXML private TableColumn<Cliente, Integer> clmnCedula;
	@FXML private TableColumn<Cliente, Date> clmnFecha_Nacimiento;
	@FXML private TableColumn<Cliente, String> clmnGenero;
	@FXML private TableColumn<Cliente, String> clmnDireccion;
	@FXML private TableColumn<Cliente, Integer> clmnTelefono;
	@FXML private TableColumn<Cliente, String> clmnCorreo;
	@FXML private TableColumn<Cliente, String> clmnObservaciones;
	@FXML private TextField txtNombre;
	@FXML private TextField txtCedula;
	@FXML private DatePicker datePkrFecha_Nacimiento;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtTelefono;
	@FXML private TextField txtCorreo;
	@FXML private TextArea txtAreaObservaciones;
	//Declarar un objeto tipo toggleGroup para seleccionar los radioButtons
	@FXML private ToggleGroup tglGroupGenero;
	@FXML private RadioButton rdoBtmFemenino;
	@FXML private RadioButton rdoBtmMasculino;

	@FXML private Button btnNuevo;
	@FXML private Button btnGuardar;
	@FXML private Button btnEditar;
	@FXML private Button btnBorrar;




	//Listas
	private ObservableList<Cliente> listaClientes;




	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Inicializamos la lista con una clase utitity
		listaClientes = FXCollections.observableArrayList();

		//Creamos un objeto de tipo Conexion para conectar a la DB
		Conexion conexion = new Conexion();
		//Conecatmos a la DB
		conexion.establecerConexion();

		//Cargo la lista de Clientes con la info de la DB con la conexión establecida
		Cliente.llenarInformacion(conexion.getConexion(), listaClientes);

		//Cargo el ComboBoxx
		cmbCliente.setItems(listaClientes);

		//Cargo la lista de Clientes en la tabla
		tblViewCliente.setItems(listaClientes);

		clmnId.setCellValueFactory(new PropertyValueFactory<Cliente, String>("id"));
		clmnNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
		clmnCedula.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("cedula"));
		clmnFecha_Nacimiento.setCellValueFactory(new PropertyValueFactory<Cliente, Date>("fecha_nacimiento"));
		clmnGenero.setCellValueFactory(new PropertyValueFactory<Cliente, String>("genero"));
		clmnDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
		clmnTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("telefono"));
		clmnCorreo.setCellValueFactory(new PropertyValueFactory<Cliente, String>("correo"));
		clmnObservaciones.setCellValueFactory(new PropertyValueFactory<Cliente, String>("observaciones"));

		//Método que ejecuta las selección de registros en la tabla del GUI
		gestionarEventos();
		//Cerramos la conexion
		conexion.cerrarConexion();

	}

	/**
	 * Permite seleccionar un cliente de la tabla y cargar los datos en cada textField
	 */
	public void gestionarEventos(){

		//Agrega el escuchador a la tabla que detecta cuando se ha seleccionado un registro
		tblViewCliente.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Cliente>(){

					//Implementa el método del escuchador, de tal forma que los datos del registro
					//se muestren en cada textField asociado al Cliente
					@Override
					public void changed(ObservableValue<? extends Cliente> observable, Cliente oldValue,
							Cliente newValue) {

						System.out.println("Fue seleccionado el registro: " + newValue.getNombre());
						txtNombre.setText(newValue.getNombre());
						txtCedula.setText(String.valueOf(newValue.getCedula()));
						//datePkrFecha_Nacimiento.setValue(LocalDate.parse(newValue.getFecha_nacimiento().toString()));
						//DatePicker solo muestra y recibe fechas en formato LOCALDATE, q son Date pero sin marca de tiempo
						datePkrFecha_Nacimiento.setValue(newValue.getFecha_nacimiento().toLocalDate());
						txtDireccion.setText(newValue.getDireccion());
						txtTelefono.setText(String.valueOf(newValue.getTelefono()));
						txtCorreo.setText(newValue.getCorreo());
						txtAreaObservaciones.setText(newValue.getObservaciones());

						//Definimos que radioButton se selecciona según el genero
						//Si no existe un ToggleGroup para los radioButton los select no funcionan
						if (newValue.getGenero().equals("F")){

							rdoBtmFemenino.setSelected(true);
							rdoBtmMasculino.setSelected(false);
						} else if (newValue.getGenero().equals("M")) {

							rdoBtmFemenino.setSelected(false);
							rdoBtmMasculino.setSelected(true);
						} else{
							//Genero no es campo obligatorio, por tanto sino es F o M no es selección ningún radioButto
							rdoBtmFemenino.setSelected(false);
							rdoBtmMasculino.setSelected(false);
						}

						//Activa los botones guardar y borrar cada vez que selecciona un registro
						btnEditar.setDisable(false);
						btnBorrar.setDisable(false);
						btnGuardar.setDisable(true);

					}
				});

	}

	/**
	 * Asociado al botón Nuevo para limpiar los textField
	 */
	@FXML
	public void limpiarComponentes(){

		txtNombre.setText(null);
		txtCedula.setText(null);
		txtDireccion.setText(null);
		txtTelefono.setText(null);
		txtCorreo.setText(null);
	    txtAreaObservaciones.setText(null);
		datePkrFecha_Nacimiento.setValue(null);
		rdoBtmFemenino.setSelected(false);
		rdoBtmMasculino.setSelected(false);

		btnEditar.setDisable(true);
		btnBorrar.setDisable(true);
		btnGuardar.setDisable(false);


	}


	/**
	 * Asociado al botón Guardar para guardar datos de un cliente
	 */
	@FXML
	public void guardarCliente(){
		//Creamos un objeto de tipo Conexion para conectar a la DB
		Conexion conexion = new Conexion();
		//Conecatmos a la DB
		conexion.establecerConexion();
		//Cliente.guardarRegistro(conexion.getConexion());

		//Creamos una instancia del tipo Cliente Avanzado II
		Cliente nuevoCliente = new Cliente(
				txtNombre.getText(),
				Integer.valueOf(txtCedula.getText()),
				Date.valueOf(datePkrFecha_Nacimiento.getValue()),
				txtDireccion.getText(),
				Integer.valueOf(txtTelefono.getText()),
				txtCorreo.getText(),
				generoSeleccionado(),
				txtAreaObservaciones.getText());

		//Llamamos al método guardarCliente
		nuevoCliente.guardarRegistro(conexion.getConexion());

		//Cerramos la conexion
		conexion.cerrarConexion();

		//Mensaje de alerta par indicar que el registro fue guardado
		Alert info = new Alert(AlertType.CONFIRMATION);
		info.setTitle("Alerta de Registros");
		info.setContentText("El registro ha sido agregado");

		//Una vez fue guardado se agrega ese registro a la lista de Clientes
		//Para que lo muestre en la tabla
		listaClientes.add(nuevoCliente);




	}


	public String generoSeleccionado(){

		String seleccion = null;

		if (rdoBtmFemenino.isSelected() == true){
			seleccion = "F";

		} else if (rdoBtmMasculino.isSelected() == true){
			seleccion = "M";
		}

		return seleccion;

	}


}
