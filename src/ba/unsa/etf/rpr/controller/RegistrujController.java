package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.model.Kurir;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrujController {
    public TextField nazivField;
    public TextField brojTelefonaField;
    public Button okBtn;
    public Button cancelBtn;
    private Kurir kurir;

    public RegistrujController(Kurir kurir) {
        this.kurir = kurir;
    }

    @FXML
    public void initialize() {
        if(kurir!=null) {
            nazivField.setText(kurir.getNaziv());
            brojTelefonaField.setText(kurir.getBrojTelefona());
        }
    }



    public void okAction(ActionEvent actionEvent) {


        if(nazivField.getText().trim().isEmpty()) {
            nazivField.getStyleClass().removeAll("poljeIspravno");
            nazivField.getStyleClass().add("poljeNijeIspravno");
        }

        else {
            nazivField.getStyleClass().removeAll("poljeNijeIspravno");
            nazivField.getStyleClass().add("poljeIspravno");
        }

        if(brojTelefonaField.getText().trim().isEmpty()) {
            brojTelefonaField.getStyleClass().removeAll("poljeIspravno");
            brojTelefonaField.getStyleClass().add("poljeNijeIspravno");
        }

        else {
            brojTelefonaField.getStyleClass().removeAll("poljeNijeIspravno");
            brojTelefonaField.getStyleClass().add("poljeIspravno");
        }

        if(!nazivField.getText().trim().isEmpty() && !brojTelefonaField.getText().trim().isEmpty()) {

            if(kurir==null) kurir = new Kurir(-1,nazivField.getText(),brojTelefonaField.getText());
            else {
                kurir.setNaziv(nazivField.getText());
                kurir.setBrojTelefona(brojTelefonaField.getText());
            }
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }


    }

    public void cancelAction(ActionEvent actionEvent) {

       kurir=null;
       Stage stage = (Stage) cancelBtn.getScene().getWindow();
       stage.close();
    }

    public Kurir getKurir() {
        return kurir;
    }
}
