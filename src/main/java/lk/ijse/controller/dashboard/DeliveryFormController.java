/*package lk.ijse.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.tm.customerTm;
import lk.ijse.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryFormController implements Initializable {

    public TextField txtCustomerID;
    public TextField txtorderID;
    public TextField txtdeliveryCost;

    public TextField txtdeliverydate;
    public TextField txtdeliveryID;
    public TextField txtdeliverydescription;
    public TableView tableDelivery;
    public TableColumn clmdeliveryId;
    public TableColumn clmdeliverydate;
    public TableColumn clmdeliveryDescription;
    public TableColumn clmdeeliverycost;
    public TableColumn clmorderID;
    public TableColumn txtcustomerId;
    public TableColumn clmCustomerId;

    public AnchorPane root;

    public void clmdeliveryId(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void clmdeliverydate(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void clmdeeliverycost(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void clmorderID(TableColumn.CellEditEvent cellEditEvent) {
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clmdeliveryId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        clmdeliverydate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        clmdeeliverycost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        clmCustomerId.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        tableListener();
        try {

            getAllDelivery();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void getAllDelivery() throws SQLException, ClassNotFoundException {

        List<customerDto> customers = CustomerModel.getCustomers();
        List<customerTm> tmList = new ArrayList<>();
        for(customerDto dto: customers){
            tmList.add(new customerTm(
                    dto.getCustomerID(),
                    dto.getCustomerName(),
                    dto.getCustomerAddress(),
                    dto.getCustomerContactNumber()
            ));
        }
       // updateCustomerTable(tmList);
    }


    public void tableListener() {
    }

    public void buttonOnActionClear(ActionEvent actionEvent) {
    }

    public void buttonOnActionSave(ActionEvent actionEvent) {
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
    }

    public void buttonOnActionDelete(ActionEvent actionEvent) {
    }

    public void buttonOnActionBack(ActionEvent actionEvent) {
    }
}
*/

package lk.ijse.controller.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.DeliveryBo;
import lk.ijse.dto.deliveryDto;
import lk.ijse.dto.tm.deliveryTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.DeliveryModel;
import lk.ijse.model.OrderModel;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class DeliveryFormController  {

    public TextField txtCustomerID;

    public TextField txtorderID;

    public TextField txtdeliveryCost;
    public TextField txtdeliverydate;

    public TextField txtdeliveryID;

    public TextField txtdeliverydescription;

    public TableView<deliveryTm> tableDelivery;

    public TableColumn<?, ?> clmdeliveryId;

    public TableColumn<?, ?> clmdeliverydate;

    public TableColumn<?, ?> clmdeliveryDescription;

    public TableColumn<?, ?> clmdeeliverycost;

    public TableColumn<?, ?> clmorderID;

    public TableColumn<?, ?> txtcustomerId;

    public TableColumn<?, ?> clmCustomerId;

    public AnchorPane root;
    public DatePicker txtDate;
    public ComboBox<String> comCustomerID;
    public ComboBox<String> comOrderID;
    DeliveryBo deliveryBo= (DeliveryBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.DELIVERY);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        clmdeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        clmdeliverydate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        clmdeliveryDescription.setCellValueFactory(new PropertyValueFactory<>("deliveryDescription"));
        clmdeeliverycost.setCellValueFactory(new PropertyValueFactory<>("deliveryCost"));
        clmCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        tableListener();
        try {
            getAllDelivery();
            loadUserNames();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void tableListener() {

    }

    public void buttonOnActionClear(ActionEvent actionEvent) {
        clearFields();
    }

    public void buttonOnActionSave(ActionEvent actionEvent) {
        String id = txtdeliveryID.getText();
        String date = String.valueOf(txtDate.getValue());
        String description = txtdeliverydescription.getText();
        double delivarCost = Double.parseDouble(txtdeliveryCost.getText());
        String customerID = comCustomerID.getValue();

        if (id.isEmpty() || date.isEmpty() || description.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Field Not found").show();
            return;
        }

        deliveryDto dto = new deliveryDto(id, date, description, delivarCost, customerID);

        try {
            boolean isSaved = DeliveryModel.saveDelivery(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                getAllDelivery();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearFields();
    }

    private void getAllDelivery() throws SQLException, ClassNotFoundException {
        ObservableList<deliveryTm> observableList = FXCollections.observableArrayList();
        List<deliveryDto> delivery = DeliveryModel.getDelivery();

        for (deliveryDto dto : delivery) {
            observableList.add(new deliveryTm(
                    dto.getDeliveryId(),
                    dto.getDeliveryDate(),
                    dto.getDeliveryDescription(),
                    dto.getDeliveryCost(),
                    dto.getCustomerID()
            ));
        }
        tableDelivery.setItems(observableList);
    }

    public void loadUserNames() {
        try{
            List<String> id = CustomerModel.getCustomerID();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : id){
                obList.add(un);
            }
            comCustomerID.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
    }

    private boolean validateDelivery() {
        String idText = txtdeliveryID.getText();
        boolean isDeliveryIDValidated = Pattern.matches("[D][0-9]{3,}", idText);
        if (!isDeliveryIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Delivery ID!").show();
            return false;
        }
        return true;
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
        String id = txtdeliveryID.getText();
        String date = String.valueOf(txtDate.getValue());
        double cost = Double.parseDouble(txtdeliveryCost.getText());

        deliveryDto dto = new deliveryDto(
                id,
                date,
                txtdeliverydescription.getText(),
                cost,
                comCustomerID.getValue()
        );

        try {
            boolean isUpdated = DeliveryModel.updateDelivery(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
                getAllDelivery();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearFields();
    }

    public void buttonOnActionBack(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Order Details");
        stage.show();

    }

    public void buttonOnActionDelete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DeliveryModel.deleteDelivery(id);
        getAllDelivery();
        clearFields();
    }
    String id = "";
    public void tableDeliveryOnmouseClicked(MouseEvent mouseEvent) {
        Integer index = tableDelivery.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id = tableDelivery.getItems().get(index).getDeliveryId();
        txtdeliveryID.setText(clmdeliveryId.getCellData(index).toString());
        txtdeliverydescription.setText(clmdeliveryDescription.getCellData(index).toString());
        txtDate.setValue(LocalDate.parse(clmdeliverydate.getCellData(index).toString()));
        txtdeliveryCost.setText(clmdeeliverycost.getCellData(index).toString());
        comCustomerID.setValue(clmCustomerId.getCellData(index).toString());
        }

    public void clearFields() {
        txtdeliveryID.clear();
        comCustomerID.setValue("");
        txtDate.setValue(null);
        txtdeliveryCost.clear();
        txtdeliverydescription.clear();
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.DOUBLE,txtdeliveryCost))return false;
        return true;
    }

    public void txtdeliverycost(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.DOUBLE, txtdeliveryCost);
    }

    public void txtdescription(KeyEvent keyEvent) {

    }
}
