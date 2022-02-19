package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.BrzaPostaDAO;
import ba.unsa.etf.rpr.model.Kurir;
import ba.unsa.etf.rpr.model.Posiljka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class GlavnaController {


    public TableView<Posiljka> tableViewPosiljka;
    public TableColumn colPosikljaId;
    public TableColumn colPosiljkaOpis;
    public TableColumn colPosiljkaAdresa;
    public TableColumn colKurir;
    public TableColumn colTezina;
    public Button odjavaBtn;
    private BrzaPostaDAO model = BrzaPostaDAO.getInstance();

    @FXML
    public void initialize() {

        ObservableList<Posiljka> posiljke = FXCollections.observableArrayList();
        posiljke.addAll(model.posiljke());
        colPosikljaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPosiljkaOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        colPosiljkaAdresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        colKurir.setCellValueFactory(new PropertyValueFactory<>("kurir"));
        colTezina.setCellValueFactory(new PropertyValueFactory<>("tezina"));
        tableViewPosiljka.setItems(posiljke);


        tableViewPosiljka.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!tableViewPosiljka.getItems().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
                        && tableViewPosiljka.getSelectionModel().getSelectedItem()!=null) {
                    Stage novi = new Stage();
                    DetaljnoController detaljnoController = new DetaljnoController(tableViewPosiljka.getSelectionModel().getSelectedItem());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detaljno.fxml"));
                    loader.setController(detaljnoController);
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    novi.setScene(new Scene(root));
                    novi.setResizable(false);
                    novi.toFront();
                    novi.show();
                }
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

    public void napraviAction(ActionEvent actionEvent) throws IOException {

        Stage novi = new Stage();
        PosiljkaController posiljkaController = new PosiljkaController(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/posiljka.fxml"));
        loader.setController(posiljkaController);
        Parent root = loader.load();
        novi.setScene(new Scene(root));
        novi.setResizable(false);
        novi.toFront();
        novi.show();

        novi.setOnHiding(e->{
            Posiljka posiljka = posiljkaController.getPosiljka();
            if(posiljka!=null) {
                model.kreirajPosiljku(posiljka);
                ObservableList<Posiljka> posiljke = FXCollections.observableArrayList();
                posiljke.addAll(model.posiljke());
                tableViewPosiljka.setItems(posiljke);
                tableViewPosiljka.refresh();
            }
        });
    }

    public void obrisiAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Upozorenje");
        alert.setHeaderText("Brisanje pošiljke");
        alert.setContentText("Da li ste sigurni da zelite obrisati?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
            if(tableViewPosiljka.getSelectionModel().getSelectedItem()!=null) {
                model.obrisiPosiljku(tableViewPosiljka.getSelectionModel().getSelectedItem().getId());
                tableViewPosiljka.getItems().remove(tableViewPosiljka.getSelectionModel().getSelectedItem());
                tableViewPosiljka.refresh();
            }

    }

    public void azurirajAction(ActionEvent actionEvent) throws IOException {

        Stage novi = new Stage();
        PosiljkaController posiljkaController = new PosiljkaController(tableViewPosiljka.getSelectionModel().getSelectedItem());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/posiljka.fxml"));
        loader.setController(posiljkaController);
        Parent root = loader.load();
        novi.setScene(new Scene(root));
        novi.setResizable(false);
        novi.toFront();
        novi.show();

        novi.setOnHiding(e->{
            Posiljka posiljka = posiljkaController.getPosiljka();
            if(posiljka!=null) {
                model.azurirajPosiljku(posiljka);
                tableViewPosiljka.refresh();
            }
        });



    }

    public void registrujAction(ActionEvent actionEvent) throws IOException {

        Stage novi = new Stage();
        RegistrujController registrujController = new RegistrujController(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registruj.fxml"));
        loader.setController(registrujController);
        Parent root = loader.load();
        novi.setScene(new Scene(root));
        novi.setResizable(false);
        novi.toFront();
        novi.show();

        novi.setOnHiding(e->{
            Kurir kurir = registrujController.getKurir();
            if(kurir!=null) {
                model.dodajKurira(kurir);
            }
        });

    }

    public void azurirajKuriraAction(ActionEvent actionEvent) throws IOException {

        try {
            ChoiceDialog<Kurir> dialog = new ChoiceDialog<>(model.kuriri().get(0),model.kuriri());
            dialog.setTitle("Azuriraj kurira");
            dialog.setHeaderText("Azuriranje kurira");
            dialog.setContentText("Odaberite kurira kojeg zelite azurirati:");

            Optional<Kurir> result = dialog.showAndWait();
            if (result.isPresent()){

                Stage novi = new Stage();
                RegistrujController registrujController = new RegistrujController(result.get());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registruj.fxml"));
                loader.setController(registrujController);
                Parent root = loader.load();
                novi.setScene(new Scene(root));
                novi.setResizable(false);
                novi.toFront();
                novi.show();

                novi.setOnHiding(e->{
                    Kurir kurir = registrujController.getKurir();
                    if(kurir!=null) {
                        model.azurirajKurira(kurir);
                        ObservableList<Posiljka> posiljke = FXCollections.observableArrayList();
                        posiljke.addAll(model.posiljke());
                        tableViewPosiljka.setItems(posiljke);
                        tableViewPosiljka.refresh();
                    }
                });

            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public void obrisiKuriraAction(ActionEvent actionEvent) {

        try {
            ChoiceDialog<Kurir> dialog = new ChoiceDialog<>(model.kuriri().get(0),model.kuriri());
            dialog.setTitle("Obrisi kurira");
            dialog.setHeaderText("Brisanje kurira");
            dialog.setContentText("Odaberite kurira kojeg zelite obrisati:");

            Optional<Kurir> result = dialog.showAndWait();
            if (result.isPresent()){
                model.obrisiKurira(result.get());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
