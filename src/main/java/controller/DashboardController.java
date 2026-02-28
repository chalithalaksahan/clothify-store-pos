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
import java.util.ResourceBundle;


public class DashboardController implements Initializable {

    @FXML
    private JFXButton exit;

    @FXML
    private Label menu;

    @FXML
    private Label menuBack;

    @FXML
    private AnchorPane slider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event->{
            System.exit(0);
        });

        double iconOnlyWidth = 70;   // shows icons only
        double expandedWidth = 280;  // shows icons + labels

        // Clip to prevent overflow dots
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(slider.widthProperty());
        clip.heightProperty().bind(slider.heightProperty());
        slider.setClip(clip);

        // Start in icon-only mode
        slider.setMinWidth(iconOnlyWidth);
        slider.setMaxWidth(iconOnlyWidth);
        slider.setPrefWidth(iconOnlyWidth);
        setLabelsVisible(false);  // hide labels, icons still show
        menuBack.setVisible(false);

        menu.setOnMouseClicked(mouseEvent -> {
            // Expand → show icons + labels
            Timeline widthExpand = new Timeline(
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
            widthExpand.play();

            // Show labels only after fully expanded
            widthExpand.setOnFinished(e -> {
                setLabelsVisible(true);
                menu.setVisible(false);
                menuBack.setVisible(true);
            });
        });

        menuBack.setOnMouseClicked(mouseEvent -> {
            // Hide labels immediately before collapsing
            setLabelsVisible(false);

            Timeline widthCollapse = new Timeline(
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
            widthCollapse.play();

            widthCollapse.setOnFinished(e -> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });
        });
    }

    // ── Recursively show/hide only Labels, not icons ──────────────────────────
    private void setLabelsVisible(boolean visible) {
        setLabelsVisibleInPane(slider, visible);
    }

    private void setLabelsVisibleInPane(Pane parent, boolean visible) {
        for (javafx.scene.Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof Label label) {
                label.setVisible(visible);
                label.setManaged(visible);
            } else if (node instanceof Pane pane) {
                // recurse into nested panes but skip icons (ImageView, SVGPath etc)
                setLabelsVisibleInPane(pane, visible);
            }
            // ImageView, SVGPath, FontIcon etc are untouched → always visible
        }
    }
}
