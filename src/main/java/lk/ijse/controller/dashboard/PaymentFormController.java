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
import lk.ijse.bo.PaymentBo;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.paymentDto;
import lk.ijse.dto.tm.customerTm;
import lk.ijse.dto.tm.paymentTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;
import lk.ijse.model.OrderModel;
import lk.ijse.model.PaymentModel;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PaymentFormController{
    public TextField txtpaymentID;
    public TextField txtpaymentcost;
    public TextField txtpaymentmethod;
    public TextField txtpaymentdate;
    public TextField txtpaymenttime;
    public TableColumn<?,?> colpayID;
    public TableColumn<?,?> colpaydate;
    public TableColumn<?,?> cplpaymethod;
    public TableColumn<?,?> colpaymentcost;
    public TableColumn<?,?> colpaymenttime;
    public TableColumn<?,?> colItemId;
    public TableColumn<?,?> colorderID;
    public TableColumn<?,?> colitemID;

    public AnchorPane root;
    public TableView<paymentTm> tablePayment;
    public ComboBox<String> cmborderID;
    public ComboBox<String> cmbItemID;
    public ComboBox<String> cmbcustomerID;
    public TableColumn<?,?> colcustomerID;
    public DatePicker paymentDatePicker;

    PaymentBo paymentBo= (PaymentBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colpayID.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        colpaymentcost.setCellValueFactory(new PropertyValueFactory<>("paymentCost"));
        colpaydate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colpaymenttime.setCellValueFactory(new PropertyValueFactory<>("paymentTime"));
        cplpaymethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colorderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colcustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        tableListener();
        try {
            getAllPayments();
            loadCustomerID();
            loadItemID();
            loadOrderID();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void tableListener() {
    }

    public void buttonOnActionClear(ActionEvent actionEvent) {
        clearFields();
    }
    public void buttonOnActionSave(ActionEvent actionEvent) {
        String id = txtpaymentID.getText();
        String cost = txtpaymentcost.getText();
        String date = String.valueOf(paymentDatePicker.getValue());
        String time = txtpaymenttime.getText();
        String method = txtpaymentmethod.getText();
        String orderID = cmborderID.getValue();
        String itemID = cmbItemID.getValue();
        String customerID = cmbcustomerID.getValue();

        if (id.isEmpty() || cost.isEmpty() || date.isEmpty() || time.isEmpty() || method.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Field Not found").showAndWait();
            return;
        }
        paymentDto dto = new paymentDto(id, date, method, cost, time, orderID, itemID, customerID);
        try {
            boolean isSaved = PaymentModel.savePayment(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
                getAllPayments();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void getAllPayments() throws SQLException, ClassNotFoundException {
        ObservableList<paymentTm> observableList = FXCollections.observableArrayList();
        List<paymentDto> payments = PaymentModel.getPayment();

        for (paymentDto dto : payments) {
            observableList.add(new paymentTm(
                    dto.getPaymentID(),
                    dto.getPaymentDate(),
                    dto.getPaymentMethod(),
                    dto.getPaymentCost(),
                    dto.getPaymentTime(),
                    dto.getOrderID(),
                    dto.getItemID(),
                    dto.getCustomerID()
            ));
        }
        tablePayment.setItems(observableList);
    }

    private boolean validatePayment(){
        String idText = txtpaymentID.getText();
        boolean isCustomerIDValidated = Pattern.matches("[P][0-9]{3,}", idText);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }
        return true;
    }

    public void loadCustomerID() {
        try{
            List<String> id = CustomerModel.getCustomerID();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : id){
                obList.add(un);
            }
            cmbcustomerID.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
    }
    public void loadItemID() {
        List<String> id = ItemModel.getitemIds();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id){
            obList.add(un);
        }
        cmbItemID.setItems(obList);
    }
    public void loadOrderID() {
        try{
            List<String> id = OrderModel.getOrderIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : id){
                obList.add(un);
            }
            cmborderID.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
        String id = txtpaymentID.getText();
        String date = String.valueOf(paymentDatePicker.getValue());

        paymentDto dto = new paymentDto(
                id,
                date,
                txtpaymentmethod.getText(),
                txtpaymentcost.getText(),
                txtpaymenttime.getText(),
                cmborderID.getValue(),
                cmbItemID.getValue(),
                cmbcustomerID.getValue()
        );
        try {
            boolean isUpdated = PaymentModel.updatePayment(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
                getAllPayments();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        PaymentModel.deletePayment(id);
        getAllPayments();
        clearFields();
    }

    String id = "";


    public void tableCustomerOnmouseClicked(MouseEvent mouseEvent) {
        Integer index = tablePayment.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id = tablePayment.getItems().get(index).getPaymentID();
        txtpaymentID.setText(colpayID.getCellData(index).toString());
        txtpaymenttime.setText(colpaymenttime.getCellData(index).toString());
        paymentDatePicker.setValue(LocalDate.parse(colpaydate.getCellData(index).toString()));
        txtpaymentcost.setText(colpaymentcost.getCellData(index).toString());
        txtpaymentmethod.setText(cplpaymethod.getCellData(index).toString());
        cmborderID.setValue(colorderID.getCellData(index).toString());
        cmbcustomerID.setValue(colcustomerID.getCellData(index).toString());
        cmbItemID.setValue(colItemId.getCellData(index).toString());
    }

    private void clearFields(){
        txtpaymentID.clear();
        txtpaymenttime.clear();
        paymentDatePicker.setValue(null);
        txtpaymentcost.clear();
        txtpaymentmethod.clear();
    }
}

