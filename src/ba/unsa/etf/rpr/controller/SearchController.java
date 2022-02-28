package ba.unsa.etf.rpr.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class SearchController {

    public TextField fldImage;
    public ListView<String> imageView;

    private String slika;


    public SearchController() {
        slika ="";
    }

    @FXML
    public void initialize() {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!imageView.getItems().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && imageView.getSelectionModel().getSelectedItem()!=null) {
                    slika= imageView.getSelectionModel().getSelectedItem();
                    Stage stage = (Stage) fldImage.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }


    public void searchAction(ActionEvent actionEvent) {

        if(fldImage.getText().trim().isEmpty()){
            ResourceBundle bundle =  ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("searchHeader"));
            alert.setContentText(bundle.getString("searchDescription"));
            alert.showAndWait();
            return;
        }

        File file = new File(System.getProperty("user.home"));
        new Thread(() -> search(file)).start();

    }

    private void search(File file) {
        if (file == null) return;
        if (file.isDirectory()) {
            if (file.listFiles() != null) {
                for (File temp : Objects.requireNonNull(file.listFiles())) {
                    if (temp.isDirectory()) search(temp);
                    else {
                        if (temp.getName().contains(fldImage.getText()) && getExtension(temp.getName()).isPresent() &&
                                (getExtension(temp.getName()).get().equals("jpg") || getExtension(temp.getName()).get().equals("gif")
                                        || getExtension(temp.getName()).get().equals("png"))) {
                            Platform.runLater(()-> imageView.getItems().add(temp.getAbsolutePath()));
                        }
                    }
                }
            }
        }
    }

    public Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public String getSlika() {

        return slika;
    }

}
