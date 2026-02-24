import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/login.fxml"));

        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("Clothify Store Pos");
        stage.setMaximized(true);
        stage.show();
    }
}
