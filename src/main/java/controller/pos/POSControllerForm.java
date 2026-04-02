package controller.pos;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class POSControllerForm {

    @FXML
    private JFXComboBox<?> cmbCustomer;

    @FXML
    private JFXComboBox<?> cmbPaymentMethod;

    @FXML
    private TableColumn<?, ?> colColor;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colIndex;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblSubTotal;

    @FXML
    private Label lblTax;

    @FXML
    private TableView<?> tblInvoiceItems;

    @FXML
    private JFXTextField txtAmountPaid;

    @FXML
    private JFXTextField txtBarcodeScan;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;

}
