package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.model.Korisnik;
import ba.unsa.etf.rpr.model.Kurir;
import ba.unsa.etf.rpr.model.Posiljka;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DetaljnoController {
    public TextField posiljkaIdField;
    public TextField opisField;
    public TextField posiljaocNazivField;
    public TextField posiljaocTelefonField;
    public TextField posiljaocAdresaField;
    public TextField primalacNazivField;
    public TextField primalacTelefonField;
    public TextField primalacAdresaField;
    public TextField kurirNazivField;
    public TextField kurirTelefonField;
    public TextField tezinaField;
    public TextField cijenaField;
    public TextField kurirIdField;
    private Posiljka posiljka;


    public DetaljnoController(Posiljka posiljka) {
        this.posiljka = posiljka;
    }

    @FXML
    public void initialize() {
        Kurir kurir = posiljka.getKurir();
        Korisnik posiljaoc = posiljka.getPosiljaoc();
        Korisnik primaoc = posiljka.getPrimaoc();
        posiljkaIdField.setText(String.valueOf(posiljka.getId()));
        opisField.setText(posiljka.getOpis());
        posiljaocNazivField.setText(posiljaoc.getNaziv());
        posiljaocTelefonField.setText(posiljaoc.getBrojTelefona());
        posiljaocAdresaField.setText(posiljaoc.getAdresa());
        primalacNazivField.setText(primaoc.getNaziv());
        primalacTelefonField.setText(primaoc.getBrojTelefona());
        primalacAdresaField.setText(primaoc.getAdresa());
        kurirIdField.setText(String.valueOf(kurir.getId()));
        kurirNazivField.setText(kurir.getNaziv());
        kurirTelefonField.setText(kurir.getBrojTelefona());
        tezinaField.setText(String.valueOf(posiljka.getTezina()));
        cijenaField.setText(String.valueOf(posiljka.getCijenaDostave()));
    }
}
