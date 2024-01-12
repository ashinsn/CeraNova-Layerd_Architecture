package lk.ijse.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.customerDto;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static lk.ijse.model.CustomerModel.getCustomers;

public class CustomerOrderFormController implements Initializable {
    public AnchorPane pane;
    public Label lblOrderId;
    public Label lblOrderDate;
    public JFXComboBox<String> cmbCustomerId;
    public Label lblCustomerName;
    public JFXComboBox<String> cmbItemCode;
    public Label lblDescription;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public TextField txtQty;
    public TableView tblOrderCart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colAction;
    public JFXButton btnAddToCart;
    public Label lblNetTotal;

    public void cmbCustomerOnAction(ActionEvent actionEvent) {

    }

    public void cmbItemOnAction(ActionEvent actionEvent) {

    }

    public void txtQtyOnAction(ActionEvent actionEvent) {

    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            String nextorderID = OrderModel.generateNextOrderId();
            lblOrderId.setText(nextorderID);
            List<customerDto> customers = CustomerModel.getCustomers();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (customerDto dto : customers) {
                list.add(dto.getCustomerID());
            }
            cmbCustomerId.setItems(list);

            cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                try {
                    customerDto customer = CustomerModel.getCustomer(t1);
                    lblCustomerName.setText(customer.getCustomerName());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    /*private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        setDate();
        loadCustomerIds();
        loadItemCodes();
    }

    private void setCellValueFactory() {
        colcode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colunitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colaction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void generateNextOrderId() {
        try {
            String orderId = orderModel.generateNextOrderId();
            lblorderId.setText(orderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDto = itemModel.loadAllItems();

            for (ItemDto dto : itemDto) {
                obList.add(dto.getCode());

            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> idList = customerModel.getAllCustomer();

            for (CustomerDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }


    public void cmbItemCodeOnAction(ActionEvent actionEvent) {
        String code = cmbItemCode.getValue();
        try {
            ItemDto itemDto = itemModel.searchItem(code);
            lblqtynHand.setText(itemDto.getQtyOnHand());
            lblDescriptin.setText(itemDto.getDescription());
            lblunitPrice.setText(String.valueOf(itemDto.getUnitPrice()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void cmbCustomerIOnAction(ActionEvent actionEvent) {
        String id = cmbCustomerId.getValue();

        try {
            CustomerDto customerDto = customerModel.searchCustomer(id);
            lblcustomerName.setText(customerDto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String code = cmbItemCode.getValue();
        String description = lblDescriptin.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblunitPrice.getText());
        double tot = unitPrice * qty;
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);


        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if (colcode.getCellData(i).equals(code)) {
                    int col_qty = (int) colqty.getCellData(i);
                    qty += col_qty;
                    tot = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(tot);

                    calculateTotal();
                    tblOrder.refresh();
                    return;
                }
            }
        }
        var cartTm = new CartTm(code, description, qty, unitPrice, tot, btn);

        obList.add(cartTm);

        tblOrder.setItems(obList);
        calculateTotal();
        txtQty.clear();
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
            initialize();
            if (type.orElse(no) == yes) {
                int focusedIndex = tblOrder.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblOrder.refresh();
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            total += (double) coltotal.getCellData(i);
        }
        lblnettotal.setText(String.valueOf(total));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String orderId = lblorderId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());
        String customerId = cmbCustomerId.getValue();

        List<CartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            CartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }

        System.out.println("Place order form controller: " + cartTmList);
        var placeOrderDto = new PlaceOrderDto(orderId, date, customerId, cartTmList);
        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }

    public void btnnercustomerOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customerpage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();

    }

    public void btnbackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }
}
*/
    
}
