package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.controller.AboutController;
import ba.unsa.etf.rpr.controller.ManagerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    public static void main(String[] args) {
	    launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Locale.setDefault(new Locale("en", "US"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"),bundle);
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.toFront();
        stage.show();
        AboutController.setGetHostController(getHostServices());


    }
}
