package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private JFXButton btnDashboard;
    @FXML private JFXButton btnEmployees;
    @FXML private JFXButton btnInventory;
    @FXML private JFXButton btnProducts;
    @FXML private JFXButton btnReports;
    @FXML private JFXButton btnSales;
    @FXML private JFXButton btnSettings;
    @FXML private JFXButton btnSuppliers;
    @FXML private JFXButton exit;
    @FXML private Label menu;
    @FXML private Label menuBack;
    @FXML private AnchorPane slider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exit.setOnMouseClicked(e -> System.exit(0));

        final double iconOnlyWidth = 70;
        final double expandedWidth = 280;

        // Clip to prevent content overflow
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(slider.widthProperty());
        clip.heightProperty().bind(slider.heightProperty());
        slider.setClip(clip);

        // Start collapsed (icons only)
        slider.setMinWidth(iconOnlyWidth);
        slider.setMaxWidth(iconOnlyWidth);
        slider.setPrefWidth(iconOnlyWidth);
        setLabelsVisible(false);
        menuBack.setVisible(false);

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
                setLabelsVisible(true);
                menu.setVisible(false);
                menuBack.setVisible(true);
            });
            expand.play();
        });

        // Collapse sidebar
        menuBack.setOnMouseClicked(e -> {
            setLabelsVisible(false);
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

        final String normalStyle   = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Poppins SemiBold'; -fx-font-size: 24px; -fx-font-weight: 600;";
        final String selectedStyle = "-fx-background-color: #2D788A; -fx-text-fill: #DAF3F7; -fx-background-radius: 8; -fx-font-family: 'Poppins SemiBold'; -fx-font-size: 24px;-fx-font-weight: 600;";

        for (JFXButton item : menuItems) {
            item.setOnMouseClicked(e -> {
                menuItems.forEach(i -> i.setStyle(normalStyle));
                item.setStyle(selectedStyle);
            });
        }
    }

    private void setLabelsVisible(boolean visible) {
        setLabelsVisibleInPane(slider, visible);
    }

    private void setLabelsVisibleInPane(Pane parent, boolean visible) {
        for (javafx.scene.Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof Label label) {
                label.setVisible(visible);
                label.setManaged(visible);
            } else if (node instanceof Pane pane) {
                setLabelsVisibleInPane(pane, visible);
            }
        }
    }
}


