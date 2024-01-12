package lk.ijse.controller.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.OrderDetailBo;
import lk.ijse.dto.itemDto;
import lk.ijse.dto.tm.CartTM;
import lk.ijse.dto.tm.PlaceOrrderTM;
import lk.ijse.model.ItemModel;
import lk.ijse.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderDetailsFormController{
    public TextField txtOrderId;
    public TextField txtQuantity;
    public TableView<PlaceOrrderTM> tblOrder;
    public TableColumn<?,?> colItemName;
    public TableColumn<?,?> colQut;
    public TableColumn<?,?> colUnitPrice;
    public TableColumn<?,?> colTotal;
    public TableColumn<PlaceOrrderTM,Void> colAction;
    public ComboBox<String> comItemName;
    public Label lblQutOnHand;
    public Label lblUnitPrice;
    public Label lblNetTotal;

    OrderDetailBo orderDetailBo= (OrderDetailBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ORDERDETAIL);

    private ObservableList<PlaceOrrderTM> orderTMS = FXCollections.observableArrayList();

    public void btnPlaceOrderONAtion(ActionEvent actionEvent) throws SQLException {
        String oid = txtOrderId.getText();

        List<CartTM> cartDTOList = new ArrayList<>();

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            PlaceOrrderTM tm = orderTMS.get(i);

            CartTM cartDTO = new CartTM(tm.getItemName(), tm.getQuantity(), tm.getUnitPrice(), tm.getTotal());
            cartDTOList.add(cartDTO);
        }

        boolean isPlaced = OrderModel.placeOrder(oid, cartDTOList);
        if (isPlaced) {
            new Alert(Alert.AlertType.INFORMATION, "Order Placed Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Order Placed Failed").show();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemName();
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colQut.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void loadItemName() {
        List<String> name = ItemModel.getitemNames();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : name){
            obList.add(un);
        }
        comItemName.setItems(obList);
    }

    public void ItemNameOnMouseClicked(ActionEvent actionEvent) {
        String name = comItemName.getValue();
        itemDto dto = ItemModel.getQuantity(name);
        lblQutOnHand.setText(dto.getQuantityOfItem()+"");
        lblUnitPrice.setText(dto.getUnitPrice()+"");
    }

    public void AddOnAction(ActionEvent actionEvent) {
        String name = comItemName.getValue();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = quantity * unitPrice;

        Button btnRemove = new Button("Remove");
        /*Image image = new Image("/assest/icons/bin.jpg");*/
        ImageView view = new ImageView();
        view.setFitHeight(20);
        view.setFitWidth(20);
        btnRemove.setGraphic(view);
        btnRemove.setCursor(Cursor.HAND);
        setRemoveBtnOnAction(btnRemove);

        if (!orderTMS.isEmpty()) {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if (colItemName.getCellData(i).equals(name)) {
                    quantity += (int) colQut.getCellData(i);
                    total = quantity * unitPrice;
                    orderTMS.get(i).setQuantity(quantity);
                    orderTMS.get(i).setTotal(total);
                    tblOrder.refresh();
                    calculateNetTotal();
                    return;
                }
            }
        }

        PlaceOrrderTM tm = new PlaceOrrderTM(name, quantity, unitPrice, total, btnRemove);
        orderTMS.add(tm);
        tblOrder.setItems(orderTMS);
        calculateNetTotal();
    }

    private void setRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index = tblOrder.getSelectionModel().getSelectedIndex();
                orderTMS.remove(index);
                tblOrder.refresh();
                calculateNetTotal();
            }
        });
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            double total  = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    public void buttonOnActionBack(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard/order_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Order Details");
        stage.show();
    }
}
