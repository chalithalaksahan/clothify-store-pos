package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXButton;
import config.AppModule;
import dto.LoginResult;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.custom.LoginService;
import service.custom.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public JFXButton btnAddUserOnAction;

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
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        LoginResult result = loginService.login(email, password);

        if (result.isSuccess()) {
            showMessage(result.getMessage(), true);
        } else {
            showMessage(result.getMessage(), false);
        }

        System.out.println(result.isSuccess());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injector = Guice.createInjector(new AppModule());
        injector.injectMembers(this);
    }

    @FXML
    public void btnAddUserOnAction(MouseEvent mouseEvent) {
        try {
            userService.createUser("admin", "12345678");
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
