/*package lk.ijse.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ItemFormController {
    public TextField txtItemID;
    public TextField txtSize;
    public TextField txtQuantityofitem;
    public TextField txtItemtype;
    public TableColumn colItemType;
    public TableColumn colItemID;
    public TableColumn colItemsize;
    public TableColumn colQuantityofitem;
    public TextField txtsalaryId;
    public TextField txtamount;
    public TextField txtdateofissue;
    public TableView tableSalary;
    public TableColumn colsalaryId;
    public TableColumn coldateofissue;
    public TableColumn colamount;
    public TableColumn colemployeeId;

    public AnchorPane root;

    public void tableItem(SortEvent<TableView> tableViewSortEvent) {
    }

    public void buttonOnActionDelete(ActionEvent actionEvent) {
    }

    public void buttonOnActionBack(ActionEvent actionEvent) {
    }

    public void buttonOnActionClear(ActionEvent actionEvent) {
    }



    public void buttonOnActionUpdate(ActionEvent actionEvent) {
    }

    public void buttonOnActionSave(ActionEvent actionEvent) {
    }
}*/

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
import lk.ijse.bo.ItemBo;
import lk.ijse.dto.employeeDto;
import lk.ijse.dto.itemDto;
import lk.ijse.dto.tm.ItemsTm;
import lk.ijse.model.EmployeeModel;
import lk.ijse.model.ItemModel;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ItemFormController{
    public TextField txtItemID;
    public TextField txtSize;
    public TextField txtQuantityofitem;
    public TextField txtItemtype;
    public TableColumn<?,?> colItemType;
    public TableColumn<?,?> colitemID;
    public TableColumn<?,?> colType;
    public TableColumn<?,?> colSize;
    public TableColumn<?,?> colQuantityOfItem;
    public TableView <ItemsTm>tableItemsID;
    public TextField txtType;
    public TextField txtQuanity;
    public TextField txtDateOfIssue;
    public TextField txtAmount;
    public ComboBox<String> comType;
    public ComboBox<String> comSize;
    public TextField txtItemName;
    public TableColumn<?,?> colitemName;
    public TextField txtUnitPrice;
    public TableColumn<?,?> colUnitPrice;
    public AnchorPane root;

   ItemBo itemBo = (ItemBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEM);
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list1 = FXCollections.observableArrayList("Select Type","Tiles","Outdoor Ceramics","Sanitaryware","Tableware","Home Decor");
        comType.setItems(list1);
        comType.getSelectionModel().select("Select Type");

        ObservableList<String> list2 = FXCollections.observableArrayList("Select Size","Small","Medium","Large","Extra Large");
        comSize.setItems(list2);
        comSize.getSelectionModel().select("Select Size");


        colitemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colQuantityOfItem.setCellValueFactory(new PropertyValueFactory<>("quantityOfItem"));
        colType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        colitemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tableListener();
        try {
            getAllItems();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableListener(){}

    public void buttonOnActionClear(ActionEvent actionEvent) {
        clearFields();
    }

    public void buttonOnActionSave(ActionEvent actionEvent) {

    }

    private void getAllItems() throws SQLException, ClassNotFoundException{
        ObservableList<ItemsTm> observableList = FXCollections.observableArrayList();
        List<itemDto> items = ItemModel.getItems();

        for (itemDto dto : items) {
            observableList.add(new ItemsTm(
                    dto.getItemID(),
                    dto.getItemName(),
                    dto.getItemType(),
                    dto.getUnitPrice(),
                    dto.getItemSize(),
                    dto.getQuantityOfItem()
            ));
        }
        tableItemsID.setItems(observableList);
    }

    private boolean validatItem() {
        String idText = txtItemID.getText();
        boolean isCustomerIDValidated = Pattern.matches("[C][0-9]{3,}", idText);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }
        return true;
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {

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

    public void buttonOnActionDelete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{

    }
    String id = "";

    ////////////////////////////////////
    public void clearFields() {
        txtItemID.clear();
        txtQuanity.clear();
        comType.getSelectionModel().select("Select Type");
        comSize.getSelectionModel().select("Select Size");
        txtUnitPrice.clear();
    }

    ///////////////////////////////////////itemsControlller
    public void tableItem(SortEvent<TableView> tableViewSortEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnUpdateONAction(ActionEvent actionEvent) {
        String id = txtItemID.getText();
        String size = comSize.getValue();
        double quantityofitem = Double.parseDouble(txtQuanity.getText());
        String itemtype = comType.getValue();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        itemDto itemDto = new itemDto(
                id,
                txtItemName.getText(),
                itemtype,
                unitPrice,
                size,
                quantityofitem
        );
        try {
            boolean isUpdated = ItemModel.updateItem(itemDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
                clearFields();
                getAllItems();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteONAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (!ItemModel.deleteItem(id)) {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }

        getAllItems();
        clearFields();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtItemID.getText();
        String type = comType.getValue();
        String size = comSize.getValue();
        double quantityofitem = Double.parseDouble(txtQuanity.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        itemDto itemDto = new itemDto(
                id,
                txtItemName.getText(),
                type,
                unitPrice,
                size,
                quantityofitem
        );

        if (type.equals("Select Type") || size.equals("Select Size")){
            new Alert(Alert.AlertType.ERROR, "Field Not found").show();
            return;
        }
        try {
            boolean isSaved = ItemModel.saveItem(itemDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                clearFields();
                getAllItems();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tableItemsOnmouseClicked(MouseEvent mouseEvent) {
        Integer index = tableItemsID.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id = tableItemsID.getItems().get(index).getItemID();
        txtItemID.setText(tableItemsID.getItems().get(index).getItemID());
        txtQuanity.setText(tableItemsID.getItems().get(index).getQuantityOfItem()+"");
        comType.getSelectionModel().select(tableItemsID.getItems().get(index).getItemType());
        comSize.getSelectionModel().select(tableItemsID.getItems().get(index).getItemSize());
        txtItemName.setText(tableItemsID.getItems().get(index).getItemName());
        txtUnitPrice.setText(tableItemsID.getItems().get(index).getUnitPrice()+"");
    }

    public void tableItems(SortEvent<TableView> tableViewSortEvent) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) {

    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtItemName))return false;
        if (!Regex.setTextColor(TextFilds.DOUBLE,txtUnitPrice))return false;
        if (!Regex.setTextColor(TextFilds.INT,txtQuanity))return false;
        return true;
    }
    public void txtname(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME, txtItemName);
    }

    public void txtunitprice(KeyEvent keyEvent) {Regex.setTextColor(TextFilds.DOUBLE, txtUnitPrice);}

    public void txtqtyofitem(KeyEvent keyEvent) {Regex.setTextColor(TextFilds.INT, txtQuanity);}

}
