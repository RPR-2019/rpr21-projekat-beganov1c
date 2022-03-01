package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.DAO.ExpressMailDAO;
import ba.unsa.etf.rpr.enums.OrderStatus;
import ba.unsa.etf.rpr.report.PrintReportMain;
import ba.unsa.etf.rpr.model.Courier;
import ba.unsa.etf.rpr.model.Manager;
import ba.unsa.etf.rpr.model.Package;
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
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController {


    public TableView<Package> tableViewPackage;
    public TableColumn colPackageId;
    public TableColumn colPackageDescription;
    public TableColumn colPackageAddress;
    public TableColumn colCourier;
    public TableColumn colWeight;
    public Button logoutBtn;
    private ExpressMailDAO model = ExpressMailDAO.getInstance();

    @FXML
    public void initialize() {

        ObservableList<Package> packages = FXCollections.observableArrayList();
        packages.addAll(model.packages());
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPackageDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackageAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCourier.setCellValueFactory(new PropertyValueFactory<>("courier"));
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
                    stage.setTitle(bundle.getString("details"));
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.toFront();
                    stage.show();
                }
            }
        });

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

    public void makePackageAction(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Stage stage = new Stage();
        PackageController packageController = new PackageController(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/package.fxml"),bundle);
        loader.setController(packageController);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle(bundle.getString("createPackage"));
        stage.setResizable(false);
        stage.toFront();
        stage.show();

        stage.setOnHiding(e->{
            Package aPackage = packageController.getPackage();
            if(aPackage !=null) {
                model.createPackage(aPackage);
                ObservableList<Package> packages = FXCollections.observableArrayList();
                packages.addAll(model.packages());
                tableViewPackage.setItems(packages);
                tableViewPackage.refresh();
            }
        });
    }

    public void deletePackageAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        alert.setTitle(bundle.getString("warning"));
        alert.setHeaderText(bundle.getString("deletePackage"));
        alert.setContentText(bundle.getString("deletePackageContent"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK &&tableViewPackage.getSelectionModel().getSelectedItem()!=null) {
            model.deletePackage(tableViewPackage.getSelectionModel().getSelectedItem().getId());
            tableViewPackage.getItems().remove(tableViewPackage.getSelectionModel().getSelectedItem());
            tableViewPackage.refresh();
        }

    }

    public void updatePackageAction(ActionEvent actionEvent) throws IOException {

        if(tableViewPackage.getSelectionModel().getSelectedItem()!=null) {
            try {
                Stage stage = new Stage();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                PackageController packageController = new PackageController(tableViewPackage.getSelectionModel().getSelectedItem());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/package.fxml"),bundle);
                loader.setController(packageController);
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle(bundle.getString("updatePackage"));
                stage.toFront();
                stage.show();

                stage.setOnHiding(e -> {
                    Package aPackage = packageController.getPackage();
                    if (aPackage != null) {
                        model.updatePackage(aPackage);
                        tableViewPackage.refresh();
                        if (aPackage.getOrderStatus() == OrderStatus.ERROR) {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle(bundle.getString("deliveringError"));
                            alert.setHeaderText(bundle.getString("deliveringErrorHeader"));
                            alert.setContentText(bundle.getString("chooseOption"));

                            ButtonType buttonTypeFeedback = new ButtonType(bundle.getString("showFeedback"));
                            ButtonType buttonTypeResend = new ButtonType(bundle.getString("resendPackage"));
                            ButtonType buttonTypeDelte = new ButtonType(bundle.getString("deletePackageFromData"));

                            alert.getButtonTypes().setAll(buttonTypeFeedback, buttonTypeResend, buttonTypeDelte);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonTypeFeedback){
                                Stage stage1 = new Stage();
                                DetailsController detailsController = new DetailsController(aPackage);
                                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/details.fxml"),bundle);
                                loader1.setController(detailsController);
                                Parent root1 = null;
                                try {
                                    root1 = loader1.load();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                stage1.setScene(new Scene(root1));
                                stage1.setResizable(false);
                                stage1.toFront();
                                stage1.show();

                            } else if (result.get() == buttonTypeResend) {

                                aPackage.setSendingTime(LocalDateTime.now());
                                aPackage.setOrderStatus(OrderStatus.IN_WAREHOUSE);
                                model.updatePackage(aPackage);

                            } else {
                                deletePackageAction(actionEvent);
                            }


                        }
                    }
                });
            } catch (Exception e) {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("update"));
                alert.setHeaderText(bundle.getString("updateForbidden"));
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

        else {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("packageNotSelected"));
            alert.setContentText(bundle.getString("selectPackage"));
            alert.showAndWait();
        }




    }

    public void registerCourierAction(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Stage stage = new Stage();
        CourierController courierController = new CourierController(null, model.getUsernames());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courier.fxml"),bundle);
        loader.setController(courierController);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle(bundle.getString("createCourier"));
        stage.toFront();
        stage.show();

        stage.setOnHiding(e->{
            Courier courier = courierController.getCourier();
            if(courier !=null) {
                model.addCourier(courier);
            }
        });

    }

    public void updateCourierAction(ActionEvent actionEvent) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        try {
            ChoiceDialog<Courier> dialog = new ChoiceDialog<>(model.couriers().get(0),model.couriers());
            dialog.setTitle(bundle.getString("updateCourier"));
            dialog.setHeaderText(bundle.getString("updateCourierHeader"));
            dialog.setContentText(bundle.getString("courierContent"));

            Optional<Courier> result = dialog.showAndWait();
            if (result.isPresent()){
                Stage stage = new Stage();
                CourierController courierController = new CourierController(result.get(), model.getUsernames());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courier.fxml"),bundle);
                loader.setController(courierController);
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.setTitle(bundle.getString("updateCourier"));
                stage.setResizable(false);
                stage.toFront();
                stage.show();

                stage.setOnHiding(e->{
                    Courier courier = courierController.getCourier();
                    if(courier !=null) {
                        model.updateCourier(courier);
                        ObservableList<Package> packages = FXCollections.observableArrayList();
                        packages.addAll(model.packages());
                        tableViewPackage.setItems(packages);
                        tableViewPackage.refresh();
                    }
                });

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void deleteCourierAction(ActionEvent actionEvent) {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        try {
            ChoiceDialog<Courier> dialog = new ChoiceDialog<>(model.couriers().get(0),model.couriers());
            dialog.setTitle(bundle.getString("deleteCourier"));
            dialog.setHeaderText(bundle.getString("deleteCourierHeader"));
            dialog.setContentText(bundle.getString("deleteCourierContent"));

            Optional<Courier> result = dialog.showAndWait();
            if (result.isPresent()){

                ArrayList<Courier> couriers = (ArrayList<Courier>) model.couriers();
                couriers.remove(result.get());
                ChoiceDialog<Courier> dialog2 = new ChoiceDialog<>(couriers.get(0),couriers);
                dialog2.setTitle(bundle.getString("deleteCourier"));
                dialog2.setHeaderText(bundle.getString("assigningPackages"));
                dialog2.setContentText(bundle.getString("assigningContent"));
                Optional<Courier> result2 = dialog2.showAndWait();
                if(result2.isEmpty())
                    return;
                for(Package aPackage : model.packages()) {
                    if(aPackage.getCourier().getId()==result.get().getId())
                        aPackage.setCourier(result2.get());
                        model.updatePackage(aPackage);
                }
                model.deleteCourier(result.get());
                ObservableList<Package> packages = FXCollections.observableArrayList();
                packages.addAll(model.packages());
                tableViewPackage.setItems(packages);
                tableViewPackage.refresh();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void registerManagerAction(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        ManagerController managerController = new ManagerController(null, model.getUsernames());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manager.fxml"),bundle);
        loader.setController(managerController);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle(bundle.getString("createManager"));
        stage.toFront();
        stage.show();

        stage.setOnHiding(e->{
            Manager manager = managerController.getManager();
            if(manager !=null) {
                model.addManager(manager);
            }
        });
    }


    public void updateManagerAction(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        try {
            ChoiceDialog<Manager> dialog = new ChoiceDialog<>(model.managers().get(0),model.managers());
            dialog.setTitle(bundle.getString("updateManager"));
            dialog.setHeaderText(bundle.getString("updateManagerHeader"));
            dialog.setContentText(bundle.getString("updateManagerContent"));

            Optional<Manager> result = dialog.showAndWait();
            if (result.isPresent()){
                Stage stage = new Stage();
                ManagerController managerController = new ManagerController(result.get(), model.getUsernames());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manager.fxml"),bundle);
                loader.setController(managerController);
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.setTitle(bundle.getString("updateManager"));
                stage.setResizable(false);
                stage.toFront();
                stage.show();

                stage.setOnHiding(e->{
                    Manager manager = managerController.getManager();
                    if(manager !=null) {
                        model.updateManager(manager);
                    }
                });

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteManagerAction(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        try {
            ChoiceDialog<Manager> dialog = new ChoiceDialog<>(model.managers().get(0),model.managers());
            dialog.setTitle(bundle.getString("deleteManager"));
            dialog.setHeaderText(bundle.getString("deleteManagerHeader"));
            dialog.setContentText(bundle.getString("deleteManagerContent"));

            Optional<Manager> result = dialog.showAndWait();
            if (result.isPresent() && result.isPresent()){
                model.deleteManager(result.get());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAction (ActionEvent actionEvent) {
        FileChooser chooser=new FileChooser();
        File file=chooser.showSaveDialog(new Stage());
        if(file==null) return;
        model.writeInFile(file);
    }

    public void reportAction(ActionEvent actionEvent) {

        try {
            new PrintReportMain().showReport(model.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }
    public void englishAction(ActionEvent actionEvent) {

        Locale.setDefault(new Locale("en", "US"));
        Stage primaryStage=(Stage) logoutBtn.getScene().getWindow();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"),bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(bundle.getString("expressMail"));
        primaryStage.toFront();
        primaryStage.show();

    }

    public void bosnianAction(ActionEvent actionEvent) {

        Locale.setDefault(new Locale("bs", "BA"));
        Stage primaryStage=(Stage) logoutBtn.getScene().getWindow();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"),bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(bundle.getString("expressMail"));
        primaryStage.toFront();
        primaryStage.show();

    }

    public void exitAction (ActionEvent actionEvent) {

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();

    }
}
