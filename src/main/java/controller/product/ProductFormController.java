package controller.product;

import Entity.Supplier;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dto.CategoryDTO;
import dto.ProductDTO;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import service.custom.CategoryService;
import service.custom.ProductService;
import service.custom.SupplierService;
import util.CategoryType;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ProductFormController implements Initializable {

    public Label lblMessage;
    @FXML
    private ToggleGroup categoryStatus;

    @FXML
    private JFXComboBox<String> cmbParentCat;

    @FXML
    private JFXComboBox<CategoryDTO> cmbProdCategory;

    @FXML
    private JFXComboBox<Supplier> cmbProdSupplier;

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
    CategoryService catServiceType;

    @Inject
    SupplierService supServiceType;

    @Inject
    ProductService proServiceType;

    public void btnSearchCategoryOnAction(ActionEvent actionEvent) {
        String code = txtCatCode.getText();

        CategoryDTO category = null;
        try {
            category = catServiceType.searchCategory(code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (category != null){
            setTextToValuesForCat(category);
            showMessage("Category found: " + category.getCategoryName(), true);
        }else {
            showMessage("Category not found with code: " + code, false);
        }

    }

    private void setTextToValuesForCat(CategoryDTO category) {
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

        if (catServiceType.creatCategory(category)) {
            loadCategoryTable();
            clearCategoryFields();
            showMessage("Category created successfully", true);
        } else {
            showMessage("Failed to create category", false);
        }
    }

    public void btnDeleteCategoryOnAction(ActionEvent actionEvent) {
        String code = txtCatCode.getText();
        if (catServiceType.deleteCategory(code)) {
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

        try {
            if(catServiceType.updateCategory(category)) {
                loadCategoryTable();
                clearCategoryFields();
                showMessage("Category updated successfully", true);
            } else {
                showMessage("Failed to update category", false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        loadCategories();
        loadSuppliers();

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
                setTextToValuesForCat(t1);
            }
        });
    }

    private void loadCategoryTable() {
        try {
            List<CategoryDTO> categories = catServiceType.getAllCategories();
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

    public void btnSearchProductOnAction(ActionEvent actionEvent) {
        String skuCode = txtSkuCode.getText();

        if (skuCode == null || skuCode.isEmpty()) {
            showMessage("Please enter a SKU code to search.", false);
            return;
        }
        if (proServiceType.searchProduct(skuCode) != null){
            ProductDTO product = proServiceType.searchProduct(skuCode);
            setTextToValuesForProduct(product);
            showMessage("Product found: " + product.getName(), true);
        }else {
            showMessage("Product not found with SKU code: " + skuCode, false);
        }

    }

    private void setTextToValuesForProduct(ProductDTO product) {
        txtProdName.setText(product.getName());
        cmbProdCategory.setValue(product.getCategory());
        cmbProdSupplier.setValue(product.getSupplier());
        txtCostPrice.setText(product.getCostPrice());
        txtSellingPrice.setText(product.getSellingPrice());
        txtMinQty.setText(product.getMinQty());
        txtReorderLevel.setText(product.getReOrderLvl());
        txtProdDesc.setText(product.getDescription());
        txtColor.setText(product.getColor());
        txtSize.setText(product.getSize());
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) {
        String skuCode = txtSkuCode.getText();
        String proName = txtProdName.getText();
        CategoryDTO category = cmbProdCategory.getValue();
        Supplier supplier = cmbProdSupplier.getValue();
        String costPrice = txtCostPrice.getText();
        String sellingPrice = txtSellingPrice.getText();
        String minQty = txtMinQty.getText();
        String reOrderLvl = txtReorderLevel.getText();
        String proDesc = txtProdDesc.getText();
        String color = txtColor.getText();
        String size = txtSize.getText();

        ProductDTO product = new ProductDTO(skuCode, proName, category, supplier, costPrice, sellingPrice, minQty, reOrderLvl, proDesc, color, size);

        if(proServiceType.createProduct(product)){
            showMessage("Product created successfully", true);
        }else{
            showMessage("Failed to create product", false);
        }
    }

    public void btnUpdateProductOnAction(ActionEvent actionEvent) {
        String proName = txtProdName.getText();
        CategoryDTO category = cmbProdCategory.getValue();
        Supplier supplier = cmbProdSupplier.getValue();
        String costPrice = txtCostPrice.getText();
        String sellingPrice = txtSellingPrice.getText();
        String minQty = txtMinQty.getText();
        String reOrderLvl = txtReorderLevel.getText();
        String proDesc = txtProdDesc.getText();
        String color = txtColor.getText();
        String size = txtSize.getText();
        String skuCode = txtSkuCode.getText();

        ProductDTO product = new ProductDTO(skuCode, proName, category, supplier, costPrice, sellingPrice, minQty, reOrderLvl, proDesc, color, size);

        if (proServiceType.updateProduct(product)){
            showMessage("Product updated successfully", true);
        }else{
            showMessage("Failed to update product", false);
        }
    }

    public void btnDeleteProductOnAction(ActionEvent actionEvent) {
        String skuCode = txtSkuCode.getText();

        if (proServiceType.deleteProduct(skuCode)){
            showMessage("Product deleted successfully", true);
        }else {
            showMessage("Failed to delete product", false);
        }
    }


    //---------------------------------------------------------------------- universal combo box for entity or DTO -------------------------------------------------
    public class ComboBoxSearchUtil {
        public static <T> void makeSearchable(ComboBox<T> comboBox, ObservableList<T> items, Function<T, String> stringMapper) {

            // 1. Wrap the list
            FilteredList<T> filteredList = new FilteredList<>(items, p -> true);
            comboBox.setItems(filteredList);
            comboBox.setEditable(true);

            // 2. Set the String Converter dynamically using your Function
            comboBox.setConverter(new StringConverter<T>() {
                @Override
                public String toString(T object) {
                    // Extracts the string using the getter you pass in
                    return object == null ? "" : stringMapper.apply(object);
                }

                @Override
                public T fromString(String string) {
                    if (string == null || string.isEmpty()) return null;
                    return items.stream()
                            .filter(item -> {
                                String itemString = stringMapper.apply(item);
                                return itemString != null && itemString.equalsIgnoreCase(string);
                            })
                            .findFirst()
                            .orElse(null);
                }
            });

            // 3. Add the Universal Search Listener
            comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                if (comboBox.getSelectionModel().getSelectedItem() != null) {
                    return; // Prevent text deletion upon selection
                }

                Platform.runLater(() -> {
                    filteredList.setPredicate(item -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        // Search dynamically using the getter
                        String itemString = stringMapper.apply(item);
                        return itemString != null && itemString.toLowerCase().contains(newValue.toLowerCase());
                    });

                    if (!filteredList.isEmpty() && !comboBox.isShowing()) {
                        comboBox.show();
                    }
                });
            });
        }
    }
//----------------------------------------------------------------------------------- universal cmd End ------------------------------------------------------------------------------
    public void loadCategories() {
        ObservableList<CategoryDTO> list = null;
        try {
            list = FXCollections.observableArrayList(catServiceType.getAllCategories());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // ONE LINE: Make it searchable by Category Name!
        ComboBoxSearchUtil.makeSearchable(cmbProdCategory, list, CategoryDTO::getCategoryName);
    }
    public void loadSuppliers() {
        ObservableList<Supplier> list = null;
        try {
            list = FXCollections.observableArrayList(supServiceType.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // ONE LINE: Make it searchable by Category Name!
        ComboBoxSearchUtil.makeSearchable(cmbProdSupplier, list, Supplier::getCompanyName);
    }
}
