package ba.unsa.etf.rpr.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Button cancelBtn;
    public TextField fldUsername;
    public PasswordField fldPassword;

    @FXML
    public void initialize() {

        fldUsername.getStyleClass().add("fieldIncorrect");
        fldPassword.getStyleClass().add("fieldIncorrect");

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("fieldIncorrect");
                fldUsername.getStyleClass().add("fieldCorrect");
            } else {
                fldUsername.getStyleClass().removeAll("fieldCorrect");
                fldUsername.getStyleClass().add("fieldIncorrect");
            }
        });
        fldPassword.textProperty().addListener((obs, oldIme, newPass) -> {
            if (!newPass.isEmpty()) {
                fldPassword.getStyleClass().removeAll("fieldIncorrect");
                fldPassword.getStyleClass().add("fieldCorrect");
            } else {
                fldPassword.getStyleClass().removeAll("fieldCorrect");
                fldPassword.getStyleClass().add("fieldIncorrect");
            }
        });

    }


    public void aboutAction(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.toFront();
        stage.show();

    }

    public void okAction(ActionEvent actionEvent) throws IOException {

        if(fldUsername.getText().equals("admin") && fldPassword.getText().equals("admin")) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Express mail");
            stage.toFront();
            stage.show();
            Stage login = (Stage) cancelBtn.getScene().getWindow();
            login.close();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Check the correctness of the access data.\nIf you have problems contact customer support");
            alert.setHeaderText("Incorrect access data!");
            alert.showAndWait();
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
