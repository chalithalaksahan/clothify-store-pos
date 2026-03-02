package controller.supplier;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Supplier;


import java.net.URL;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {


    @FXML private JFXButton btnAddSupplier;
    @FXML private JFXButton btnDelete;
    @FXML private JFXButton btnSearch;
    @FXML private JFXButton btnUpdate;
    @FXML private JFXCheckBox chkBoxStatus;
    @FXML private JFXComboBox<String> cmbPaymentType;
    @FXML private TableView<Supplier> tblSupplier;
    @FXML private TableColumn<Supplier, Integer> colSupplierId;
    @FXML private TableColumn<Supplier, String>  colCompanyName;
    @FXML private TableColumn<Supplier, String>  colEmail;
    @FXML private TableColumn<Supplier, String>  colContactPerson;
    @FXML private TableColumn<Supplier, String>  colContactNo;
    @FXML private TableColumn<Supplier, String>  colCity;
    @FXML private TableColumn<Supplier, String>  colCountry;
    @FXML private TableColumn<Supplier, String>  colPaymentType;
    @FXML private TableColumn<Supplier, Boolean> colActiveStatus;
    @FXML private JFXTextField txtSupplierId;
    @FXML private JFXTextField txtCompanyName;
    @FXML private JFXTextField txtContactNo;
    @FXML private JFXTextField txtContactPerson;
    @FXML private JFXTextField txtCountry;
    @FXML private JFXTextField txtEmail;
    @FXML private JFXTextField txtCity;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }
}
