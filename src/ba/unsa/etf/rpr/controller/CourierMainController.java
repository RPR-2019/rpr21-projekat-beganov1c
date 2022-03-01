package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.DAO.ExpressMailDAO;
import ba.unsa.etf.rpr.model.Package;
import ba.unsa.etf.rpr.report.PrintReportCourier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;


public class CourierMainController {


    public TableView<Package> tableViewPackage;
    public TableColumn colPackageId;
    public TableColumn colPackageDescription;
    public TableColumn colPackageAddress;
    public TableColumn colDeliveryPrice;
    public TableColumn colWeight;
    public Button logoutBtn;
    private TreeSet<Package> packagesTree;

    private ExpressMailDAO model = ExpressMailDAO.getInstance();

    public CourierMainController(TreeSet<Package> packagesTree) {
        this.packagesTree = packagesTree;
    }

    @FXML
    public void initialize() {

        ObservableList<Package> packages = FXCollections.observableArrayList(packagesTree);
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPackageDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackageAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDeliveryPrice.setCellValueFactory(new PropertyValueFactory<>("deliveryCost"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        tableViewPackage.setItems(packages);


        tableViewPackage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!tableViewPackage.getItems().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
                        && tableViewPackage.getSelectionModel().getSelectedItem()!=null) {
                    ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                    Stage stage = new Stage();
                    DetailsController detailsController = new DetailsController(tableViewPackage.getSelectionModel().getSelectedItem());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/details.fxml"),bundle);
                    loader.setController(detailsController);
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setScene(new Scene(root));
                    stage.setTitle(bundle.getString("details"));
                    stage.setResizable(false);
                    stage.toFront();
                    stage.show();
                }
            }
        });

    }



    public void logoutAction(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Stage stage1 = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"),bundle);
        stage1.setScene(new Scene(root));
        stage1.setTitle(bundle.getString("login"));
        stage1.setResizable(false);
        stage1.toFront();
        stage1.show();


    }

    public void aboutAction(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"),bundle);
        stage.setScene(new Scene(root));
        stage.setTitle(bundle.getString("aboutT"));
        stage.setResizable(false);
        stage.toFront();
        stage.show();

    }

    public void exitAction (ActionEvent actionEvent) {

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();

    }

    public void monthlyReportAction (ActionEvent actionEvent) {

        if(!tableViewPackage.getItems().isEmpty()) {
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String string = LocalDateTime.now().format(format);
                int date = Integer.parseInt(string.substring(6, 10) + string.substring(3, 5)) * 100;
                new PrintReportCourier().showReport(model.getConn(), tableViewPackage.getItems().get(0).getCourier().getId(), date);
            } catch (JRException e1) {
                e1.printStackTrace();
            }
        }
        else {
            ResourceBundle bundle =  ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("noPackageCourierHeader"));
            alert.setContentText(bundle.getString("noPackageCourierDesc"));
            alert.showAndWait();
        }

    }

    public void annualReportAction (ActionEvent actionEvent) {

        if(!tableViewPackage.getItems().isEmpty()) {
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String string = LocalDateTime.now().format(format);
                int date = Integer.parseInt(string.substring(6, 10)) * 10000;
                new PrintReportCourier().showReport(model.getConn(), tableViewPackage.getItems().get(0).getCourier().getId(), date);
            } catch (JRException e1) {
                e1.printStackTrace();
            }
        }
        else {
            ResourceBundle bundle =  ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("noPackageCourierHeader"));
            alert.setContentText(bundle.getString("noPackageCourierDesc"));
            alert.showAndWait();
        }

    }

    public void englishAction(ActionEvent actionEvent) {

        Locale.setDefault(new Locale("en", "US"));
        Stage primaryStage=(Stage) logoutBtn.getScene().getWindow();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/courierMain.fxml" ), bundle);
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        primaryStage.setTitle(bundle.getString("expressMail"));
        primaryStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        primaryStage.show();
    }

    public void bosnianAction(ActionEvent actionEvent) {

        Locale.setDefault(new Locale("bs", "BA"));
        Stage primaryStage=(Stage) logoutBtn.getScene().getWindow();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/courierMain.fxml" ), bundle);
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        primaryStage.setTitle(bundle.getString("expressMail"));
        primaryStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        primaryStage.show();

    }
}
