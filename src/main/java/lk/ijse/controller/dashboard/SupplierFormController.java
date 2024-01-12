package lk.ijse.controller.dashboard;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.SupplierBo;
import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.supplierDto;
import lk.ijse.dto.tm.customerTm;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.tm.supplierTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.SupplierModel;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;

public class SupplierFormController {
    public TextField txtsupplierID;
    public TextField txtsuppliercontactnumber;
    public TextField txtsupplieraddress;
    public TextField txtsuppliername;
    public TableColumn<?,?> colsupplierID;
    public TableColumn<?,?> colsuppliername;
    public TableColumn<?,?> colsupplieraddress;
    public TableColumn<?,?> colsuppliercontactnumber;
    public TableColumn<?,?> colUserID;
    public TableView<supplierTm> tableSupplier;

    public AnchorPane root;

    SupplierBo supplierBo =(SupplierBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colsupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        colsuppliername.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colsupplieraddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colsuppliercontactnumber.setCellValueFactory(new PropertyValueFactory<>("supplierContactNumber"));

        tableListener();
        try {

            getAllSuppliers();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void buttonOnActionClear(ActionEvent actionEvent) {
        clearFields();

    }

    public void buttonOnActionSave(ActionEvent actionEvent) {
        String id = txtsupplierID.getText();
        String name = txtsuppliername.getText();
        String address = txtsupplieraddress.getText();
        String contact = txtsuppliercontactnumber.getText();

        if (id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Field Not found").showAndWait();
            return;
        }

        supplierDto dto = new supplierDto(id, name, address, contact);
        try {
            boolean isSaved = SupplierModel.saveSupplier(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
                getAllSuppliers();
                clearFields();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Duplicate entry").show();
        }
    }

    private void getAllSuppliers() throws SQLException, ClassNotFoundException {
        ObservableList<supplierTm> observableList = FXCollections.observableArrayList();
        List<supplierDto> suppliers = SupplierModel.getSuppliers();

        for(supplierDto dto: suppliers){
            observableList.add(new supplierTm(
                    dto.getSupplierID(),
                    dto.getSupplierName(),
                    dto.getSupplierAddress(),
                    dto.getSupplierContactNumber()
            ));
        }
        tableSupplier.setItems(observableList);
    }

    private boolean validateSupplier() {
        String idText = txtsupplierID.getText();
        boolean isSupplierIDValidated = Pattern.matches("[S][0-9]{3,}", idText);
        if (!isSupplierIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Supplier ID!").show();
            return false;
        }

        String nameText = txtsuppliername.getText();
        boolean isSupplierNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isSupplierNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Supplier name").show();
            return false;
        }

        String addressText = txtsupplieraddress.getText();
        boolean isSupplierAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isSupplierAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Supplier address").show();
            return false;
        }

        String contactnumberText = txtsuppliercontactnumber.getText();
        boolean isSupplierContactNumberValidated = Pattern.matches("[0-9]{10}", contactnumberText);
        if (!isSupplierContactNumberValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Supplier contact number").show();
            return false;
        }
        return true;
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
        String id = txtsupplierID.getText();
        supplierDto dto = new supplierDto(
                id,
                txtsuppliername.getText(),
                txtsupplieraddress.getText(),
                txtsuppliercontactnumber.getText()
        );
        try {
            boolean isUpdated = SupplierModel.updateSupplier(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                getAllSuppliers();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Order Details");
        stage.show();
    }

    public void buttonOnActionDelete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        SupplierModel.deleteSupplier(id);
        getAllSuppliers();
        txtsupplieraddress.setText("");
        txtsuppliername.setText("");
        txtsupplierID.setText("");
        txtsuppliercontactnumber.setText("");
    }
    String id = "";
    public void tableListener(){}
    private void updateSupplierTable(List<supplierTm> list){
    }

    public void tableSupplierOnmouseClicked(MouseEvent mouseEvent) {
        Integer index = tableSupplier.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id = tableSupplier.getItems().get(index).getSupplierID();
        txtsupplierID.setText(colsupplierID.getCellData(index).toString());
        txtsuppliercontactnumber.setText(colsuppliercontactnumber.getCellData(index).toString());
        txtsuppliername.setText(colsuppliername.getCellData(index).toString());
        txtsupplieraddress.setText(colsupplieraddress.getCellData(index).toString());
    }
    public void clearFields() {
        txtsupplierID.clear();
        txtsuppliername.clear();
        txtsupplieraddress.clear();
        txtsuppliercontactnumber.clear();
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtsuppliername))return false;
        if (!Regex.setTextColor(TextFilds.ADDRESS,txtsupplieraddress))return false;
        if (!Regex.setTextColor(TextFilds.INT,txtsuppliercontactnumber))return false;
        return true;
    }

    public void txtsuppliercontactnumber(KeyEvent keyEvent) { Regex.setTextColor(TextFilds.INT, txtsuppliercontactnumber);}

    public void txtsupplieraddress(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.ADDRESS, txtsupplieraddress);
    }

    public void txtsuppliername(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME, txtsuppliername);
    }
}
