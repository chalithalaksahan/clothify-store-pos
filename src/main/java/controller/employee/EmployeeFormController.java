package controller.employee;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import model.Employee;
import service.custom.EmployeeService;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    public TableColumn colHireDate;

    @FXML
    public JFXTextField txtName;

    @FXML
    public DatePicker dpHireDate;
    @FXML
    private JFXCheckBox chkBoxStatus;

    @FXML
    private JFXComboBox<String> cmbUserRole;

    @FXML
    private TableColumn colActiveStatus;

    @FXML
    private TableColumn colContactNo;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colEmployeeId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPassword;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableColumn colUserRole;

    @FXML
    private Label lblMessage;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtSalary;

    @Inject
    EmployeeService serviceType;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        colHireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        colActiveStatus.setCellValueFactory(new PropertyValueFactory<>("active"));

        cmbUserRole.setItems(
              FXCollections.observableArrayList("Admin", "Staff")
        );

        loadTable();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if (t1 != null) {
               setTextToValues(t1);
            }
        });

        genarateId();
    }

    private void setTextToValues(Object t1) {
        Employee employee = (Employee) t1;
        txtEmployeeId.setText(employee.getEmployeeId());
        txtName.setText(employee.getName());
        txtContactNo.setText(String.valueOf(employee.getContact()));
        txtEmail.setText(employee.getEmail());
        txtPassword.setText(employee.getPassword());
        txtConfirmPassword.setText(employee.getConfirmPassword());
        txtSalary.setText(String.valueOf(employee.getSalary()));
        cmbUserRole.setValue(employee.getUserRole());
        dpHireDate.setValue(java.time.LocalDate.parse(employee.getHireDate()));
        chkBoxStatus.setSelected(employee.isActive());
    }

    private void genarateId() {
        txtEmployeeId.setText(serviceType.getEmployeeId());
    }

    public void loadTable() {
        if (serviceType == null) return; // nothing to load
        List<Employee> employees = serviceType.getAllEmployees();

        ArrayList<Employee> employeeArrayList = new ArrayList<>();

        employees.forEach(employee -> {
            employeeArrayList.add(new Employee(
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getContact(),
                    employee.getEmail(),
                    employee.getPassword(),
                    employee.getConfirmPassword(),
                    employee.getSalary(),
                    employee.getUserRole(),
                    employee.getHireDate(),
                    employee.isActive()
            ));
        });

        tblEmployee.setItems(FXCollections.observableArrayList(employeeArrayList));


    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
        if (serviceType == null) {
            showMessage("Service is not available", false);
            return;
        }

        String id = serviceType.getEmployeeId();
        if (id == null || id.isEmpty()) {
            showMessage("Employee ID cannot be empty.", false);
            return;
        }

        String name = (txtName == null) ? "" : txtName.getText();

        String contactNoText = (txtContactNo == null) ? "" : txtContactNo.getText();
        if (contactNoText == null || contactNoText.trim().isEmpty()) {
            showMessage("Contact No cannot be empty.", false);
            return;
        }

        int contact;
        try {
            contact = Integer.parseInt(contactNoText.trim());
        } catch (NumberFormatException ex) {
            showMessage("Contact No must be numeric.", false);
            return;
        }

        String email = (txtEmail == null) ? "" : txtEmail.getText();
        String password = (txtPassword == null) ? "" : txtPassword.getText();
        String confirmPassword = (txtConfirmPassword == null) ? "" : txtConfirmPassword.getText();

        double salary = 0.0;
        try {
            String salaryText = (txtSalary == null) ? "0" : txtSalary.getText();
            salary = (salaryText == null || salaryText.trim().isEmpty()) ? 0.0 : Double.parseDouble(salaryText.trim());
        } catch (NumberFormatException ex) {
            showMessage("Salary must be a valid number.", false);
            return;
        }

        String userRole = (cmbUserRole == null) ? null : cmbUserRole.getValue();
        if (userRole == null || userRole.trim().isEmpty()) userRole = "Staff";

        if (dpHireDate == null || dpHireDate.getValue() == null) {
            showMessage("Hire date is required.", false);
            return;
        }
        String hireDate = dpHireDate.getValue().toString();
        boolean isActive = (chkBoxStatus != null) && chkBoxStatus.isSelected();

        Employee employee = new Employee(id, name, contact, email, password, confirmPassword, salary, userRole, hireDate, isActive);

        if(serviceType.addEmployee(employee)){
            showMessage("Employee added successfully!", true);
            genarateId();
            loadTable();
        } else {
            showMessage("Failed to add employee. Please try again.", false);
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = (txtEmployeeId == null) ? "" : txtEmployeeId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please select a Employee to delete.", false);
            return;
        }
        if (serviceType.deleteEmployee(id)) {
            showMessage("Employee deleted successfully!", true);
        } else {
            showMessage("Failed to delete employee. Please try again.", false);
        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event)  {
        String id = (txtEmployeeId == null) ? "" : txtEmployeeId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please enter an Employee ID to search.", false);
            return;
        }
        try {
            Employee employee = serviceType.searchEmployee(id);

        if (employee != null) {
            setTextToValues(employee);
            showMessage("Employee found!", true);
        } else {
            showMessage("Employee not found with ID: " + id, false);
        }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        String id = (txtEmployeeId == null) ? "" : txtEmployeeId.getText();
        if (id == null || id.isEmpty()) {
            showMessage("Please select a Employee to update.", false);
            return;
        }
        String name = (txtName == null) ? "" : txtName.getText();

        String contactNoText = (txtContactNo == null) ? "" : txtContactNo.getText();
        if (contactNoText == null || contactNoText.isEmpty()) {
            showMessage("Contact No cannot be empty.", false);
            return;
        }
        int contactNo;
        try {
            contactNo = Integer.parseInt(contactNoText.trim());
        } catch (NumberFormatException ex) {
            showMessage("Contact No must be numeric!", false);
            return;
        }

        String email = (txtEmail == null) ? "" : txtEmail.getText();
        String password = (txtPassword == null) ? "" : txtPassword.getText();
        String confirmPassword = (txtConfirmPassword == null) ? "" : txtConfirmPassword.getText();
        double salary = 0.0;
        try {
            String salaryText = (txtSalary == null) ? "0" : txtSalary.getText();
            salary = (salaryText == null || salaryText.trim().isEmpty()) ? 0.0 : Double.parseDouble(salaryText.trim());
        } catch (NumberFormatException ex) {
            showMessage("Salary must be a valid number.", false);
            return;
        }
        String userRole = (cmbUserRole == null) ? "Staff" : cmbUserRole.getValue();
        if (userRole == null) userRole = "Staff";
        if (dpHireDate == null || dpHireDate.getValue() == null) {
            showMessage("Hire date is required.", false);
            return;
        }
        String hireDate = dpHireDate.getValue().toString();
        boolean isActive = (chkBoxStatus != null) && chkBoxStatus.isSelected();

            Employee employee = new Employee(id, name, contactNo, email, password, confirmPassword, salary, userRole, hireDate, isActive);

            if (serviceType != null && serviceType.updateEmployee(employee)) {
                showMessage("Employee updated successfully!", true);
            } else {
                showMessage("Failed to update employee. Please try again.", false);
            }
        loadTable();
    }

    public void txtContactNoOnKeyTyped(KeyEvent keyEvent) {
        if (txtContactNo == null) return;
        String contact = txtContactNo.getText();
        if (contact == null || contact.isEmpty()) return;
        int last = contact.charAt(contact.length() - 1);
        // use Character.isDigit for clarity
        if (!Character.isDigit(last)) {
            showMessage("Contact No must be numeric!", false);
        } else if (contact.length() > 10) {
            showMessage("Contact No cannot exceed 10 digits!", false);
        } else {
            lblMessage.setText("");
            lblMessage.setStyle("");
            if (contact.length() == 10) {
                if (dpHireDate != null) dpHireDate.requestFocus();
            }

        }
    }

    public void txtEmailOnKeyPressed(KeyEvent e) {
        if (txtEmail == null) return;
        String email = txtEmail.getText();
        if (email == null || email.isEmpty()) return; // avoid charAt on empty
        char ch = email.charAt(email.length()-1);
        System.out.println(ch);
        if(email.contains("@") && email.contains(".")){
            lblMessage.setText("");
            lblMessage.setStyle("");

        } else{
            showMessage("Please enter a valid email address!", false);
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
