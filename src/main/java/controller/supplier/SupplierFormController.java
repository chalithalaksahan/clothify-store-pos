package controller.supplier;


import com.google.inject.Inject;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Supplier;
import service.custom.SupplierService;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {



    @FXML private JFXCheckBox chkBoxStatus;
    @FXML private JFXComboBox<String> cmbPaymentType;
    @FXML private TableView<Supplier> tblSupplier;
    @FXML private TableColumn colSupplierId;
    @FXML private TableColumn  colCompanyName;
    @FXML private TableColumn  colEmail;
    @FXML private TableColumn  colContactPerson;
    @FXML private TableColumn  colContactNo;
    @FXML private TableColumn  colCity;
    @FXML private TableColumn  colCountry;
    @FXML private TableColumn  colPaymentType;
    @FXML private TableColumn  colActiveStatus;
    @FXML private JFXTextField txtSupplierId;
    @FXML private JFXTextField txtCompanyName;
    @FXML private JFXTextField txtContactNo;
    @FXML private JFXTextField txtContactPerson;
    @FXML private JFXTextField txtCountry;
    @FXML private JFXTextField txtEmail;
    @FXML private JFXTextField txtCity;

    @Inject
    SupplierService serviceType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactPerson.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colActiveStatus.setCellValueFactory(new PropertyValueFactory<>("active"));

        cmbPaymentType.setItems(
                FXCollections.observableArrayList(Arrays.asList("Cash", "Credit Card", "Bank Transfer"))
        );

        loadTable();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if (t1 == null) return;
            setTextToValues(t1);
        });
    }

    private void setTextToValues(Supplier supplier) {
        txtSupplierId.setText(supplier.getSupplierId());
        txtCompanyName.setText(supplier.getCompanyName());
        txtEmail.setText(supplier.getEmail());
        txtContactPerson.setText(supplier.getContactPerson());
        txtContactNo.setText(String.valueOf(supplier.getContactNo()));
        txtCity.setText(supplier.getCity());
        txtCountry.setText(supplier.getCountry());
        cmbPaymentType.setValue(supplier.getPaymentType());
        chkBoxStatus.setSelected(supplier.isActive());

    }

    public void loadTable(){

        List<Supplier> suppliers;
        try {
            suppliers = serviceType.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Supplier> supplierArrayList = new ArrayList<>();

        suppliers.forEach(supplier -> {
            supplierArrayList.add(new Supplier(
                    supplier.getSupplierId(),
                    supplier.getCompanyName(),
                    supplier.getEmail(),
                    supplier.getContactPerson(),
                    supplier.getContactNo(),
                    supplier.getCity(),
                    supplier.getCountry(),
                    supplier.getPaymentType(),
                    supplier.isActive()
            ));
        });

        tblSupplier.setItems(FXCollections.observableArrayList(supplierArrayList));

    }


    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        String companyName = txtCompanyName.getText();
        String email = txtEmail.getText();
        String contactPerson = txtContactPerson.getText();
        String contactNoText = txtContactNo.getText();
        if (contactNoText == null || contactNoText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Contact No cannot be empty.").show();
            return;
        }
        int contactNo = Integer.parseInt(contactNoText);
        String city = txtCity.getText();
        String country = txtCountry.getText();
        if (cmbPaymentType.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment type.").show();
            return;
        }
        String paymentType = cmbPaymentType.getValue();
        boolean activeStatus = chkBoxStatus.isSelected();

        Supplier supplier = new Supplier(id, companyName, email, contactPerson, contactNo, city, country, paymentType, activeStatus);

        if (serviceType.addSupplier(supplier)) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier added successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to add supplier").show();
        }
        loadTable();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        if (id == null || id.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a supplier to update.").show();
            return;
        }
        String companyName = txtCompanyName.getText();
        String email = txtEmail.getText();
        String contactPerson = txtContactPerson.getText();
        String contactNoText = txtContactNo.getText();
        if (contactNoText == null || contactNoText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Contact No cannot be empty.").show();
            return;
        }
        int contactNo = Integer.parseInt(contactNoText);
        String city = txtCity.getText();
        String country = txtCountry.getText();
        if (cmbPaymentType.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment type.").show();
            return;
        }
        String paymentType = cmbPaymentType.getValue();
        boolean activeStatus = chkBoxStatus.isSelected();

        Supplier supplier = new Supplier(id, companyName, email, contactPerson, contactNo, city, country, paymentType, activeStatus);

        if (serviceType.updateSupplier(supplier)) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update supplier").show();
        }
        loadTable();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        if (id == null || id.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a supplier to delete.").show();
            return;
        }

        if (serviceType.deleteSupplier(id)) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete supplier").show();
        }
        loadTable();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        if (id == null || id.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a supplier ID to search.").show();
            return;
        }

        try {
            Supplier supplier = serviceType.searchSupplier(id);

            if (supplier != null) {

                setTextToValues(supplier);
                new Alert(Alert.AlertType.INFORMATION, "Supplier found!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier not found!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
