package ba.unsa.etf.rpr.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public Button cancelBtn;
    public TextField fldUsername;
    public PasswordField fldPassword;

    @FXML
    public void initialize() {

        fldUsername.getStyleClass().add("poljeNijeIspravno");
        fldPassword.getStyleClass().add("poljeNijeIspravno");

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldPassword.textProperty().addListener((obs, oldIme, newPass) -> {
            if (!newPass.isEmpty()) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });

    }


    public void aboutAction(ActionEvent actionEvent) throws IOException {

        Stage novi = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        novi.setScene(new Scene(root));
        novi.setResizable(false);
        novi.toFront();
        novi.show();

    }

    public void okAction(ActionEvent actionEvent) throws IOException {

        if(fldUsername.getText().equals("admin") && fldPassword.getText().equals("admin")) {
            Stage novi = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavna.fxml"));
            novi.setScene(new Scene(root));
            novi.setTitle("Brza posta");
            novi.toFront();
            novi.show();
            Stage login = (Stage) cancelBtn.getScene().getWindow();
            login.close();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greska");
            alert.setContentText("Provjerite ispravnost pristupnih podataka.\nUkoliko imate problema kontaktirajte korisnicku podrsku");
            alert.setHeaderText("Pogresni pristupni podaci!");
            alert.showAndWait();
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
