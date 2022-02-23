package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.model.Courier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    public TextField nameField;
    public TextField telephoneNumberField;
    public Button okBtn;
    public Button cancelBtn;
    private Courier courier;

    public RegisterController(Courier courier) {
        this.courier = courier;
    }

    @FXML
    public void initialize() {
        if(courier !=null) {
            nameField.setText(courier.getName());
            telephoneNumberField.setText(courier.getTelephoneNumber());
        }
    }


    public void okAction(ActionEvent actionEvent) {


        if(nameField.getText().trim().isEmpty()) {
            nameField.getStyleClass().removeAll("fieldCorrect");
            nameField.getStyleClass().add("fieldIncorrect");
        }

        else {
            nameField.getStyleClass().removeAll("fieldIncorrect");
            nameField.getStyleClass().add("fieldCorrect");
        }

        if(telephoneNumberField.getText().trim().isEmpty()) {
            telephoneNumberField.getStyleClass().removeAll("fieldCorrect");
            telephoneNumberField.getStyleClass().add("fieldIncorrect");
        }

        else {
            telephoneNumberField.getStyleClass().removeAll("fieldIncorrect");
            telephoneNumberField.getStyleClass().add("fieldCorrect");
        }

        if(!nameField.getText().trim().isEmpty() && !telephoneNumberField.getText().trim().isEmpty()) {

            if(courier ==null) courier = new Courier(-1, nameField.getText(), telephoneNumberField.getText());
            else {
                courier.setName(nameField.getText());
                courier.setTelephoneNumber(telephoneNumberField.getText());
            }
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }


    }

    public void cancelAction(ActionEvent actionEvent) {

       courier =null;
       Stage stage = (Stage) cancelBtn.getScene().getWindow();
       stage.close();
    }

    public Courier getCourier() {
        return courier;
    }
}
