package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXButton;
import config.AppModule;
import dto.LoginResult;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public Label lblDateAndTime;
    @FXML
    public Label lblUserName;
    @FXML
    public Label lblUserRole;
    @FXML private JFXButton btnDashboard;
    @FXML private JFXButton btnEmployees;
    @FXML private JFXButton btnInventory;
    @FXML private JFXButton btnProducts;
    @FXML private JFXButton btnReports;
    @FXML private JFXButton btnSales;
    @FXML private JFXButton btnSettings;
    @FXML private JFXButton btnSuppliers;
    @FXML private JFXButton exit;
    @FXML private ImageView menu;
    @FXML private ImageView menuBack;
    @FXML private AnchorPane slider;

    @FXML
    private AnchorPane dashRoot;

    private Injector injector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injector = Guice.createInjector(new AppModule());

        loadDateAndTime();

        exit.setOnMouseClicked(e -> System.exit(0));

        final double iconOnlyWidth = 85;
        final double expandedWidth = 280;

        // Clip to prevent content overflow
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(slider.widthProperty());
        clip.heightProperty().bind(slider.heightProperty());
        slider.setClip(clip);

        // Store original button texts
        storeButtonTexts();

        // Start expanded
        slider.setMinWidth(expandedWidth);
        slider.setMaxWidth(expandedWidth);
        slider.setPrefWidth(expandedWidth);
        setButtonTextsVisible(true);
        menu.setVisible(false);
        menuBack.setVisible(true);

        // Expand sidebar
        menu.setOnMouseClicked(e -> {
            Timeline expand = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(slider.prefWidthProperty(), iconOnlyWidth, Interpolator.EASE_BOTH),
                            new KeyValue(slider.minWidthProperty(),  iconOnlyWidth, Interpolator.EASE_BOTH),
                            new KeyValue(slider.maxWidthProperty(),  iconOnlyWidth, Interpolator.EASE_BOTH)
                    ),
                    new KeyFrame(Duration.seconds(0.4),
                            new KeyValue(slider.prefWidthProperty(), expandedWidth, Interpolator.EASE_BOTH),
                            new KeyValue(slider.minWidthProperty(),  expandedWidth, Interpolator.EASE_BOTH),
                            new KeyValue(slider.maxWidthProperty(),  expandedWidth, Interpolator.EASE_BOTH)
                    )
            );
            expand.setOnFinished(ev -> {
                setButtonTextsVisible(true);
                menu.setVisible(false);
                menuBack.setVisible(true);
            });
            expand.play();
        });

        // Collapse sidebar
        menuBack.setOnMouseClicked(e -> {
            setButtonTextsVisible(false);
            Timeline collapse = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(slider.prefWidthProperty(), expandedWidth, Interpolator.EASE_BOTH),
                            new KeyValue(slider.minWidthProperty(),  expandedWidth, Interpolator.EASE_BOTH),
                            new KeyValue(slider.maxWidthProperty(),  expandedWidth, Interpolator.EASE_BOTH)
                    ),
                    new KeyFrame(Duration.seconds(0.4),
                            new KeyValue(slider.prefWidthProperty(), iconOnlyWidth, Interpolator.EASE_BOTH),
                            new KeyValue(slider.minWidthProperty(),  iconOnlyWidth, Interpolator.EASE_BOTH),
                            new KeyValue(slider.maxWidthProperty(),  iconOnlyWidth, Interpolator.EASE_BOTH)
                    )
            );
            collapse.setOnFinished(ev -> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });
            collapse.play();
        });

        // Menu item highlight on click
        List<JFXButton> menuItems = List.of(
                btnDashboard, btnEmployees, btnInventory, btnProducts,
                btnReports, btnSales, btnSuppliers, btnSettings
        );

        final String normalStyle   = "-fx-background-color: transparent; -fx-text-fill: white; ";
        final String selectedStyle = "-fx-background-color: #2D788A; -fx-text-fill: #DAF3F7; -fx-background-radius: 8; ";

        btnDashboard.setOnMouseClicked(e -> { menuItems.forEach(i -> i.setStyle(normalStyle)); btnDashboard.setStyle(selectedStyle); btnDashboardOnAction(e); });
        btnSales.setOnMouseClicked(e ->     { menuItems.forEach(i -> i.setStyle(normalStyle)); btnSales.setStyle(selectedStyle);     btnSalesOnAction(e); });
        btnProducts.setOnMouseClicked(e ->  { menuItems.forEach(i -> i.setStyle(normalStyle)); btnProducts.setStyle(selectedStyle);  btnProductsOnAction(e); });
        btnInventory.setOnMouseClicked(e -> { menuItems.forEach(i -> i.setStyle(normalStyle)); btnInventory.setStyle(selectedStyle); btnInventoryOnAction(e); });
        btnSuppliers.setOnMouseClicked(e -> { menuItems.forEach(i -> i.setStyle(normalStyle)); btnSuppliers.setStyle(selectedStyle); btnSuppliersOnAction(e); });
        btnEmployees.setOnMouseClicked(e -> { menuItems.forEach(i -> i.setStyle(normalStyle)); btnEmployees.setStyle(selectedStyle); btnEmployeesOnAction(e); });
        btnReports.setOnMouseClicked(e ->   { menuItems.forEach(i -> i.setStyle(normalStyle)); btnReports.setStyle(selectedStyle);   btnReportsOnAction(e); });
        btnSettings.setOnMouseClicked(e ->  { menuItems.forEach(i -> i.setStyle(normalStyle)); btnSettings.setStyle(selectedStyle);  btnSettingsOnAction(e); });

        // Shift dashRoot left anchor live as sidebar animates
        slider.widthProperty().addListener((obs, oldVal, newVal) ->
                AnchorPane.setLeftAnchor(dashRoot, newVal.doubleValue()));

        loadIntoRoot("/view/dashboard_form.fxml");
    }

    private final java.util.Map<JFXButton, String> buttonTextMap = new java.util.HashMap<>();

    private void storeButtonTexts() {
        List<JFXButton> allButtons = List.of(
                btnDashboard, btnEmployees, btnInventory, btnProducts,
                btnReports, btnSales, btnSuppliers, btnSettings, exit
        );
        for (JFXButton btn : allButtons) {
            buttonTextMap.put(btn, btn.getText());
        }
    }

    private void setButtonTextsVisible(boolean visible) {
        for (java.util.Map.Entry<JFXButton, String> entry : buttonTextMap.entrySet()) {
            entry.getKey().setText(visible ? entry.getValue() : "");
        }
    }

    public void btnDashboardOnAction(MouseEvent mouseEvent) {
        loadIntoRoot("/view/dashboard_form.fxml");
    }
    private void loadIntoRoot(String fxmlPath) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.out.println("FXML not found: " + fxmlPath);
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(injector::getInstance);
            Parent parent = fxmlLoader.load();
            AnchorPane.setTopAnchor(parent, 0.0);
            AnchorPane.setBottomAnchor(parent, 0.0);
            AnchorPane.setLeftAnchor(parent, 0.0);
            AnchorPane.setRightAnchor(parent, 0.0);
            dashRoot.getChildren().setAll(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd, yyyy");


        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblDateAndTime.setText(sdf.format(date)+"  "+now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void setLoginResult(LoginResult result) {
        String userName = result.getUser().getFirstName() + " " + result.getUser().getLastName();
        int userRole = result.getUser().getUserRole();
        lblUserName.setText(userName);
       switch (userRole) {
           case 1 : lblUserRole.setText("Admin"); break;
           case 2 : lblUserRole.setText("Cashier"); break;
           default: lblUserRole.setText("Guest");
       }

    }

    public void btnSalesOnAction(MouseEvent mouseEvent) {
        loadIntoRoot("/view/supplier_form.fxml");
    }

    public void btnProductsOnAction(MouseEvent mouseEvent) {
        loadIntoRoot("/view/supplier_form.fxml");
    }

    public void btnInventoryOnAction(MouseEvent mouseEvent) {
        loadIntoRoot("/view/supplier_form.fxml");
    }

    public void btnSuppliersOnAction(MouseEvent mouseEvent) {
        loadIntoRoot("/view/supplier_form.fxml");
    }

    public void btnEmployeesOnAction(MouseEvent mouseEvent) {
        loadIntoRoot("/view/employee_form.fxml");
    }

    public void btnReportsOnAction(MouseEvent mouseEvent) {
        loadIntoRoot("/view/supplier_form.fxml");

    }

    public void btnSettingsOnAction(MouseEvent mouseEvent) {
        loadIntoRoot("/view/supplier_form.fxml");
    }


}


