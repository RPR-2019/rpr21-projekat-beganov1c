package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.model.Courier;
import ba.unsa.etf.rpr.model.Package;
import ba.unsa.etf.rpr.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;

public class DetailsController {
    public TextField packageIdField;
    public TextField descriptionField;
    public TextField senderNameField;
    public TextField senderTelephoneNumberField;
    public TextField senderAddressField;
    public TextField senderCityField;
    public TextField senderZipCodeField;
    public TextField receiverNameField;
    public TextField receiverTelephoneNumberField;
    public TextField receiverAddressField;
    public TextField receiverCityField;
    public TextField receiverZipCodeField;
    public TextField courierNameField;
    public TextField courierTelephoneNumberField;
    public TextField weightField;
    public TextField deliveryPriceField;
    public TextField courierIdField;
    private Package aPackage;
    public TextField deliveryTimeField;
    public TextField sendingTimeField;
    public TextField orderStatusField;



    public DetailsController(Package aPackage) {
        this.aPackage = aPackage;
    }

    @FXML
    public void initialize() {
        User sender = aPackage.getSender();
        User receiver = aPackage.getReceiver();
        Courier courier = aPackage.getCourier();
        descriptionField.setText(aPackage.getDescription());
        if(sender.getZipCode()!=-1) {
            senderAddressField.setText(sender.getAddress());
            senderCityField.setText(sender.getCity());
            senderZipCodeField.setText(String.valueOf(sender.getZipCode()));
            senderNameField.setText(sender.getName());
            senderTelephoneNumberField.setText(sender.getTelephoneNumber());
        }
        receiverNameField.setText(receiver.getName());
        receiverTelephoneNumberField.setText(receiver.getTelephoneNumber());
        receiverAddressField.setText(receiver.getAddress());
        courierIdField.setText(String.valueOf(courier.getId()));
        courierNameField.setText(courier.getName());
        courierTelephoneNumberField.setText(courier.getTelephoneNumber());
        weightField.setText(String.valueOf(aPackage.getWeight()));
        deliveryPriceField.setText(String.valueOf(aPackage.getDeliveryCost()));
        receiverCityField.setText(receiver.getCity());
        receiverZipCodeField.setText(String.valueOf(receiver.getZipCode()));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if(aPackage.getSendingTime()!=null)sendingTimeField.setText(aPackage.getSendingTime().format(format));
        if(aPackage.getDeliveryTime()!=null)deliveryTimeField.setText(aPackage.getDeliveryTime().format(format));
        orderStatusField.setText(String.valueOf(aPackage.getOrderStatus()));


    }
}
