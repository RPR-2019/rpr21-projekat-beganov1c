package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.DAO.ExpressMailDAO;
import ba.unsa.etf.rpr.model.Courier;
import ba.unsa.etf.rpr.model.Manager;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    public Button cancelBtn;
    public TextField fldUsername;
    public PasswordField fldPassword;

    private ExpressMailDAO model = ExpressMailDAO.getInstance();

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

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"),bundle);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle(bundle.getString("aboutT"));
        stage.toFront();
        stage.show();

    }

    public void okAction(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Courier courier=null;
        Manager manager=null;
        courier=model.getCourier(fldUsername.getText(),fldPassword.getText());
        manager=model.getManager(fldUsername.getText(),fldPassword.getText());

        if(fldUsername.getText().equals("admin") && fldPassword.getText().equals("admin")) {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"),bundle);
            stage.setScene(new Scene(root));
            stage.setTitle(bundle.getString("expressMail"));
            stage.toFront();
            stage.show();
            stage.setMinHeight(550);
            stage.setMinWidth(500);
            Stage login = (Stage) cancelBtn.getScene().getWindow();
            login.close();
        }

        else if(courier!=null) {

            Stage stage = new Stage();
            CourierMainController courierMainController = new CourierMainController(model.getPackagesForCourier(courier));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courierMain.fxml"),bundle);
            loader.setController(courierMainController);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle(bundle.getString("expressMail"));
            stage.toFront();
            stage.show();
            stage.setMinHeight(500);
            stage.setMinWidth(500);
            Stage login = (Stage) cancelBtn.getScene().getWindow();
            login.close();

        }

        else if(manager!=null) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/managerMain.fxml"),bundle);
            stage.setScene(new Scene(root));
            stage.setTitle(bundle.getString("expressMail"));
            stage.toFront();
            stage.show();
            stage.setMinHeight(550);
            stage.setMinWidth(500);
            Stage login = (Stage) cancelBtn.getScene().getWindow();
            login.close();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setContentText(bundle.getString("loginContent"));
            alert.setHeaderText(bundle.getString("incorrectData"));
            alert.showAndWait();
        }
    }

    public void englishAction(ActionEvent actionEvent) {

        Locale.setDefault(new Locale("en", "US"));
        Stage primaryStage=(Stage) fldUsername.getScene().getWindow();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"),bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Express mail");
        primaryStage.toFront();
        primaryStage.show();

    }

    public void bosnianAction(ActionEvent actionEvent) {

        Locale.setDefault(new Locale("bs", "BA"));
        Stage primaryStage=(Stage) fldUsername.getScene().getWindow();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"),bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Express mail");
        primaryStage.toFront();
        primaryStage.show();

    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
