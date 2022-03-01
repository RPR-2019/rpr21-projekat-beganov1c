package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.model.Courier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class CourierController {
    public TextField nameField;
    public TextField telephoneNumberField;
    public TextField usernameField;
    public TextField passwordField;
    public Button okBtn;
    public Button cancelBtn;
    public ImageView imageView;

    private Courier courier;
    private ArrayList<String> usernames;
    private int set=0;
    private String username="";

    public CourierController(Courier courier, ArrayList<String> usernames) {
        this.courier = courier;
        this.usernames=usernames;
    }

    @FXML
    public void initialize() {
        if(courier !=null) {
            nameField.setText(courier.getName());
            telephoneNumberField.setText(courier.getTelephoneNumber());
            usernameField.setText(courier.getUsername().substring(1));
            passwordField.setText(courier.getPassword());
            username=courier.getUsername();
            if(courier.getImage()!=null)
                imageView.setImage(new Image(courier.getImage()));
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

        if(telephoneNumberField.getText().trim().isEmpty()) {
            telephoneNumberField.getStyleClass().removeAll("fieldCorrect");
            telephoneNumberField.getStyleClass().add("fieldIncorrect");
        }

        else {
            telephoneNumberField.getStyleClass().removeAll("fieldIncorrect");
            telephoneNumberField.getStyleClass().add("fieldCorrect");
        }

        if(usernameField.getText().trim().isEmpty()) {
            usernameField.getStyleClass().removeAll("fieldCorrect");
            usernameField.getStyleClass().add("fieldIncorrect");
        }

        else if(!Objects.equals(username, usernameField.getText())) {

            Optional<String> found=usernames.stream().filter(username -> username.equals("c"+usernameField.getText())).findFirst();
            if(found.isPresent()) {
                usernameField.getStyleClass().removeAll("fieldCorrect");
                usernameField.getStyleClass().add("fieldIncorrect");
                isti.set(true);
            }
            else {
                usernameField.getStyleClass().removeAll("fieldIncorrect");
                usernameField.getStyleClass().add("fieldCorrect");
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

        if(!nameField.getText().trim().isEmpty() && !telephoneNumberField.getText().trim().isEmpty() && !usernameField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty() && !isti.get() ) {

            if(courier ==null) {
                if (set == 1)
                    courier = new Courier(-1, nameField.getText(), telephoneNumberField.getText(), "c" + usernameField.getText(), passwordField.getText(), imageView.getImage().getUrl());
                else
                    courier = new Courier(-1, nameField.getText(), telephoneNumberField.getText(), "c" + usernameField.getText(), passwordField.getText(), null);
            }
            else {
                courier.setName(nameField.getText());
                courier.setTelephoneNumber(telephoneNumberField.getText());
                courier.setUsername("c"+usernameField.getText());
                courier.setPassword(passwordField.getText());
                if(set==1) courier.setImage(imageView.getImage().getUrl());
            }
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }


    }

    public void changeAction(ActionEvent actionEvent) {
        SearchController searchController = new SearchController();
        ResourceBundle bundle =  ResourceBundle.getBundle("Translation");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"), bundle);
        fxmlLoader.setController(searchController);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Scene scene = new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(bundle.getString("pictureChange"));

        stage.setOnHiding(windowEvent ->{
            if(!searchController.getSlika().equals("")) {
                imageView.setImage(new Image(searchController.getSlika()));
                set = 1;
            }

        });
        stage.show();
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
