package controller.supplier;


import com.google.inject.Inject;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    @FXML
    private Label lblMessage;
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

         generateId();

    }
    String genId = serviceType.getSupplierId();

    private void generateId(){
        txtSupplierId.setText(genId);
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
        String id = genId;
        if (id == null || id.isEmpty()) {
            showMessage("Supplier ID cannot be empty.", false);
            return;
        }

        String companyName = txtCompanyName.getText();
        String email = txtEmail.getText();
        String contactPerson = txtContactPerson.getText();
        String contactNoText = txtContactNo.getText();
        if (contactNoText == null || contactNoText.isEmpty()) {
            showMessage("Contact No cannot be empty.", false);
            return;
        }
        int contactNo = Integer.parseInt(contactNoText);
        String city = txtCity.getText();
        String country = txtCountry.getText();
        if (cmbPaymentType.getValue() == null) {
            showMessage("Please select a payment type.", false);
            return;
        }
        String paymentType = cmbPaymentType.getValue();
        boolean activeStatus = chkBoxStatus.isSelected();

        Supplier supplier = new Supplier(id, companyName, email, contactPerson, contactNo, city, country, paymentType, activeStatus);

        if (serviceType.addSupplier(supplier)) {
            showMessage("Supplier added successfully!", true);
            generateId();

        } else {
            showMessage("Failed to add supplier.", false);
        }
        loadTable();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please select a supplier to update.", false);
            return;
        }
        String companyName = txtCompanyName.getText();
        String email = txtEmail.getText();
        String contactPerson = txtContactPerson.getText();
        String contactNoText = txtContactNo.getText();
        if (contactNoText == null || contactNoText.isEmpty()) {
            showMessage("Contact No cannot be empty.", false);
            return;
        }
        int contactNo = Integer.parseInt(contactNoText);
        String city = txtCity.getText();
        String country = txtCountry.getText();
        if (cmbPaymentType.getValue() == null) {
            showMessage("Please select a payment type.", false);
            return;
        }
        String paymentType = cmbPaymentType.getValue();
        boolean activeStatus = chkBoxStatus.isSelected();

        Supplier supplier = new Supplier(id, companyName, email, contactPerson, contactNo, city, country, paymentType, activeStatus);

        if (serviceType.updateSupplier(supplier)) {
            showMessage("Supplier updated successfully!", true);
        } else {
            showMessage("Failed to update supplier.", false);
        }
        loadTable();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please select a supplier to delete.", false);
            return;
        }

        if (serviceType.deleteSupplier(id)) {
            showMessage("Supplier deleted successfully!", true);
        } else {
            showMessage("Failed to delete supplier.", false);
        }
        loadTable();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please enter a supplier ID to search.", false);
            return;
        }

        try {
            Supplier supplier = serviceType.searchSupplier(id);

            if (supplier != null) {

                setTextToValues(supplier);
                showMessage("Supplier found!", true);
            } else {
                showMessage("Supplier not found!", false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void showMessage(String message, boolean isSuccess) {
        lblMessage.setText(message);
        lblMessage.setStyle( "-fx-font-size: 16px;" + "-fx-font-weight: bold;" + "-fx-padding: 10px;" +
                "-fx-background-radius: 6px;" + "-fx-background-color:" + (isSuccess
                ? "linear-gradient(to right, #133846, #2D788A, #133846);"
                :"linear-gradient(to right, #632222, #9E3020, #632222);") + "-fx-text-fill:white");
    }



}
