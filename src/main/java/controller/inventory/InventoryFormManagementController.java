package controller.inventory;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InventoryFormManagementController {

    @FXML
    private JFXComboBox<?> cmbCategory;

    @FXML
    private JFXComboBox<?> cmbItem;

    @FXML
    private JFXComboBox<?> cmbSelectPoForGrn;

    @FXML
    private JFXComboBox<?> cmbSupplier;

    @FXML
    private TableColumn<?, ?> colAvailableQty;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colGrnDateList;

    @FXML
    private TableColumn<?, ?> colGrnId;

    @FXML
    private TableColumn<?, ?> colGrnItem;

    @FXML
    private TableColumn<?, ?> colGrnOrdered;

    @FXML
    private TableColumn<?, ?> colGrnPoId;

    @FXML
    private TableColumn<?, ?> colGrnPrice;

    @FXML
    private TableColumn<?, ?> colGrnReceived;

    @FXML
    private TableColumn<?, ?> colGrnSupplier;

    @FXML
    private TableColumn<?, ?> colGrnTotal;

    @FXML
    private TableColumn<?, ?> colPoDateList;

    @FXML
    private TableColumn<?, ?> colPoId;

    @FXML
    private TableColumn<?, ?> colPoItem;

    @FXML
    private TableColumn<?, ?> colPoQty;

    @FXML
    private TableColumn<?, ?> colPoSupplier;

    @FXML
    private TableColumn<?, ?> colPoTotal;

    @FXML
    private TableColumn<?, ?> colPoTotalList;

    @FXML
    private TableColumn<?, ?> colPoUnitPrice;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colStockValue;

    @FXML
    private DatePicker dpGrnDate;

    @FXML
    private DatePicker dpPoDate;

    @FXML
    private Label lblGrandTotal;

    @FXML
    private Label lblGrnTotal;

    @FXML
    private Label lblSubTotal;

    @FXML
    private Label lblTax;

    @FXML
    private TableView<?> tblGrnItems;

    @FXML
    private TableView<?> tblGrnList;

    @FXML
    private TableView<?> tblInventory;

    @FXML
    private TableView<?> tblPoItems;

    @FXML
    private TableView<?> tblPoList;

    @FXML
    private JFXTextField txtGrnSupplier;

    @FXML
    private JFXTextArea txtRemarks;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    void btnAddPoItem(ActionEvent event) {

    }

    @FXML
    void btnApproveAndUpdateGrn(ActionEvent event) {

    }

    @FXML
    void btnCancelPo(ActionEvent event) {

    }

    @FXML
    void btnDeletePoItem(ActionEvent event) {

    }

    @FXML
    void btnPrintPo(ActionEvent event) {

    }

    @FXML
    void btnSavePo(ActionEvent event) {

    }

    @FXML
    void btnUpdatePoItem(ActionEvent event) {

    }

}
