/*package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {


    public TextField txtUsername;
    public TextField txtPassword;

    public AnchorPane root;


    public void buttonOnActionLogin(ActionEvent actionEvent) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.equals("admin") && password.equals("1234")) {
            try {
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) txtUsername.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Dashboard");
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password. Please try again.").show();
        }
    }

    //@FXML
    public void initialize() {
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                buttonOnActionLogin();
            }
        });
    }


    public void buttonOnActionLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (true) {//username.equals("admin") && password.equals("1234")
            try {
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) txtUsername.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Dashboard");
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password. Please try again.").show();
        }
    }

    public void btnOnActionForgot(ActionEvent actionEvent) {
    }
}*/


package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    public TextField txtUsername;

    @FXML
    public AnchorPane root;

    @FXML
    public PasswordField txtPassword;
    public Button btnLogin;
    @FXML
    private AnchorPane loginAncour;
    public void btnLoginOnAction(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Replace the condition with a valid check for the username and password
        if (username.equals("admin") && password.equals("1234")) {
            try {
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) loginAncour.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Dashboard");
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password. Please try again.").show();
        }
    }
    @FXML
    void txtUserNameGoToPassword(ActionEvent event) {
        txtPassword.requestFocus();

    }

}



