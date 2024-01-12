/*package lk.ijse.controller.dashboard;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.tm.customerTm;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.collections.FXCollections;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.CustomerModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class CustomerFormController implements Initializable {
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TextField txtCustomername;
    public TextField txtCustomerid;
    public TextField txtCustomercontactnumber;

    public TableView<customerTm> tableCustomer;

    public AnchorPane root;

    public TextField txtCustomeraddress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        tableListener();
        try {

            getAllCustomers();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void buttonOnActionClear(ActionEvent actionEvent) {

    }

    public void buttonOnActionSave(ActionEvent actionEvent) {
        String id = txtCustomerid.getText();
        String name = txtCustomername.getText();
        String address = txtCustomeraddress.getText();
        String contact = txtCustomercontactnumber.getText();
        if (id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Field Not found").showAndWait();
            return;
        }
        customerDto dto = new customerDto(
                id,
                name,
                address,
                contact
        );
        try {
            boolean isSaved = customerDto.saveCustomer(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Success").showAndWait();
                getAllCustomers();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    ObservableList<customerTm> observableList;
    private void getAllCustomers() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<customerDto> customers = CustomerModel.getCustomers();

        for(customerDto dto: customers){
            observableList.add(new customerTm(
                    dto.getCustomerID(),
                    dto.getCustomerName(),
                    dto.getCustomerAddress(),
                    dto.getCustomerContactNumber()
            ));
        }
        tableCustomer.setItems(observableList);
    }



    public void buttonOnActionUpdate(ActionEvent actionEvent) {
//        tableCustomer.setItems(FXCollections.observableArrayList(list));
//        tableCustomer.refresh();
    }

    private void tableListener(){
//        tableCustomer.getSelectionModel()
//                .selectedItemProperty()
//                .addListener((observableValue, customerTm, t1) -> {
//                    txtId.setText(t1.getID());
//                    txtName.setText(t1.getName());
//                    txtAddress.setText(t1.getAddress());
//                    txtContact.setText(t1.getContact());
//                });
    }



    public void buttonOnActionDelete(ActionEvent actionEvent) {
    }

 //   private void updateCustomerTable(List<customerTm> list){
//        tableCustomer.setItems(FXCollections.observableArrayList(list));
//        tableCustomer.refresh();
  //  }
    public void buttonOnActionMakeOrder(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard/order_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Item Management");
        stage.show();
    }
    public void buttonOnActionBack(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        //stage.setTitle("");
        stage.show();
    }
    private void printCustomer() throws JRException {
        HashMap map = new HashMap();
        map.put("custId", txtCustomerid.getText());
        map.put("custAddress", txtCustomeraddress.getText());
        map.put("custContact", txtCustomercontactnumber.getText());
        map.put("custName", txtCustomername.getText());


        InputStream resourceAsStream = getClass().getResourceAsStream("../report/Customer_form.fxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport, //compiled report
                        map,
                        new JREmptyDataSource() //database connection
                );

        JasperViewer.viewReport(jasperPrint, false);
    }

    private boolean validateCustomer() {
        String idText = txtCustomerid.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isCustomerIDValidated = Pattern.matches("[C][0-9]{3,}", idText);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String nameText = txtCustomername.getText();
//        boolean isCustomerNameValidated = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        boolean isCustomerNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isCustomerNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            return false;
        }

        String addressText = txtCustomeraddress.getText();
//        boolean isCustomerAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isCustomerAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isCustomerAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }

        String postalCodeText = txtCustomercontactnumber.getText();
//        boolean isCustomerPostalCodeValidated = Pattern.compile("[0-9]{5}").matcher(postalCodeText).matches();
        boolean isCustomerPostalCodeValidated = Pattern.matches("[0-9]{10}", postalCodeText);
        if (!isCustomerPostalCodeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer contact").show();
            return false;
        }
        return true;

    }

    public void btnReportOnAction(ActionEvent actionEvent) {
    }
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        Parent root = FXMLLoader.load(getClass().getResource("/veiw/order_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new javafx.scene .Scene(root));
        stage.setTitle("Order Form");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(test.getScene().getWindow());
    }
}*/
package lk.ijse.controller.dashboard;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.CustomerBo;
import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.tm.customerTm;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.CustomerModel;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class CustomerFormController {
    public TableColumn<?, ?> colID;
    public TableColumn<?, ?> colName;
    public TableColumn<?, ?> colContact;
    public TableColumn<?, ?> colAddress;
    public TextField txtCustomername;
    public TextField txtCustomerid;
    public TextField txtCustomercontactnumber;

    public TableView<customerTm> tableCustomer;

    public AnchorPane root;
    public TableColumn<?,?> colUserID;

    @FXML
    private TextField txtuserID;

    public TextField txtCustomeraddress;
    CustomerBo customerBo= (CustomerBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("customerContactNumber"));

        tableListener();
        try {
            getAllCustomers();
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
        String id = txtCustomerid.getText();
        String name = txtCustomername.getText();
        String address = txtCustomeraddress.getText();
        String contact = txtCustomercontactnumber.getText();

        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR, "Pleace Check TextFilds !").show();
            return;
        }

        customerDto dto = new customerDto(id, name, address, contact);
        try {
            boolean isSaved = CustomerModel.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
                getAllCustomers();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Duplicate entry").show();
        }
    }


    private void getAllCustomers() throws SQLException, ClassNotFoundException {
        ObservableList<customerTm> observableList = FXCollections.observableArrayList();
        List<customerDto> customers = CustomerModel.getCustomers();

        for (customerDto dto : customers) {
            observableList.add(new customerTm(
                    dto.getCustomerID(),
                    dto.getCustomerName(),
                    dto.getCustomerAddress(),
                    dto.getCustomerContactNumber()
            ));
        }
        tableCustomer.setItems(observableList);
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR, "Pleace Check TextFilds !").show();
            return;
        }

        String id = txtCustomerid.getText();
        customerDto dto = new customerDto(
                id,
                txtCustomername.getText(),
                txtCustomeraddress.getText(),
                txtCustomercontactnumber.getText()
        );
        try {
            boolean isUpdated = CustomerModel.updateCustomer(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
                getAllCustomers();
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
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (!CustomerModel.deleteCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }

        getAllCustomers();
        txtCustomeraddress.setText("");
        txtCustomername.setText("");
        txtCustomerid.setText("");
        txtCustomercontactnumber.setText("");
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/customerReportsdemo.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                dbconnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    String id = "";

    public void tableCustomerOnouseClicked(MouseEvent mouseEvent) {
        Integer index = tableCustomer.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id = tableCustomer.getItems().get(index).getCustomerID();
        txtCustomerid.setText(colID.getCellData(index).toString());
        txtCustomercontactnumber.setText(colContact.getCellData(index).toString());
        txtCustomername.setText(colName.getCellData(index).toString());
        txtCustomeraddress.setText(colAddress.getCellData(index).toString());
    }

    public void clearFields() {
        txtCustomerid.clear();
        txtCustomername.clear();
        txtCustomeraddress.clear();
        txtCustomercontactnumber.clear();
    }
    @FXML
    void txtCustNameGoToCustAddress(ActionEvent event) {
        txtCustomeraddress.requestFocus();
    }
    @FXML
    void txtcustAddressGoToUserID(ActionEvent event) {
        txtuserID.requestFocus();
    }
    @FXML
    void txtcustomerIdGoToCustName(ActionEvent event) {
        txtCustomername.requestFocus();
    }
    @FXML
    void txtuserIDGoToCustContact(ActionEvent event) {
        txtCustomercontactnumber.requestFocus();
    }


    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtCustomername))return false;
        if (!Regex.setTextColor(TextFilds.ADDRESS,txtCustomeraddress))return false;
        if (!Regex.setTextColor(TextFilds.PHONE,txtCustomercontactnumber))return false;
        return true;
    }

    public void txtAddress(KeyEvent keyEvent) { Regex.setTextColor(TextFilds.ADDRESS, txtCustomeraddress);
    }

    public void txtcontactnumber(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.PHONE, txtCustomercontactnumber);
    }

    public void txtCustomerName(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME, txtCustomername);
    }

}



