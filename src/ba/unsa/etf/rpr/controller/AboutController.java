package ba.unsa.etf.rpr.controller;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class AboutController {
    static HostServices hostServices ;
    public Button cancelBtn;

    public static void setGetHostController(HostServices hostServices)
    {
        AboutController.hostServices = hostServices;
    }

    @FXML
    Hyperlink link=new Hyperlink();

    @FXML
    public void initialize(){
        link.setOnAction(e->hostServices.showDocument("https://github.com/beganov1c"));
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
