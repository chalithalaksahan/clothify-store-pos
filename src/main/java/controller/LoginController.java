package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXButton;
import config.AppModule;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private Injector injector;

    @Inject
    LoginService loginService;
    @Inject
    UserService userService;


    @FXML
    public void btnLoginOnAction(MouseEvent mouseEvent) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        loginService.login(email,password);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      injector = Guice.createInjector(new AppModule());
    }

    @FXML
    public void btnAddUserOnAction(MouseEvent mouseEvent) {
        userService.createUser("admin","12345678");
    }
}
