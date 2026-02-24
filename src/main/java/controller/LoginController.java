package controller;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.custom.LoginService;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @Inject
    LoginService loginService;

    public void btnLoginOnAction(MouseEvent mouseEvent) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

    }
}
