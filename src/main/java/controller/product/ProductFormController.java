package controller.product;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dto.CategoryDTO;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.custom.CategoryService;
import util.CategoryType;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {

    public Label lblMessage;
    @FXML
    private ToggleGroup categoryStatus;

    @FXML
    private JFXComboBox<String> cmbParentCat;

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
    private TableColumn<CategoryDTO, String> colCatListCode;

    @FXML
    private TableColumn<CategoryDTO, String> colCatListDesc;

    @FXML
    private TableColumn<CategoryDTO, String> colCatListName;

    @FXML
    private TableColumn<CategoryDTO, String> colCatListParent;

    @FXML
    private TableColumn<CategoryDTO, Boolean> colCatListStatus;

    @FXML
    private JFXRadioButton rbtnActive;

    @FXML
    private JFXRadioButton rbtnInactive;

    @FXML
    private TableView<CategoryDTO> tblAddProducts;

    @FXML
    private TableView<CategoryDTO> tblAllProducts;

    @FXML
    private TableView<CategoryDTO> tblCategoryList;

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

    @Inject
    CategoryService serviceType;

    public void btnSearchCategoryOnAction(ActionEvent actionEvent) {
        String code = txtCatCode.getText();

        CategoryDTO category = null;
        try {
            category = serviceType.searchCategory(code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (category != null){
            setTextToValues(category);
            showMessage("Category found: " + category.getCategoryName(), true);
        }else {
            showMessage("Category not found with code: " + code, false);
        }

    }

    private void setTextToValues(CategoryDTO category) {
        txtCatCode.setText(category.getCategoryCode());
        txtCatName.setText(category.getCategoryName());
        cmbParentCat.setValue(category.getParentCategory());
        if ("Active".equalsIgnoreCase(category.getStatus())) {
            rbtnActive.setSelected(true);
        } else {
            rbtnInactive.setSelected(true);
        }
        txtCatDesc.setText(category.getDescription());
    }

    public void btnAddCategoryOnAction(ActionEvent actionEvent) {
        String code = txtCatCode.getText();
        String name = txtCatName.getText();
        String parent = cmbParentCat.getValue();
        boolean isActive = rbtnActive.isSelected();
        String status = isActive ? "Active" : "Inactive";
        String desc = txtCatDesc.getText();

        CategoryDTO category = new CategoryDTO(code, name, parent, status, desc);

        if (serviceType.creatCategory(category)) {
            loadCategoryTable();
            clearCategoryFields();
            showMessage("Category created successfully", true);
        } else {
            showMessage("Failed to create category", false);
        }
    }

    public void btnDeleteCategoryOnAction(ActionEvent actionEvent) {
        String code = txtCatCode.getText();
        if (serviceType.deleteCategory(code)) {
            loadCategoryTable();
            clearCategoryFields();
            showMessage("Category deleted successfully", true);
        } else {
            showMessage("Failed to delete category", false);
        }

    }

    public void btnUpdateCategoryOnAction(ActionEvent actionEvent) {

        String name = txtCatName.getText();
        String parent = cmbParentCat.getValue().toString();
        boolean isActive = rbtnActive.isSelected();
        String status = isActive ? "Active" : "Inactive";
        String desc = txtCatDesc.getText();
        String code = txtCatCode.getText();

        CategoryDTO category = new CategoryDTO(code, name, parent, status, desc);

        if(serviceType.updateCategory(category)) {
            loadCategoryTable();
            clearCategoryFields();
            showMessage("Category updated successfully", true);
        } else {
            showMessage("Failed to update category", false);
        }
    }
    private void showMessage(String message, boolean isSuccess) {
        lblMessage.setText(message);
        lblMessage.setStyle( "-fx-font-size: 16px;" + "-fx-font-weight: bold;" + "-fx-padding: 10px;" +
                "-fx-background-radius: 6px;" + "-fx-background-color:" + (isSuccess
                ? "linear-gradient(to right, #133846, #2D788A, #133846);"
                :"linear-gradient(to right, #632222, #9E3020, #632222);") + "-fx-text-fill:white");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCatListCode.setCellValueFactory(new PropertyValueFactory<>("categoryCode"));
        colCatListName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colCatListParent.setCellValueFactory(new PropertyValueFactory<>("parentCategory"));
        colCatListStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCatListDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadCategoryTable();

        cmbParentCat.setItems(
                FXCollections.observableArrayList(Arrays.stream(CategoryType.values()).map(Enum::name).toList())
        );
        loadCategoryTable();

        tblCategoryList.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if (t1 != null) {
                setTextToValues(t1);
            }
        });
    }

    private void loadCategoryTable() {
        try {
            List<CategoryDTO> categories = serviceType.getAllCategories();
            tblCategoryList.setItems(FXCollections.observableArrayList(categories));
        } catch (Exception e) {
            showMessage("Failed to load categories: " + e.getMessage(), false);
        }
    }
    private void clearCategoryFields() {
        txtCatCode.setText("");
        txtCatName.setText("");
        cmbParentCat.setValue("");
        rbtnActive.setSelected(true);
        txtCatDesc.setText("");
    }
}
