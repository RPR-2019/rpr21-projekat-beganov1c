package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;



public class GlavnaController {


    public Button odjavaBtn;

    public void aboutAction(ActionEvent actionEvent) throws IOException {

        Stage novi = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        novi.setScene(new Scene(root));
        novi.setResizable(false);
        novi.toFront();
        novi.show();

    }

    public void odjavaAction(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) odjavaBtn.getScene().getWindow();
        stage.close();

        Stage novi = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        novi.setScene(new Scene(root));
        novi.setTitle("Login");
        novi.setResizable(false);
        novi.toFront();
        novi.show();


    }

    public void napraviAction(ActionEvent actionEvent) {
    }

    public void obrisiAction(ActionEvent actionEvent) {
    }

    public void azurirajAction(ActionEvent actionEvent) {
    }

    public void registrujAction(ActionEvent actionEvent) {
    }
}
