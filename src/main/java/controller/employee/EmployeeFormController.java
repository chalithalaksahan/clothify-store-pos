package controller.employee;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.EmployeeDTO;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import service.custom.EmployeeService;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    // --- FXML Injections ---
    @FXML private JFXTextField txtEmployeeId;
    @FXML private JFXTextField txtFirstName;
    @FXML private JFXTextField txtLastName;
    @FXML private JFXTextField txtContactNo;
    @FXML private JFXTextField txtEmail;
    @FXML private JFXPasswordField txtPassword;
    @FXML private JFXPasswordField txtConfirmPassword;
    @FXML private JFXTextField txtSalary;
    @FXML private DatePicker dpHireDate;
    @FXML private JFXComboBox<String> cmbUserRole;
    @FXML private JFXCheckBox chkBoxStatus;
    @FXML private Label lblMessage;

    // --- Table & Columns ---
    @FXML private TableView<EmployeeDTO> tblEmployee;
    @FXML private TableColumn<EmployeeDTO, String> colEmployeeId;
    @FXML private TableColumn<EmployeeDTO, String> colFirstName;
    @FXML private TableColumn<EmployeeDTO, String> colLastName;
    @FXML private TableColumn<EmployeeDTO, Integer> colContactNo;
    @FXML private TableColumn<EmployeeDTO, String> colEmail;
    @FXML private TableColumn<EmployeeDTO, String> colPassword;
    @FXML private TableColumn<EmployeeDTO, Double> colSalary;
    @FXML private TableColumn<EmployeeDTO, String> colUserRole;
    @FXML private TableColumn<EmployeeDTO, LocalDate> colHireDate;
    @FXML private TableColumn<EmployeeDTO, Boolean> colActiveStatus;

    @Inject
    EmployeeService serviceType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Map table columns exactly to EmployeeDTO fields
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        colHireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        colActiveStatus.setCellValueFactory(new PropertyValueFactory<>("active"));

        cmbUserRole.setItems(FXCollections.observableArrayList("Admin", "Staff"));

        loadTable();
        generateId();

        // Listen for table row selections
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                setTextToValues(newSelection);
            }
        });
    }
    private void generateId(){
        txtEmployeeId.setText(serviceType.getEmployeeId());
    }

    private void setTextToValues(EmployeeDTO dto) {
        txtEmployeeId.setText(dto.getEmployeeId());
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtContactNo.setText(String.valueOf(dto.getContactNo()));
        txtEmail.setText(dto.getEmail());
        txtPassword.setText(dto.getPassword());
        txtConfirmPassword.setText(dto.getPassword());
        txtSalary.setText(String.valueOf(dto.getSalary()));
        cmbUserRole.setValue(dto.getUserRole());
        dpHireDate.setValue(dto.getHireDate());
        chkBoxStatus.setSelected(dto.isActive());
    }

    public void loadTable() {
        if (serviceType == null) return;
        List<EmployeeDTO> employees = null;
        try {
            employees = serviceType.getAllEmployees();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tblEmployee.setItems(FXCollections.observableArrayList(employees));
    }

    @FXML
    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
        if (serviceType == null) return;

        EmployeeDTO dto = extractDTOFromFields();
        if (dto == null) return;

        dto.setEmployeeId(serviceType.getEmployeeId());
        dto.setEmail(txtEmail.getText() == null ? "" : txtEmail.getText());

        if (serviceType.addEmployee(dto)) {
            showMessage("Employee added successfully!", true);
            clearFields();
            generateId();
            loadTable();
        } else {
            showMessage("Failed to add employee.", false);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please select an Employee to update.", false);
            return;
        }

        EmployeeDTO dto = extractDTOFromFields();
        if (dto == null) return;

        try {
            dto.setEmployeeId(id);
            if (serviceType != null && serviceType.updateEmployee(dto)) {
                showMessage("Employee updated successfully!", true);
                loadTable();
            } else {
                showMessage("Failed to update employee.", false);
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid Employee ID.", false);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id= txtEmployeeId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please select an Employee to delete.", false);
            return;
        }

        try {
            if (serviceType.deleteEmployee(id)) {
                showMessage("Employee deleted successfully!", true);
                clearFields();
                loadTable();
            } else {
                showMessage("Failed to delete employee.", false);
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid Employee ID format.", false);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event)  {
        String id = txtEmployeeId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please enter an Employee ID.", false);
            return;
        }

        try {
            EmployeeDTO dto = serviceType.searchEmployee(id);

            if (dto != null) {
                setTextToValues(dto);
                showMessage("Employee found!", true);
            } else {
                showMessage("Employee not found.", false);
            }
        } catch (NumberFormatException e) {
            showMessage("Employee ID must be a number.", false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private EmployeeDTO extractDTOFromFields() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(txtEmployeeId.getId());

        dto.setFirstName(txtFirstName.getText() == null ? "" : txtFirstName.getText().trim());
        if (dto.getFirstName().isEmpty()) {
            showMessage("First Name cannot be empty.", false);
            return null;
        }

        dto.setLastName(txtLastName.getText() == null ? "" : txtLastName.getText().trim());
        if (dto.getLastName().isEmpty()) {
            showMessage("Last Name cannot be empty.", false);
            return null;
        }

        try {
            dto.setContactNo(Integer.parseInt(txtContactNo.getText().trim()));
        } catch (Exception ex) {
            showMessage("Contact No must be numeric.", false);
            return null;
        }


        dto.setPassword(txtPassword.getText() == null ? "" : txtPassword.getText());
        dto.setConfirmPassword(txtConfirmPassword.getText() == null ? "" : txtConfirmPassword.getText());

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            showMessage("Passwords do not match.", false);
            return null;
        }

        try {
            dto.setSalary(txtSalary.getText().isEmpty() ? 0.0 : Double.parseDouble(txtSalary.getText()));
        } catch (Exception ex) {
            showMessage("Salary must be a valid number.", false);
            return null;
        }

        dto.setUserRole(cmbUserRole.getValue() == null ? "Staff" : cmbUserRole.getValue());

        if (dpHireDate.getValue() == null) {
            showMessage("Hire date is required.", false);
            return null;
        }
        dto.setHireDate(dpHireDate.getValue());
        dto.setActive(chkBoxStatus.isSelected());

        return dto;
    }

    private void clearFields() {
        txtEmployeeId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtContactNo.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtSalary.clear();
        dpHireDate.setValue(null);
        cmbUserRole.getSelectionModel().clearSelection();
        chkBoxStatus.setSelected(false);
    }

    public void txtContactNoOnKeyTyped(KeyEvent keyEvent) {
        String contact = txtContactNo.getText();
        if (contact == null || contact.isEmpty()) return;

        char last = contact.charAt(contact.length() - 1);
        if (!Character.isDigit(last)) {
            showMessage("Contact No must be numeric!", false);
        } else if (contact.length() > 10) {
            showMessage("Contact No cannot exceed 10 digits!", false);
        } else {
            lblMessage.setText("");
            lblMessage.setStyle("");
            if (contact.length() == 10 && dpHireDate != null) {
                dpHireDate.requestFocus();
            }
        }
    }

    public void txtEmailOnKeyPressed(KeyEvent e) {
        String email = txtEmail.getText();
        if (email == null || email.isEmpty()) return;

        if (email.contains("@") && email.contains(".")) {
            lblMessage.setText("");
            lblMessage.setStyle("");
        } else {
            showMessage("Please enter a valid email address!", false);
        }
    }

    private void showMessage(String message, boolean isSuccess) {
        lblMessage.setText(message);
        lblMessage.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px; " +
                "-fx-background-radius: 6px; -fx-background-color: " +
                (isSuccess ? "linear-gradient(to right, #133846, #2D788A, #133846);"
                        : "linear-gradient(to right, #632222, #9E3020, #632222);") +
                " -fx-text-fill: white;");
    }
}