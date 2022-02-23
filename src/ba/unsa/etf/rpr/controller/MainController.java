package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.ExpressMailDAO;
import ba.unsa.etf.rpr.OrderStatus;
import ba.unsa.etf.rpr.model.Courier;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


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
                    Stage stage = new Stage();
                    DetailsController detailsController = new DetailsController(tableViewPackage.getSelectionModel().getSelectedItem());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/details.fxml"));
                    loader.setController(detailsController);
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.toFront();
                    stage.show();
                }
            }
        });

        autoResizeColumns(tableViewPackage);

    }

    public static void autoResizeColumns( TableView<?> table ) {

        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().forEach( column ->
        {
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getItems().size(); i++ )
            {
                //cell must not be empty
                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    double calculateWidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if ( calculateWidth > max )
                    {
                        max = calculateWidth;
                    }
                }
            }
            column.setPrefWidth( max + 10.0d );
        } );
            table.setMaxWidth(Region.USE_COMPUTED_SIZE);

    }


    public void aboutAction(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.toFront();
        stage.show();

    }

    public void logoutAction(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();

        Stage stage1 = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage1.setScene(new Scene(root));
        stage1.setTitle("Login");
        stage1.setResizable(false);
        stage1.toFront();
        stage1.show();


    }

    public void makePackageAction(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        PackageController packageController = new PackageController(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/package.fxml"));
        loader.setController(packageController);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
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
                autoResizeColumns(tableViewPackage);
            }
        });
    }

    public void deletePackageAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Delete package");
        alert.setContentText("Are you sure you want to delete the selected package?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK &&tableViewPackage.getSelectionModel().getSelectedItem()!=null) {
            model.deletePackage(tableViewPackage.getSelectionModel().getSelectedItem().getId());
            tableViewPackage.getItems().remove(tableViewPackage.getSelectionModel().getSelectedItem());
            tableViewPackage.refresh();
            autoResizeColumns(tableViewPackage);
        }

    }

    public void updatePackageAction(ActionEvent actionEvent) throws IOException {

        try {
            Stage stage = new Stage();
            PackageController packageController = new PackageController(tableViewPackage.getSelectionModel().getSelectedItem());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/package.fxml"));
            loader.setController(packageController);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.toFront();
            stage.show();

            stage.setOnHiding(e -> {
                Package aPackage = packageController.getPackage();
                if (aPackage != null) {
                    model.updatePackage(aPackage);
                    tableViewPackage.refresh();
                    autoResizeColumns(tableViewPackage);
                    if(aPackage.getOrderStatus()== OrderStatus.ERROR) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("An error occurred while delivering");
                        alert.setContentText("If you want to see feedback (if any exists) press OK.");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            Stage stage1 = new Stage();
                            DetailsController detailsController = new DetailsController(aPackage);
                            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/details.fxml"));
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
                        }


                    }
                }
            });
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setHeaderText("Update forbidden");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }



    }

    public void registerCourierAction(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        RegisterController registerController = new RegisterController(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courier.fxml"));
        loader.setController(registerController);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.toFront();
        stage.show();

        stage.setOnHiding(e->{
            Courier courier = registerController.getCourier();
            if(courier !=null) {
                model.addCourier(courier);
            }
        });

    }

    public void updateCourierAction(ActionEvent actionEvent) throws IOException {

        try {
            ChoiceDialog<Courier> dialog = new ChoiceDialog<>(model.couriers().get(0),model.couriers());
            dialog.setTitle("Update courier");
            dialog.setHeaderText("Updating courier");
            dialog.setContentText("Select the courier you want to update:");

            Optional<Courier> result = dialog.showAndWait();
            if (result.isPresent()){

                Stage stage = new Stage();
                RegisterController registerController = new RegisterController(result.get());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courier.fxml"));
                loader.setController(registerController);
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.toFront();
                stage.show();

                stage.setOnHiding(e->{
                    Courier courier = registerController.getCourier();
                    if(courier !=null) {
                        model.updateCourier(courier);
                        ObservableList<Package> packages = FXCollections.observableArrayList();
                        packages.addAll(model.packages());
                        tableViewPackage.setItems(packages);
                        tableViewPackage.refresh();
                        autoResizeColumns(tableViewPackage);
                    }
                });

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void deleteCourierAction(ActionEvent actionEvent) {

        try {
            ChoiceDialog<Courier> dialog = new ChoiceDialog<>(model.couriers().get(0),model.couriers());
            dialog.setTitle("Delete courier");
            dialog.setHeaderText("Deleting courier");
            dialog.setContentText("Select the courier you want to delete:");

            Optional<Courier> result = dialog.showAndWait();
            if (result.isPresent()){

                ArrayList<Courier> couriers = (ArrayList<Courier>) model.couriers();
                couriers.remove(result.get());
                ChoiceDialog<Courier> dialog2 = new ChoiceDialog<>(couriers.get(0),couriers);
                dialog2.setTitle("Delete courier");
                dialog2.setHeaderText("Assigning packages");
                dialog2.setContentText("Select a courier to pick up the shipments of the deleted courier (if any exists):");
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
                autoResizeColumns(tableViewPackage);
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

    public void exitAction (ActionEvent actionEvent) {

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();

    }
}
