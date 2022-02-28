package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.model.Courier;
import ba.unsa.etf.rpr.model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ManagerController {

    public TextField nameField;
    public TextField usernameField;
    public TextField passwordField;
    public Button okBtn;
    public Button cancelBtn;
    private Manager manager;
    private ArrayList<String> usernames;

    public ManagerController(Manager manager, ArrayList<String> usernames) {
        this.manager = manager;
        this.usernames=usernames;
    }

    @FXML
    public void initialize() {
        if(manager !=null) {
            nameField.setText(manager.getName());
            usernameField.setText(manager.getUsername());
            passwordField.setText(manager.getPassword());
        }
    }


    public void okAction(ActionEvent actionEvent) {


        AtomicBoolean isti = new AtomicBoolean(false);
        if(nameField.getText().trim().isEmpty()) {
            nameField.getStyleClass().removeAll("fieldCorrect");
            nameField.getStyleClass().add("fieldIncorrect");
        }

        else {
            nameField.getStyleClass().removeAll("fieldIncorrect");
            nameField.getStyleClass().add("fieldCorrect");
        }


        if(usernameField.getText().trim().isEmpty()) {
            usernameField.getStyleClass().removeAll("fieldCorrect");
            usernameField.getStyleClass().add("fieldIncorrect");
        }

        else {

            Optional<String> nadjen=usernames.stream().filter(username -> username.equals(usernameField.getText())).findFirst();
            if(nadjen.isPresent()) {
                usernameField.getStyleClass().removeAll("fieldCorrect");
                usernameField.getStyleClass().add("fieldIncorrect");
            }
            else {
                usernameField.getStyleClass().removeAll("fieldIncorrect");
                usernameField.getStyleClass().add("fieldCorrect");
                isti.set(false);
            }


        }

        if(passwordField.getText().trim().isEmpty()) {
            passwordField.getStyleClass().removeAll("fieldCorrect");
            passwordField.getStyleClass().add("fieldIncorrect");
        }

        else {
            passwordField.getStyleClass().removeAll("fieldIncorrect");
            passwordField.getStyleClass().add("fieldCorrect");
        }

        if(!nameField.getText().trim().isEmpty() && !usernameField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty() && isti.get() ) {

            if(manager ==null) manager = new Manager(-1, nameField.getText(), "m"+usernameField.getText(), passwordField.getText());
            else {
                manager.setName(nameField.getText());
                manager.setUsername("m"+usernameField.getText());
                manager.setPassword(passwordField.getText());
            }
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }


    }

    public void cancelAction(ActionEvent actionEvent) {

        manager =null;
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public Manager getManager() {
        return manager;
    }
}
