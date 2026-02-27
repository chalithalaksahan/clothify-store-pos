package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXButton;
import config.AppModule;
import dto.LoginResult;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.custom.LoginService;
import service.custom.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public JFXButton btnAddUserOnAction;

    @FXML
    public AnchorPane loginAnchorPane;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMessage;

    private Injector injector;

    @Inject
    LoginService loginService;
    @Inject
    UserService userService;

    @FXML
    public void btnLoginOnAction(MouseEvent mouseEvent) {
        handleLogin();
    }
    @FXML
    public void btnLoginOnActionClick(MouseEvent mouseEvent) {
        handleLogin();
    }

    private void handleLogin() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        LoginResult result = loginService.login(email, password);

        if (result.isSuccess() && result.getUser() != null && result.getUser().getUserRole() == 1) {
            loadDashboard();
        } else if (result.isSuccess() && result.getUser() != null && result.getUser().getUserRole() == 2){
            loadStaffMainMenu();
        }else{
            showMessage(result.getMessage(), false);
        }
    }

    private void loadStaffMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/staff_main_menu.fxml"));
            loader.setControllerFactory(injector::getInstance);
            Scene dashboardScene = new Scene(loader.load());

            Stage stage = (Stage) loginAnchorPane.getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.setMaximized(true);
        } catch (Exception e) {
            showMessage("Failed to load staff Main menu: " + e.getMessage(), false);
        }
    }

    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            loader.setControllerFactory(injector::getInstance);
            Scene dashboardScene = new Scene(loader.load());

            Stage stage = (Stage) loginAnchorPane.getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.setMaximized(true);
        } catch (Exception e) {
            showMessage("Failed to load dashboard: " + e.getMessage(), false);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injector = Guice.createInjector(new AppModule());
        injector.injectMembers(this);

        // Tab on email → move focus to password
        txtEmail.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                txtPassword.requestFocus();
                event.consume();
            }
        });

        // Enter on password → trigger login
        txtPassword.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
                event.consume();
            }
        });
    }

    @FXML
    public void btnAddUserOnAction(MouseEvent mouseEvent) {
        try {
            userService.createUser("Staff", "12345678");
            showMessage("User created successfully!", true);
        } catch (Exception e) {
            showMessage("Failed to create user: " + e.getMessage(), false);
        }
    }

    // -- green = success, red = error ---
    private void showMessage(String message, boolean isSuccess) {
        lblMessage.setText(message);
        lblMessage.setStyle(isSuccess
                ? "-fx-text-fill: #2e7d32; -fx-font-weight: bold;"   // green
                : "-fx-text-fill: #c62828; -fx-font-weight: bold;");  // red
    }


}
