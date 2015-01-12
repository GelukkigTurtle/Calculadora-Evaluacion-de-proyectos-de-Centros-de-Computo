/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financiamientodeproyectos;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Freddy
 */
public class GUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtMonto;

    @FXML
    private MenuItem opCerrar;

    @FXML
    private Button btnCalcular;

    @FXML
    private MenuItem opAcercaDe;

    @FXML
    private TextField txtPeriodo;

    @FXML
    private Tab tab1;

    @FXML
    private TextField txtInteres;

    @FXML
    private ChoiceBox<String> cmbFormasDePago;

    @FXML
    private Tab tab2;

    @FXML
    private TableView<Registro> tbResultado;

    @FXML
    private Label lbResultado;

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtMonto1;

    @FXML
    private TextField txtPeriodo1;

    @FXML
    private ChoiceBox<String> cmbFormasDePago1;

    @FXML
    private TextField txtVS;

    @FXML
    private Button btnCalcular1;

    @FXML
    private Label lbResultado1;

    @FXML
    private Label lblTitulo1;

    @FXML
    private TextField txtA;

    @FXML
    private Label lblDynamic;

    @FXML
    private TextField txtDynamic;

    @FXML
    private Label lblDynamic1;

    @FXML
    private TextField txtDynamic1;
    @FXML
    private CheckBox chkBitacora;
    @FXML
    private TextArea txtBitacora;

    ObservableList<String> lstFormasDePago = FXCollections.observableArrayList(
            "1 - Pago de capital e intereses al final de los N años ",
            "2 - Pago de interés al final de cada año,  y de interés y todo el capital al final del último año",
            "3 - Pago de cantidades iguales al final de cada uno de los N años",
            "4 - Pago de intereses y una parte proporcional de capital (20% cada año) al final de cada uno de los N años"
    );

    ObservableList<String> lstTIRS = FXCollections.observableArrayList(
            "1 - Cálculo de la TIR con flijos constantes sin inflación",
            "2 - Cálculo de la TIR con producción constante y considerando inflación",
            "3 - Cálculo de la TIR con financiamiento"
    );
    //financiamiento bancario
    List<String> mlistSpinner;
    BigDecimal mMonto;
    BigDecimal mAnio;
    BigDecimal mInteres;
    String mTipo;
    List<Registro> listaReg;

       //tir
    List<String> mlistTir;
    BigDecimal tirMonto;
    BigDecimal tirAnio;
    BigDecimal tirA;
    BigDecimal tirVS;
    String mTipoTir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ///ocultamos bitacora y tabla
        txtBitacora.setVisible(false);
        tbResultado.setVisible(false);
        txtDynamic.setVisible(false);
        lblDynamic.setVisible(false);
        txtDynamic1.setVisible(false);
        lblDynamic1.setVisible(false);
        //llenamos el combo de formas de pago
        tbResultado.setId("my-table"); //para alinear las celdas a la izquierda

        ObservableList<String> lstFormas = FXCollections.observableArrayList(lstFormasDePago);
        cmbFormasDePago.setItems(lstFormas);

        ObservableList<String> lstTirs = FXCollections.observableArrayList(lstTIRS);
        cmbFormasDePago1.setItems(lstTirs);

        opAcercaDe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Dialogs.showInformacion();
            }
        });
        //financimiento bancario
        btnCalcular.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                getFieldsData();
                calcularTablaDePagos(mTipo);
            }
        });
        //tir
        btnCalcular1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                getFieldsDataTIR();
                calcularTIR(mTipoTir);
            }
        });

        opCerrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });
        //mostar o no la bitacora
        chkBitacora.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    txtBitacora.setVisible(true);
                } else {
                    txtBitacora.setVisible(false);
                }
            }
        });
        //evento del combobox de tipo de tir
        cmbFormasDePago1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1.contains("2")) {
                    txtDynamic.setVisible(true);
                    lblDynamic.setVisible(true);
                    txtDynamic1.setVisible(false);
                    lblDynamic1.setVisible(false);
                    lblDynamic.setText("Inflación (%): ");

                } else if (t1.contains("3")) {
                    txtDynamic.setVisible(true);
                    lblDynamic.setVisible(true);
                    txtDynamic1.setVisible(true);
                    lblDynamic1.setVisible(true);
                    lblDynamic.setText("Inflación (%): ");
                    lblDynamic1.setVisible(true);
                    lblDynamic1.setText("Financiamiento ($):");
                    

                } else {
                    txtDynamic.setVisible(false);
                    lblDynamic.setVisible(false);
                    txtDynamic1.setVisible(false);
                    lblDynamic1.setVisible(false);
                }

            }
        });
    }

    private void getFieldsData() {
        if (txtPeriodo.getText() != null && txtInteres.getText() != null && txtMonto.getText() != null) {
            if (!txtPeriodo.getText().equals("") && !txtInteres.getText().equals("") && !txtMonto.getText().equals("")) {

                mMonto = new BigDecimal(txtMonto.getText());
                mInteres = new BigDecimal(txtInteres.getText());
                mInteres = mInteres.divide(new BigDecimal(100));
                mAnio = new BigDecimal(txtPeriodo.getText());
                mTipo = cmbFormasDePago.getValue();
            } else {
                System.out.println("Debe llenar todos los campos");
            }
        } else {
            System.out.println("Debe llenar todos los campos");
        }

    }

    private void getFieldsDataTIR() {
        if (txtPeriodo1.getText() != null && txtA.getText() != null && txtMonto1.getText() != null && txtVS.getText() != null) {
            if (!txtPeriodo1.getText().equals("") && !txtA.getText().equals("") && !txtMonto1.getText().equals("") && !txtVS.getText().equals("")) {

                tirMonto = new BigDecimal(txtMonto1.getText());
                tirA = new BigDecimal(txtA.getText());
                tirAnio = new BigDecimal(txtPeriodo1.getText());
                mTipoTir = cmbFormasDePago1.getValue();
                tirVS = new BigDecimal(txtVS.getText());
            } else {
                System.out.println("Debe llenar todos los campos");
            }
        } else {
            System.out.println("Debe llenar todos los campos");
        }

    }

    private void calcularTablaDePagos(String forma) {
        lbResultado.setText("");
        lblTitulo.setText("");
        lblTitulo.setText(forma);
        tbResultado.setVisible(false);
        if (forma.contains("1")) {
            lbResultado.setText("Resultado: $ " + formatNumber(Calculadora.metodoPrimario(mMonto, mInteres, mAnio.intValue())));
        }
        if (forma.contains("2")) {
            tbResultado.setVisible(true);

            listaReg = Calculadora.metodoSecundario(mMonto, mInteres, mAnio.intValue());
            crearDetalleTabla(listaReg, forma);

        }
        if (forma.contains("3")) {
            tbResultado.setVisible(true);

            listaReg = Calculadora.metodoTerciario(mMonto, mInteres, mAnio.intValue());
            crearDetalleTabla(listaReg, forma);

        }
        if (forma.contains("4")) {
            tbResultado.setVisible(true);

            listaReg = Calculadora.metodoCuarto(mMonto, mInteres, mAnio.intValue());
            crearDetalleTabla(listaReg, forma);

        }
    }

    private void calcularTIR(String forma) {
        lbResultado1.setText("");
        lblTitulo1.setText("");
        lblTitulo1.setText(forma);

        if (forma.contains("1")) {
            lbResultado1.setText("Resultado: % " + Calculadora.TIR1(tirMonto, tirA, tirAnio.intValue(), tirVS));
        }
        if (forma.contains("2")) {
            if(txtDynamic.getText() != null || !txtDynamic.getText().equals("") ){
            BigDecimal inflacionEntera = new BigDecimal(txtDynamic.getText());
            BigDecimal inflacionDecimal = inflacionEntera.divide(new BigDecimal(100));
            BigDecimal inflacionReal = inflacionDecimal.add(new BigDecimal(1));
            lbResultado1.setText("Resultado: % " + Calculadora.TIR2(tirMonto, tirA, tirAnio.intValue(), tirVS,inflacionReal));

            }

        }
        if (forma.contains("3")) {
        if((txtDynamic.getText() != null || !txtDynamic.getText().equals("")) && (txtDynamic1.getText() != null || !txtDynamic1.getText().equals(""))){
            BigDecimal inflacionEntera = new BigDecimal(txtDynamic.getText());
            BigDecimal inflacionDecimal = inflacionEntera.divide(new BigDecimal(100));
            BigDecimal inflacionReal = inflacionDecimal.add(new BigDecimal(1));
            lbResultado1.setText("Resultado: % " + Calculadora.TIR3(tirMonto, tirA, tirAnio.intValue(), tirVS,inflacionReal,new BigDecimal(txtDynamic1.getText())));
        }

        }
        txtBitacora.setText(Calculadora.bitacora);

    }

    public String formatNumber(BigDecimal number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        return formatter.format(number);
    }

    private void crearDetalleTabla(List<Registro> reg, String forma) {
        if (forma.contains("2")) {
            crearTablaForma2();
        }

        if (forma.contains("3")) {
            crearTablaForma3();

        }
        if (forma.contains("4")) {
            crearTablaForma4();
        }

    }

    public void crearTablaForma2() {
        ObservableList<Registro> listaDeCausasBasicas = FXCollections.observableArrayList(listaReg);
        tbResultado.setItems(listaDeCausasBasicas);

        final TableColumn colAño = new TableColumn("Año");
        colAño.setCellValueFactory(
                new PropertyValueFactory<Registro, String>("anio"));

        final TableColumn colInteres = new TableColumn("Interés");
        colInteres.setCellValueFactory(
                new PropertyValueFactory<Registro, String>("interes"));

        final TableColumn colPagoFin = new TableColumn("Pago a Fin de año");
        colPagoFin.setCellValueFactory(new PropertyValueFactory<Registro, Boolean>("pagoFinAnio"));

        final TableColumn colDespPago = new TableColumn("Deuda después de pago");
        colDespPago.setCellValueFactory(new PropertyValueFactory<Registro, Boolean>("deudaDespuesPago"));

        if (tbResultado.getColumns().isEmpty()) {
            tbResultado.getColumns().addAll(colAño, colInteres, colPagoFin, colDespPago);
        } else {
            tbResultado.getColumns().clear();
            tbResultado.getColumns().addAll(colAño, colInteres, colPagoFin, colDespPago);
        }

    }

    public void crearTablaForma3() {
        ObservableList<Registro> listaDeCausasBasicas = FXCollections.observableArrayList(listaReg);
        tbResultado.setItems(listaDeCausasBasicas);

        final TableColumn colAño = new TableColumn("Año");
        colAño.setCellValueFactory(
                new PropertyValueFactory<Registro, String>("anio"));

        final TableColumn colInteres = new TableColumn("Interés");
        colInteres.setCellValueFactory(
                new PropertyValueFactory<Registro, String>("interes"));

        final TableColumn colPagoFin = new TableColumn("Pago de Fin de año");
        colPagoFin.setCellValueFactory(new PropertyValueFactory<Registro, Boolean>("pagoFinAnio"));

        final TableColumn colPagPrincipal = new TableColumn("Pago a Principal");
        colPagPrincipal.setCellValueFactory(new PropertyValueFactory<Registro, Boolean>("pagoAPrincipal"));

        final TableColumn colDespPago = new TableColumn("Deuda después de pago");
        colDespPago.setCellValueFactory(new PropertyValueFactory<Registro, Boolean>("deudaDespuesPago"));

        if (tbResultado.getColumns().isEmpty()) {
            tbResultado.getColumns().addAll(colAño, colInteres, colPagoFin, colPagPrincipal, colDespPago);
        } else {
            tbResultado.getColumns().clear();
            tbResultado.getColumns().addAll(colAño, colInteres, colPagoFin, colPagPrincipal, colDespPago);
        }

    }

    public void crearTablaForma4() {
        ObservableList<Registro> listaDeCausasBasicas = FXCollections.observableArrayList(listaReg);
        tbResultado.setItems(listaDeCausasBasicas);

        final TableColumn colAño = new TableColumn("Año");
        colAño.setCellValueFactory(
                new PropertyValueFactory<Registro, String>("anio"));

        final TableColumn colInteres = new TableColumn("Interés");
        colInteres.setCellValueFactory(
                new PropertyValueFactory<Registro, String>("interes"));

        final TableColumn colPagoFin = new TableColumn("Pago a Capital");
        colPagoFin.setCellValueFactory(new PropertyValueFactory<Registro, Boolean>("pagoACapital"));

        final TableColumn colPagoAnual = new TableColumn("Pago anual");
        colPagoAnual.setCellValueFactory(new PropertyValueFactory<Registro, Boolean>("pagoFinAnio"));

        final TableColumn colDespPago = new TableColumn("Deuda después de pago");
        colDespPago.setCellValueFactory(new PropertyValueFactory<Registro, Boolean>("deudaDespuesPago"));

        if (tbResultado.getColumns().isEmpty()) {
            tbResultado.getColumns().addAll(colAño, colInteres, colPagoFin, colPagoAnual, colDespPago);
        } else {
            tbResultado.getColumns().clear();
            tbResultado.getColumns().addAll(colAño, colInteres, colPagoFin, colPagoAnual, colDespPago);
        }

    }

}
