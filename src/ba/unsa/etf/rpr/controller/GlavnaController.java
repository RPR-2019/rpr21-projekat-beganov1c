package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.BrzaPostaDAO;
import ba.unsa.etf.rpr.model.Posiljka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;



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
        /*for (Posiljka p : model.posiljke())
            System.out.println(p.getId() +"-"+p.getOpis());
         */
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

    public void napraviAction(ActionEvent actionEvent) {
    }

    public void obrisiAction(ActionEvent actionEvent) {
    }

    public void azurirajAction(ActionEvent actionEvent) {
    }

    public void registrujAction(ActionEvent actionEvent) {
    }
}
