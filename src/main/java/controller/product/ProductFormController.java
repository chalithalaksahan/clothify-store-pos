package controller.product;

import Entity.Category;
import Entity.Supplier;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dto.CategoryDTO;
import dto.ProductDTO;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.custom.CategoryService;
import service.custom.ProductService;
import service.custom.SupplierService;
import util.CategoryType;
import util.ComboBoxSearchUtil;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {
    @FXML
    public Label lblMessage;

    @FXML
    public TableColumn<?, ?> colAddMinQty;

    @FXML
    public TableColumn<?, ?> colAddReOrderLvl;

    @FXML
    public TableColumn<?, ?> colAddColor;

    @FXML
    public TableColumn<?, ?> colAddSize;

    @FXML
    public TableColumn<?, ?> colAddDescription;

    @FXML
    private JFXComboBox<String> cmbParentCat;

    @FXML
    private JFXComboBox<CategoryDTO> cmbProdCategory;

    @FXML
    private JFXComboBox<Supplier> cmbProdSupplier;

    @FXML
    private TableColumn<ProductDTO, String> colAddCategory;

    @FXML
    private TableColumn<?, ?> colAddCost;

    @FXML
    private TableColumn<?, ?> colAddName;

    @FXML
    private TableColumn<?, ?> colAddPrice;

    @FXML
    private TableColumn<?, ?> colAddSku;

    @FXML
    private TableColumn<ProductDTO, String> colAddSupplier;

    @FXML
    private JFXTextField txtSearchProduct;

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
    private TableView<ProductDTO> tblAllProducts;

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
    private TableView<ProductDTO> tblAddProducts;

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
        CategoryDTO category = extracrDtoFromCategoryFields();

        if (category == null) {
            return;
        }

        if (catServiceType.creatCategory(category)) {
            loadCategoryTable();
            clearCategoryFields();
            loadCategories();
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
            loadCategories();
            showMessage("Category deleted successfully", true);
        } else {
            showMessage("Failed to delete category", false);
        }

    }

    public void btnUpdateCategoryOnAction(ActionEvent actionEvent) {
        CategoryDTO category = extracrDtoFromCategoryFields();
        if (category == null) {
            return;
        }

        try {
            if(catServiceType.updateCategory(category)) {
                loadCategoryTable();
                clearCategoryFields();
                loadCategories();
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

    private void loadProductTable() {
        try {
            List<ProductDTO> products = proServiceType.getAllProducts();
            tblAddProducts.setItems(FXCollections.observableArrayList(products));
        }catch (Exception e){
            showMessage("Failed to load products: " + e.getMessage(), false);
        }
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

    private void clearProductFields() {
        txtSkuCode.setText("");
        txtProdName.setText("");
        cmbProdSupplier.setValue(null);
        cmbProdCategory.setValue(null);
        txtCostPrice.setText("");
        txtSellingPrice.setText("");
        txtMinQty.setText("");
        txtReorderLevel.setText("");
        txtProdDesc.setText("");
        txtColor.setText("");
        txtSize.setText("");
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
        txtSkuCode.setText(product.getSkuCode());
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
        ProductDTO product = extractDtoFromProductFields();
        if (product == null) {
            return;
        }

        try {
            if(proServiceType.createProduct(product)){
                showMessage("Product created successfully", true);
                loadProductTable();
                clearProductFields();
            }else{
                showMessage("Failed to create product", false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateProductOnAction(ActionEvent actionEvent) {
        ProductDTO product = extractDtoFromProductFields();
        if (product == null) {
            return;
        }
        if (proServiceType.updateProduct(product)){
            loadProductTable();
            clearProductFields();
            showMessage("Product updated successfully", true);
        }else{
            showMessage("Failed to update product", false);
        }
    }

    public void btnDeleteProductOnAction(ActionEvent actionEvent) {
        String skuCode = txtSkuCode.getText();

        if (proServiceType.deleteProduct(skuCode)){
            loadProductTable();
            clearProductFields();
            showMessage("Product deleted successfully", true);
        }else {
            showMessage("Failed to delete product", false);
        }
    }

    public void loadCategories() {
        ObservableList<CategoryDTO> list = null;
        try {
            list = FXCollections.observableArrayList(catServiceType.getAllCategories());
        } catch (SQLException e) {
            showMessage("Failed to load categories: " + e.getMessage(), false);
        }


        ComboBoxSearchUtil.makeSearchable(cmbProdCategory, list, CategoryDTO::getCategoryName);
    }
    public void loadSuppliers() {
        ObservableList<Supplier> list = null;
        try {
            list = FXCollections.observableArrayList(supServiceType.getAll());
        } catch (SQLException e) {
            showMessage("Failed to load suppliers: " + e.getMessage(), false);
        }


        ComboBoxSearchUtil.makeSearchable(cmbProdSupplier, list, Supplier::getCompanyName);
    }
    private CategoryDTO extracrDtoFromCategoryFields(){
        try{
            CategoryDTO category = new CategoryDTO();

            category.setCategoryCode(ValidationUtil.requireText(txtCatCode.getText(), "Category Code"));
            category.setCategoryName(ValidationUtil.requireText(txtCatName.getText(), "Category Name"));
            category.setDescription(ValidationUtil.requireText(txtCatDesc.getText(), "Category Description"));
            category.setParentCategory(ValidationUtil.requireSelection(cmbParentCat.getValue(), "parent category"));
            boolean isActive = rbtnActive.isSelected();
            category.setStatus(isActive ? "Active" : "Inactive");

            return category;

        }catch (ValidationUtil.ValidationException e) {
            // Notice we catch the exception from the util class!
            showMessage(e.getMessage(), false);
            return null;
        }

    }

    private ProductDTO extractDtoFromProductFields(){

        try {
            ProductDTO product = new ProductDTO();

            // Use ValidationUtil.methodName()
            product.setSkuCode(ValidationUtil.requireText(txtSkuCode.getText(), "SKU Code"));
            product.setName(ValidationUtil.requireText(txtProdName.getText(), "Product Name"));
            product.setDescription(ValidationUtil.requireText(txtProdDesc.getText(), "Product Description"));
            product.setColor(ValidationUtil.requireText(txtColor.getText(), "Color"));
            product.setSize(ValidationUtil.requireText(txtSize.getText(), "Size"));

            product.setCategory(ValidationUtil.requireSelection(cmbProdCategory.getValue(), "category"));
            product.setSupplier(ValidationUtil.requireSelection(cmbProdSupplier.getValue(), "supplier"));

            product.setCostPrice(ValidationUtil.requireNumber(txtCostPrice.getText(), "Cost Price"));
            product.setSellingPrice(ValidationUtil.requireNumber(txtSellingPrice.getText(), "Selling Price"));
            product.setMinQty(ValidationUtil.requireNumber(txtMinQty.getText(), "Minimum Quantity"));
            product.setReOrderLvl(ValidationUtil.requireNumber(txtReorderLevel.getText(), "Reorder Level"));

            return product;

        } catch (ValidationUtil.ValidationException e) {
            // Notice we catch the exception from the util class!
            showMessage(e.getMessage(), false);
            return null;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategories();
        loadSuppliers();
        loadProductTable();
        loadCategoryTable();

        colCatListCode.setCellValueFactory(new PropertyValueFactory<>("categoryCode"));
        colCatListName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colCatListParent.setCellValueFactory(new PropertyValueFactory<>("parentCategory"));
        colCatListStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCatListDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        colAddSku.setCellValueFactory(new PropertyValueFactory<>("skuCode"));
        colAddName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddSupplier.setCellValueFactory(cellData -> {
            // We grab the whole Supplier object
            ProductDTO rowData = cellData.getValue();

            if (rowData.getSupplier() != null) {
                return new SimpleStringProperty(rowData.getSupplier().getCompanyName());
            }
            return new SimpleStringProperty("N/A");
        });
        colAddCategory.setCellValueFactory(cellData -> {

            ProductDTO rowData = cellData.getValue();

            if (rowData.getCategory() != null) {
                return new SimpleStringProperty(rowData.getCategory().getCategoryName());
            }
            return new SimpleStringProperty("N/A");
        });
        colAddCost.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        colAddPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colAddMinQty.setCellValueFactory(new PropertyValueFactory<>("minQty"));
        colAddReOrderLvl.setCellValueFactory(new PropertyValueFactory<>("reOrderLvl"));
        colAddDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAddColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colAddSize.setCellValueFactory(new PropertyValueFactory<>("size"));




        cmbParentCat.setItems(
                FXCollections.observableArrayList(Arrays.stream(CategoryType.values()).map(Enum::name).toList())
        );

        tblCategoryList.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if (t1 != null) {
                setTextToValuesForCat(t1);
            }
        });
        tblAddProducts.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if (t1 != null) {
                setTextToValuesForProduct(t1);
            }
        });
    }
}
