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
import lk.ijse.bo.OrderBo;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.orderDto;
import lk.ijse.dto.tm.customerTm;
import lk.ijse.dto.tm.orderTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.OrderModel;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class OrderFormController {
    public TextField txtOrderID;
    public TextField txtDeliveryID;
    public TextField txtOrderdescription;
    public TableView<orderTm> tableOrder;
    public TableColumn<?, ?> colOrderID;
    public TableColumn<?, ?> colOrderdate;
    public TableColumn<?, ?> colOrderdescription;
    public TableColumn<?, ?> colDeliveryID;
    public AnchorPane root;
    public ComboBox<String> comDelivaryID;
    public DatePicker txtOrderdate;

    OrderBo orderBo= (OrderBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ORDER);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        colOrderdate.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        colOrderdescription.setCellValueFactory(new PropertyValueFactory<>("OrderDescription"));
        colDeliveryID.setCellValueFactory(new PropertyValueFactory<>("DeliveryID"));

        tableListener();
        loadDeliveyID();
        try {
            getAllOrders();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadDeliveyID() {
        try{
            List<String> id = OrderModel.getDeliveryID();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : id){
                obList.add(un);
            }
            comDelivaryID.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
    }

    private void tableListener() {

    }

    public void btnOnActionClear(ActionEvent actionEvent) {
        clearFields();
    }

    public void buttonOnActionSave(ActionEvent actionEvent) {
        String id = txtOrderID.getText();
        String date = String.valueOf(txtOrderdate.getValue());
        String description = txtOrderdescription.getText();
        String deliveryID = comDelivaryID.getValue();

        if (id.isEmpty() || date.isEmpty() || description.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Field Not found").show();
            return;
        }

        orderDto dto = new orderDto(id, date, description, deliveryID);
        try {
            boolean isSaved = OrderModel.saveOrder(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                getAllOrders();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getAllOrders() throws SQLException, ClassNotFoundException {

        ObservableList<orderTm> observableList = FXCollections.observableArrayList();
        List<orderDto> orders = OrderModel.getOrders();

        for (orderDto dto : orders) {
            observableList.add(new orderTm(
                    dto.getOrderID(),
                    dto.getOrderDate(),
                    dto.getOrderDescription(),
                    dto.getDeliveryID()
            ));
        }
        tableOrder.setItems(observableList);
    }

    private boolean validateOrder() {
        String idText = txtOrderID.getText();
        boolean isOrderIDValidated = Pattern.matches("[O][0-9]{3,}", idText);
        if (!isOrderIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Order ID!").show();
            return false;
        }
        return true;
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
        String id = txtOrderID.getText();
        String date = String.valueOf(txtOrderdate.getValue());
        String description = txtOrderdescription.getText();

        orderDto dto = new orderDto(
                id,
                date,
                description,
                comDelivaryID.getValue()
        );
        try {
            boolean isUpdated = OrderModel.updateOrder(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                getAllOrders();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnOnActionBack(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Order Details");
        stage.show();
    }

    public void btnOnActionDelete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        OrderModel.deleteOrder(id);
        getAllOrders();
        clearFields();
    }

    String id = "";

    public void tableOrderOnmouseClicked(MouseEvent mouseEvent) {
        Integer index = tableOrder.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id = tableOrder.getItems().get(index).getOrderID();
        txtOrderID.setText(colOrderID.getCellData(index).toString());
        txtOrderdescription.setText(colOrderdescription.getCellData(index).toString());
        txtOrderdate.setValue(LocalDate.parse(colOrderdate.getCellData(index).toString()));
        comDelivaryID.setValue(colDeliveryID.getCellData(index).toString());
    }
    public void clearFields() {
        txtOrderID.clear();
        txtOrderdate.setValue(null);
        txtOrderdescription.clear();
    }


    public void buttonOnActionPlaceOrder(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard/orderdetails_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Order Details");
        stage.show();
    }

    public void txtorderdescription(KeyEvent keyEvent) {

    }
}








