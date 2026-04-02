package controller.product;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

public class ProductFormController {

    @FXML
    private ToggleGroup categoryStatus;

    @FXML
    private JFXComboBox<?> cmbParentCat;

    @FXML
    private JFXComboBox<?> cmbProdCategory;

    @FXML
    private JFXComboBox<?> cmbProdSupplier;

    @FXML
    private TableColumn<?, ?> colAddCategory;

    @FXML
    private TableColumn<?, ?> colAddCost;

    @FXML
    private TableColumn<?, ?> colAddName;

    @FXML
    private TableColumn<?, ?> colAddPrice;

    @FXML
    private TableColumn<?, ?> colAddQty;

    @FXML
    private TableColumn<?, ?> colAddSku;

    @FXML
    private TableColumn<?, ?> colAddStatus;

    @FXML
    private TableColumn<?, ?> colAddSupplier;

    @FXML
    private TableColumn<?, ?> colAllProdCategory;

    @FXML
    private TableColumn<?, ?> colAllProdCost;

    @FXML
    private TableColumn<?, ?> colAllProdName;

    @FXML
    private TableColumn<?, ?> colAllProdPrice;

    @FXML
    private TableColumn<?, ?> colAllProdQty;

    @FXML
    private TableColumn<?, ?> colAllProdSku;

    @FXML
    private TableColumn<?, ?> colAllProdStatus;

    @FXML
    private TableColumn<?, ?> colAllProdSupplier;

    @FXML
    private TableColumn<?, ?> colCatListCode;

    @FXML
    private TableColumn<?, ?> colCatListDesc;

    @FXML
    private TableColumn<?, ?> colCatListName;

    @FXML
    private TableColumn<?, ?> colCatListParent;

    @FXML
    private TableColumn<?, ?> colCatListStatus;

    @FXML
    private JFXRadioButton rbtnActive;

    @FXML
    private JFXRadioButton rbtnInactive;

    @FXML
    private TableView<?> tblAddProducts;

    @FXML
    private TableView<?> tblAllProducts;

    @FXML
    private TableView<?> tblCategoryList;

    @FXML
    private JFXTextField txtCatCode;

    @FXML
    private JFXTextField txtCatDesc;

    @FXML
    private JFXTextField txtCatName;

    @FXML
    private JFXTextField txtColor;

    @FXML
    private JFXTextField txtCostPrice;

    @FXML
    private JFXTextField txtMinQty;

    @FXML
    private JFXTextField txtProdDesc;

    @FXML
    private JFXTextField txtProdName;

    @FXML
    private JFXTextField txtReorderLevel;

    @FXML
    private JFXTextField txtSearchProduct;

    @FXML
    private JFXTextField txtSellingPrice;

    @FXML
    private JFXTextField txtSize;

    @FXML
    private JFXTextField txtSkuCode;

}
