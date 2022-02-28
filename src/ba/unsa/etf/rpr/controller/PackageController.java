package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.DAO.ExpressMailDAO;
import ba.unsa.etf.rpr.enums.OrderStatus;
import ba.unsa.etf.rpr.exception.PackageDeliveredException;
import ba.unsa.etf.rpr.exception.PackageErrorException;
import ba.unsa.etf.rpr.model.Courier;
import ba.unsa.etf.rpr.model.Package;
import ba.unsa.etf.rpr.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PackageController {

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
    public TextField weightField;
    public TextField deliveryPriceField;
    public ChoiceBox<Courier> courierChoice;
    public Button okBtn;
    public Button cancelBtn;
    public RadioButton courierRadioBtn;
    public TextField courierNameField;
    public TextField courierTelephoneNumberField;
    public ChoiceBox<OrderStatus> orderStatusChoice;
    public TextField deliveryTimeField;
    public TextField sendingTimeField;
    public RadioButton oldSenderRadio;
    public RadioButton newSenderRadio;
    public RadioButton oldReceiverRadio;
    public RadioButton newReceiverRadio;
    public ChoiceBox<User> oldSenderChoice;
    public ChoiceBox<User> oldReceiverChoice;
    private Package aPackage;
    boolean validate=false;



    private ExpressMailDAO model = ExpressMailDAO.getInstance();


    public PackageController(Package aPackage) {
        ResourceBundle bundle =  ResourceBundle.getBundle("Translation");
        this.aPackage = aPackage;
        if(aPackage!=null) {
            if (aPackage.getOrderStatus() == OrderStatus.DELIVERED)
                throw new PackageDeliveredException(bundle.getString("packageDeliveredException"));
            if (aPackage.getOrderStatus() == OrderStatus.ERROR)
                throw new PackageErrorException(bundle.getString("packageErrorException"));
            if(aPackage.getSender().getId()==1)
                validate=true;
        }
    }

    @FXML
    public void initialize() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        orderStatusChoice.setDisable(true);
        if(aPackage !=null) {
            Courier courier = aPackage.getCourier();
            User sender = aPackage.getSender();
            User receiver = aPackage.getReceiver();
            descriptionField.setText(aPackage.getDescription());
            if(sender.getZipCode()!=-1) {
                senderNameField.setText(sender.getName());
                senderTelephoneNumberField.setText(sender.getTelephoneNumber());
                senderAddressField.setText(sender.getAddress());
                senderCityField.setText(sender.getCity());
                senderZipCodeField.setText(String.valueOf(sender.getZipCode()));
            }
            receiverNameField.setText(receiver.getName());
            receiverTelephoneNumberField.setText(receiver.getTelephoneNumber());
            receiverAddressField.setText(receiver.getAddress());
            receiverCityField.setText(receiver.getCity());
            receiverZipCodeField.setText(String.valueOf(receiver.getZipCode()));
            weightField.setText(String.valueOf(aPackage.getWeight()));
            deliveryPriceField.setText(String.valueOf(aPackage.getDeliveryCost()));
            courierNameField.setText(courier.getName());
            courierTelephoneNumberField.setText(courier.getTelephoneNumber());
            if(aPackage.getSendingTime()!=null)sendingTimeField.setText(aPackage.getSendingTime().format(format));
            if(aPackage.getDeliveryTime()!=null)deliveryTimeField.setText(aPackage.getDeliveryTime().format(format));
            orderStatusChoice.setDisable(false);

        }

        courierNameField.setEditable(false);
        courierTelephoneNumberField.setEditable(false);
        deliveryTimeField.setEditable(false);
        sendingTimeField.setEditable(false);
        sendingTimeField.setText(LocalDateTime.now().format(format));


        courierRadioBtn.setOnAction(actionEvent -> {
            if(courierRadioBtn.isSelected()) {
                courierNameField.setEditable(true);
                courierTelephoneNumberField.setEditable(true);
            }
            else {
                courierNameField.setEditable(false);
                courierTelephoneNumberField.setEditable(false);
            }
        });

        courierChoice.setOnAction(actionEvent -> {
            courierNameField.setText(courierChoice.getSelectionModel().getSelectedItem().getName());
            courierTelephoneNumberField.setText(courierChoice.getSelectionModel().getSelectedItem().getTelephoneNumber());
            if(aPackage !=null)
                aPackage.setCourier(courierChoice.getValue());
        });

        ObservableList<Courier> couriers = FXCollections.observableArrayList(model.couriers());
        courierChoice.setItems(couriers);
        if(aPackage !=null)
            courierChoice.getSelectionModel().select(aPackage.getCourier());
        else if(!couriers.isEmpty())
            courierChoice.getSelectionModel().select(couriers.get(0));


        ObservableList<OrderStatus> orderStatuses = FXCollections.observableArrayList(getOrderStatuses());
        orderStatusChoice.setItems(orderStatuses);
        if(aPackage !=null) orderStatusChoice.getSelectionModel().select(aPackage.getOrderStatus());
        else orderStatusChoice.getSelectionModel().select(OrderStatus.IN_WAREHOUSE);

        if(aPackage==null) {

            oldSenderChoice.setItems(model.users());
            oldReceiverChoice.setItems(model.users());
            oldSenderChoice.setDisable(true);
            oldReceiverChoice.setDisable(true);

            oldSenderRadio.setOnAction(actionEvent -> {
                if (oldSenderRadio.isSelected()) {
                    oldSenderChoice.setDisable(false);
                    oldSenderChoice.getSelectionModel().select(0);
                }
            });

            newSenderRadio.setOnAction(actionEvent -> {
                if (newSenderRadio.isSelected()) {
                    oldSenderChoice.setDisable(true);
                }
            });

            oldReceiverRadio.setOnAction(actionEvent -> {
                if (oldReceiverRadio.isSelected()) {
                    oldReceiverChoice.setDisable(false);
                    oldReceiverChoice.getSelectionModel().select(0);
                }
            });

            newReceiverRadio.setOnAction(actionEvent -> {
                if (newReceiverRadio.isSelected()) {
                    oldReceiverChoice.setDisable(true);
                }
            });

            oldSenderChoice.setOnAction(actionEvent -> {
                senderNameField.setText(oldSenderChoice.getSelectionModel().getSelectedItem().getName());
                senderAddressField.setText(oldSenderChoice.getSelectionModel().getSelectedItem().getAddress());
                senderTelephoneNumberField.setText(oldSenderChoice.getSelectionModel().getSelectedItem().getTelephoneNumber());
                senderCityField.setText(oldSenderChoice.getSelectionModel().getSelectedItem().getCity());
                senderZipCodeField.setText(String.valueOf(oldSenderChoice.getSelectionModel().getSelectedItem().getZipCode()));
            });
            oldReceiverChoice.setOnAction(actionEvent -> {
                receiverNameField.setText(oldReceiverChoice.getSelectionModel().getSelectedItem().getName());
                receiverAddressField.setText(oldReceiverChoice.getSelectionModel().getSelectedItem().getAddress());
                receiverTelephoneNumberField.setText(oldReceiverChoice.getSelectionModel().getSelectedItem().getTelephoneNumber());
                receiverCityField.setText(oldReceiverChoice.getSelectionModel().getSelectedItem().getCity());
                receiverZipCodeField.setText(String.valueOf(oldReceiverChoice.getSelectionModel().getSelectedItem().getZipCode()));
            });
        }


    }

    private ArrayList<OrderStatus> getOrderStatuses() {

        ArrayList<OrderStatus> orderStatuses = new ArrayList<>();
        orderStatuses.add(OrderStatus.IN_WAREHOUSE);
        orderStatuses.add(OrderStatus.IN_TRANSPORT);
        orderStatuses.add(OrderStatus.DELIVERED);
        orderStatuses.add(OrderStatus.ERROR);


        return orderStatuses;
    }

    public void okAction(ActionEvent actionEvent) {
        ResourceBundle bundle =  ResourceBundle.getBundle("Translation");
        if (oldReceiverRadio.isSelected() && oldSenderRadio.isSelected() && oldSenderChoice.getSelectionModel().getSelectedItem().getId()==oldReceiverChoice.getSelectionModel().getSelectedItem().getId()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("error"));
            alert.setHeaderText(bundle.getString("senderSame"));
            alert.setContentText(bundle.getString("selectAnother"));

            alert.showAndWait();
        }
        else {
            boolean allOk = check();

            boolean allOkWithoutSender = check2();
            if (allOk || allOkWithoutSender) {

                if (aPackage == null) {
                    Optional<ButtonType> result = Optional.empty();
                    if (allOkWithoutSender && !allOk) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle(bundle.getString("warning"));
                        alert.setHeaderText(bundle.getString("senderInfo"));
                        alert.setContentText(bundle.getString("senderContent"));

                        result = alert.showAndWait();
                    }
                    if (allOk || (result.isPresent() && result.get() == ButtonType.OK)) {
                        Courier courier = courierChoice.getValue();
                        courier.setName(courierNameField.getText());
                        courier.setTelephoneNumber(courierTelephoneNumberField.getText());

                        int senderId = -1;
                        int receiverId = -1;
                        if (oldReceiverRadio.isSelected())
                            receiverId = oldReceiverChoice.getSelectionModel().getSelectedItem().getId();
                        if (oldSenderRadio.isSelected())
                            senderId = oldSenderChoice.getSelectionModel().getSelectedItem().getId();
                        int number = -1;
                        if (allOk)
                            number = Integer.parseInt(senderZipCodeField.getText());

                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        aPackage = new Package(-1, descriptionField.getText(), receiverAddressField.getText(),
                                new User(senderId, senderNameField.getText(), senderTelephoneNumberField.getText(), senderAddressField.getText(), senderCityField.getText(), number),
                                new User(receiverId, receiverNameField.getText(), receiverTelephoneNumberField.getText(), receiverAddressField.getText(), receiverCityField.getText(), Integer.parseInt(receiverZipCodeField.getText())),
                                courier, Integer.parseInt(weightField.getText()), Integer.parseInt(deliveryPriceField.getText()), receiverCityField.getText(), Integer.parseInt(receiverZipCodeField.getText()), LocalDateTime.parse(sendingTimeField.getText(), format), null, OrderStatus.IN_WAREHOUSE);

                    }
                } else if (allOk || validate) {

                    User sender = aPackage.getSender();
                    User receiver = aPackage.getReceiver();
                    Courier courier = aPackage.getCourier();

                    aPackage.setDeliveryCost(Integer.parseInt(deliveryPriceField.getText()));
                    aPackage.setWeight(Integer.parseInt(weightField.getText()));
                    aPackage.setDescription(descriptionField.getText());
                    aPackage.setAddress(receiverAddressField.getText());
                    aPackage.setCity(receiverCityField.getText());
                    aPackage.setZipCode(Integer.parseInt(receiverZipCodeField.getText()));

                    sender.setName(senderNameField.getText());
                    sender.setAddress(senderAddressField.getText());
                    sender.setTelephoneNumber(senderTelephoneNumberField.getText());
                    sender.setCity(senderCityField.getText());
                    sender.setZipCode(Integer.parseInt(senderZipCodeField.getText()));

                    receiver.setName(receiverNameField.getText());
                    receiver.setAddress(receiverAddressField.getText());
                    receiver.setTelephoneNumber(receiverTelephoneNumberField.getText());
                    receiver.setCity(receiverCityField.getText());
                    receiver.setZipCode(Integer.parseInt(receiverZipCodeField.getText()));

                    aPackage.setOrderStatus(orderStatusChoice.getSelectionModel().getSelectedItem());


                    courier.setName(courierNameField.getText());
                    courier.setTelephoneNumber(courierTelephoneNumberField.getText());

                    aPackage.setCourier(courier);
                    aPackage.setSender(sender);
                    aPackage.setReceiver(receiver);
                    if (orderStatusChoice.getSelectionModel().getSelectedItem() == OrderStatus.DELIVERED)
                        aPackage.setDeliveryTime(LocalDateTime.now());


                }
                Stage stage = (Stage) okBtn.getScene().getWindow();
                stage.close();
            }
        }

    }


    public void cancelAction(ActionEvent actionEvent) {
        aPackage =null;
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private boolean check2() {

        checkTextField(descriptionField);
        checkTextField(receiverAddressField);
        checkTextField(receiverNameField);
        checkTextField(receiverTelephoneNumberField);
        checkTextField(courierNameField);
        checkTextField(courierTelephoneNumberField);
        checkTextFieldNumber(weightField);
        checkTextFieldNumber(deliveryPriceField);
        checkTextField(receiverCityField);
        checkTextFieldNumber(receiverZipCodeField);


        return checkTextField(descriptionField) &&
                checkTextField(receiverAddressField) &&
                checkTextField(receiverNameField) &&
                checkTextField(receiverTelephoneNumberField) &&
                checkTextField(courierNameField) &&
                checkTextField(courierTelephoneNumberField) &&
                checkTextFieldNumber(weightField) &&
                checkTextFieldNumber(deliveryPriceField) &&
                checkTextField(receiverCityField) &&
                checkTextFieldNumber(receiverZipCodeField);
    }

    private boolean check() {

        checkTextField(descriptionField);
        checkTextField(receiverAddressField);
        checkTextField(receiverNameField);
        checkTextField(receiverTelephoneNumberField);
        checkTextField(courierNameField);
        checkTextField(courierTelephoneNumberField);
        checkTextField(senderAddressField);
        checkTextField(senderNameField);
        checkTextField(senderTelephoneNumberField);
        checkTextFieldNumber(weightField);
        checkTextFieldNumber(deliveryPriceField);
        checkTextField(senderCityField);
        checkTextField(receiverCityField);
        checkTextFieldNumber(receiverZipCodeField);
        checkTextFieldNumber(senderZipCodeField);


        return checkTextField(descriptionField) &&
                checkTextField(receiverAddressField) &&
                checkTextField(receiverNameField) &&
                checkTextField(receiverTelephoneNumberField) &&
                checkTextField(courierNameField) &&
                checkTextField(courierTelephoneNumberField) &&
                checkTextField(senderAddressField) &&
                checkTextField(senderNameField) &&
                checkTextField(senderTelephoneNumberField) &&
                checkTextFieldNumber(weightField) &&
                checkTextFieldNumber(deliveryPriceField) &&
                checkTextField(senderCityField) &&
                checkTextField(receiverCityField) &&
                checkTextFieldNumber(receiverZipCodeField) &&
                checkTextFieldNumber(senderZipCodeField);

    }


    private boolean checkTextFieldNumber(TextField textField) {
        try {
            int number = Integer.parseInt(textField.getText());
            if(number<=0) {
                textField.getStyleClass().removeAll("fieldCorrect");
                textField.getStyleClass().add("fieldIncorrect");
                return false;
            }
            else {
                textField.getStyleClass().removeAll("fieldIncorrect");
                textField.getStyleClass().add("fieldCorrect");
                return true;
            }


        }
        catch (Exception e) {

            textField.getStyleClass().removeAll("fieldCorrect");
            textField.getStyleClass().add("fieldIncorrect");
            return false;

        }
    }

    private boolean checkTextField(TextField textField) {

        if(textField.getText().trim().isEmpty()) {
            textField.getStyleClass().removeAll("fieldCorrect");
            textField.getStyleClass().add("fieldIncorrect");
            return false;
        }
        else {
            textField.getStyleClass().removeAll("fieldIncorrect");
            textField.getStyleClass().add("fieldCorrect");
            return true;
        }
    }

    public Package getPackage() {
        return aPackage;
    }
}
