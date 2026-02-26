package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private JFXDrawer drawPane;

    @FXML
    private JFXHamburger hmbIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("/view/side_panel.fxml"));
            drawPane.setSidePane(vbox);
            drawPane.setDefaultDrawerSize(vbox.getPrefWidth());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hmbIcon);
        transition.setRate(-1);

        hmbIcon.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (drawPane.isClosed() || drawPane.isClosing()) {
                // opening → animate hamburger → arrow
                transition.setRate(1);
                transition.play();
                drawPane.setDefaultDrawerSize(0);
                drawPane.open();
            } else {
                // closing → animate arrow → hamburger
                transition.setRate(-1);
                transition.play();
                drawPane.close();
                drawPane.setOnDrawerClosed(event -> {
                    drawPane.setDefaultDrawerSize(250);
                    drawPane.setOnDrawerClosed(null);
                });
            }
        });
    }
}
