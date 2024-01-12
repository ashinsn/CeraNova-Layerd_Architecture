package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class DashboardFormController{
    public AnchorPane ControlArea;
    public JFXButton txtDashboard;
    public Label labeldate;
    public Label labeltime;
    @FXML
    private AnchorPane root;

    public void initialize() throws IOException {
        labeldate.setText(String.valueOf(LocalDate.now()));
        TimeNow();
    }
    public void buttonOnActionCustomer(ActionEvent actionEvent) throws IOException {
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/customer_form.fxml")));
    }

    public void buttonOnActionDelivery(ActionEvent actionEvent) throws IOException {
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/delivery_form.fxml")));
    }

    public void buttonOnActionOrder(ActionEvent actionEvent) throws IOException{
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/order_form.fxml")));

    }

    public void buttonOnActionSupplier(ActionEvent actionEvent) throws IOException {
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/supplier_form.fxml")));

    }

    public void buttonOnActionEmployee(ActionEvent actionEvent) throws IOException {
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/employee_form.fxml")));

    }

    public void buttonOnActionMaterial(ActionEvent actionEvent) throws IOException{
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/material_form.fxml")));

    }

    public void buttonOnActionItem(ActionEvent actionEvent) throws IOException{
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/items_form.fxml")));
    }

    public void buttonOnActionPayment(ActionEvent actionEvent) throws IOException{
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/payment_form.fxml")));
    }


    public void buttonOnActionOrderItem(ActionEvent actionEvent) throws IOException {
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/items_form.fxml")));
    }

    public void buttonOnActionSupplierMaterial(ActionEvent actionEvent) throws IOException {
        this.ControlArea.getChildren().clear();
        this.ControlArea.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard/suppliermaterial_form.fxml")));
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {

    }

    public void btnLogOut(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Order Details");
        stage.show();
    }

    private void TimeNow(){
        Thread thread = new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!false){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() ->{
                    labeltime.setText(timenow);
                });
            }
        });
        thread.start();
    }
}
