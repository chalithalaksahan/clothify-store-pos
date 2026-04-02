package controller.settings;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class SettingsFormController {

    @FXML
    private JFXComboBox<?> cmbCurrency;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXPasswordField txtCurrentPassword;

    @FXML
    private JFXTextField txtEmailAddress;

    @FXML
    private JFXTextField txtInvoicePrefix;

    @FXML
    private JFXTextField txtLowStockAlert;

    @FXML
    private JFXPasswordField txtNewPassword;

    @FXML
    private JFXTextArea txtStoreAddress;

    @FXML
    private JFXTextField txtStoreName;

    @FXML
    private JFXTextField txtTaxRate;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtVatTin;

}
