package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.BrzaPostaDAO;
import ba.unsa.etf.rpr.model.Korisnik;
import ba.unsa.etf.rpr.model.Kurir;
import ba.unsa.etf.rpr.model.Posiljka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PosiljkaController {

    public TextField posiljkaIdField;
    public TextField opisField;
    public TextField posiljaocNazivField;
    public TextField posiljaocTelefonField;
    public TextField posiljaocAdresaField;
    public TextField primalacNazivField;
    public TextField primalacTelefonField;
    public TextField primalacAdresaField;
    public TextField tezinaField;
    public TextField cijenaField;
    public ChoiceBox<Kurir> kurirChoice;
    public Button okBtn;
    public Button cancelBtn;
    public RadioButton kurirRadioBtn;
    public TextField kurirNazivField;
    public TextField kurirTelefonField;
    private Posiljka posiljka;


    private BrzaPostaDAO model = BrzaPostaDAO.getInstance();


    public PosiljkaController(Posiljka posiljka) {
        this.posiljka = posiljka;
    }

    @FXML
    public void initialize() {
        if(posiljka!=null) {
            Kurir kurir = posiljka.getKurir();
            Korisnik posiljaoc = posiljka.getPosiljaoc();
            Korisnik primalac = posiljka.getPrimaoc();
            posiljkaIdField.setText(String.valueOf(posiljka.getId()));
            opisField.setText(posiljka.getOpis());
            posiljaocNazivField.setText(posiljaoc.getNaziv());
            posiljaocTelefonField.setText(posiljaoc.getBrojTelefona());
            posiljaocAdresaField.setText(posiljaoc.getAdresa());
            primalacNazivField.setText(primalac.getNaziv());
            primalacTelefonField.setText(primalac.getBrojTelefona());
            primalacAdresaField.setText(primalac.getAdresa());
            tezinaField.setText(String.valueOf(posiljka.getTezina()));
            cijenaField.setText(String.valueOf(posiljka.getCijenaDostave()));
            kurirNazivField.setText(kurir.getNaziv());
            kurirTelefonField.setText(kurir.getBrojTelefona());

        }

        kurirNazivField.setEditable(false);
        kurirTelefonField.setEditable(false);

        kurirRadioBtn.setOnAction(actionEvent -> {
            if(kurirRadioBtn.isSelected()) {
                kurirNazivField.setEditable(true);
                kurirTelefonField.setEditable(true);
            }
            else {
                kurirNazivField.setEditable(false);
                kurirTelefonField.setEditable(false);
            }
        });

        kurirChoice.setOnAction(actionEvent -> {
            kurirNazivField.setText(kurirChoice.getSelectionModel().getSelectedItem().getNaziv());
            kurirTelefonField.setText(kurirChoice.getSelectionModel().getSelectedItem().getBrojTelefona());
        });

        ObservableList<Kurir> kuriri = FXCollections.observableArrayList(model.kuriri());
        kurirChoice.setItems(kuriri);
        if(posiljka!=null)
            kurirChoice.getSelectionModel().select(posiljka.getKurir());
        else if(!kuriri.isEmpty())
            kurirChoice.getSelectionModel().select(kuriri.get(0));
    }

    public void okAction(ActionEvent actionEvent) {
        if(provjera()) {
            if(posiljka==null) {
                Kurir kurir = kurirChoice.getValue();
                kurir.setNaziv(kurirNazivField.getText());
                kurir.setBrojTelefona(kurirTelefonField.getText());
                posiljka = new Posiljka(-1, opisField.getText(), primalacAdresaField.getText(),
                        new Korisnik(-1, posiljaocNazivField.getText(), posiljaocTelefonField.getText(), posiljaocAdresaField.getText()),
                        new Korisnik(-1, primalacNazivField.getText(), primalacTelefonField.getText(), primalacAdresaField.getText()),
                        kurir, Integer.parseInt(tezinaField.getText()), Integer.parseInt(cijenaField.getText()));
            }
            else {

                Korisnik posiljaoc = posiljka.getPosiljaoc();
                Korisnik primaoc = posiljka.getPrimaoc();
                Kurir kurir = posiljka.getKurir();

                posiljka.setCijenaDostave(Integer.parseInt(cijenaField.getText()));
                posiljka.setTezina(Integer.parseInt(tezinaField.getText()));
                posiljka.setOpis(opisField.getText());

                posiljaoc.setNaziv(posiljaocNazivField.getText());
                posiljaoc.setAdresa(posiljaocAdresaField.getText());
                posiljaoc.setBrojTelefona(posiljaocTelefonField.getText());

                primaoc.setNaziv(primalacNazivField.getText());
                primaoc.setAdresa(primalacAdresaField.getText());
                primaoc.setBrojTelefona(primalacTelefonField.getText());

                kurir.setNaziv(kurirNazivField.getText());
                kurir.setBrojTelefona(kurirTelefonField.getText());

                posiljka.setKurir(kurir);
                posiljka.setPosiljaoc(posiljaoc);
                posiljka.setPrimaoc(primaoc);

            }
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }

    }


    public void cancelAction(ActionEvent actionEvent) {
        posiljka=null;
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private boolean provjera() {

        provjeriTextField(opisField);
        provjeriTextField(primalacAdresaField);
        provjeriTextField(primalacNazivField);
        provjeriTextField(primalacTelefonField);
        provjeriTextField(kurirNazivField);
        provjeriTextField(kurirTelefonField);
        provjeriTextField(posiljaocAdresaField);
        provjeriTextField(posiljaocNazivField);
        provjeriTextField(posiljaocTelefonField);
        provjeriTextFieldBroj(tezinaField);
        provjeriTextFieldBroj(cijenaField);


        return provjeriTextField(opisField) &&
                provjeriTextField(primalacAdresaField) &&
                provjeriTextField(primalacNazivField) &&
                provjeriTextField(primalacTelefonField) &&
                provjeriTextField(kurirNazivField) &&
                provjeriTextField(kurirTelefonField) &&
                provjeriTextField(posiljaocAdresaField) &&
                provjeriTextField(posiljaocNazivField) &&
                provjeriTextField(posiljaocTelefonField) &&
                provjeriTextFieldBroj(tezinaField) &&
                provjeriTextFieldBroj(cijenaField);
    }

    private boolean provjeriTextFieldBroj(TextField textField) {
        try {
            int broj = Integer.parseInt(textField.getText());
            if(broj<=0) {
                textField.getStyleClass().removeAll("poljeIspravno");
                textField.getStyleClass().add("poljeNijeIspravno");
                return false;
            }
            else {
                textField.getStyleClass().removeAll("poljeNijeIspravno");
                textField.getStyleClass().add("poljeIspravno");
                return true;
            }


        }
        catch (Exception e) {

            textField.getStyleClass().removeAll("poljeIspravno");
            textField.getStyleClass().add("poljeNijeIspravno");
            return false;

        }
    }

    private boolean provjeriTextField(TextField textField) {

        if(textField.getText().trim().isEmpty()) {
            textField.getStyleClass().removeAll("poljeIspravno");
            textField.getStyleClass().add("poljeNijeIspravno");
            return false;
        }
        else {
            textField.getStyleClass().removeAll("poljeNijeIspravno");
            textField.getStyleClass().add("poljeIspravno");
            return true;
        }
    }

    public Posiljka getPosiljka() {
        return posiljka;
    }
}
