import com.google.inject.Guice;
import com.google.inject.Injector;
import config.AppModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.UserCredential;
import util.PasswordUtil;

public class Starter extends Application {
    public static void main(String[] args) {
        UserCredential credential = new UserCredential();
        credential.setEmail("admin");
        credential.setPassword(PasswordUtil.encrypt("12345678")); // encrypt here!
        launch();

    }
    @Override
    public void start(Stage stage) throws Exception {

        Injector injector = Guice.createInjector(new AppModule());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/login.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);

        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("Clothify Store Pos");
        stage.setMaximized(true);
        stage.show();
    }
}
