/*package lk.ijse.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MaterialFormController {

    public AnchorPane root;
    public TextField txtmaterialID;
    public TextField txtmaterialquantity;
    public TextField txtmaterialtype;
    public TextField txtmaterialName;
    public TableView tableMaterial;
    public TableColumn colmaterialID;
    public TableColumn colmatName;
    public TableColumn colmatType;
    public TableColumn colmatQuantity;

    public void buttonOnActionBack(ActionEvent actionEvent) {
    }

    public void buttonOnActionClear(ActionEvent actionEvent) {
    }

    public void buttonOnActionDelete(ActionEvent actionEvent) {
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
import lk.ijse.bo.MaterialBo;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.materialDto;
import lk.ijse.dto.tm.customerTm;
import lk.ijse.dto.tm.materialTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.MaterialModel;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MaterialFormController {
    public AnchorPane root;
    public TextField txtmaterialID;
    public TextField txtmaterialquantity;
    public TextField txtmaterialtype;
    public TextField txtmaterialName;
    public TableView<materialTm> tableMaterial;
    public TableColumn<?,?> colmaterialID;
    public TableColumn<?,?> colmatName;
    public TableColumn<?,?> colmatType;
    public TableColumn<?,?> colmatQuantity;
    public ComboBox<String> commaterialtype;

  MaterialBo materialBo= (MaterialBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MATERIAL);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colmaterialID.setCellValueFactory(new PropertyValueFactory<>("materialID"));
        colmatName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colmatType.setCellValueFactory(new PropertyValueFactory<>("materialType"));
        colmatQuantity.setCellValueFactory(new PropertyValueFactory<>("materialQuantity"));

        ObservableList<String> list2 = FXCollections.observableArrayList("Select Type","Ball Clay","Fire Clay","Stoneware Clay","Earthenware Clay");
        commaterialtype.setItems(list2);
        commaterialtype.getSelectionModel().select("Select Type");


        tableListener();
        try {
            getAllMaterials();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void tableListener(){

    }

    public void buttonOnActionClear(ActionEvent actionEvent) {
        clearFields();
    }

    public void buttonOnActionSave(ActionEvent actionEvent) {
        String id = txtmaterialID.getText();
        String name = txtmaterialName.getText();
        String type = commaterialtype.getValue();
        int quantity = Integer.parseInt(txtmaterialquantity.getText());

        if (id.isEmpty() || name.isEmpty() || type.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Field Not found").showAndWait();
            return;
        }
        materialDto dto = new materialDto(id, name, type, quantity);
        try {
            boolean isSaved = MaterialModel.saveMaterial(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
                getAllMaterials();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getAllMaterials() throws SQLException, ClassNotFoundException {
        ObservableList<materialTm> observableList = FXCollections.observableArrayList();
        List<materialDto> materials = MaterialModel.getMaterials();

        for (materialDto dto : materials) {
            observableList.add(new materialTm(
                    dto.getMaterialID(),
                    dto.getMaterialName(),
                    dto.getMaterialType(),
                    dto.getMaterialQuantity()
            ));
        }
        tableMaterial.setItems(observableList);
    }

    private boolean validateMaterial(){
        String idText = txtmaterialID.getText();
        boolean isMaterialIDValidated = Pattern.matches("[M][0-9]{3,}", idText);
        if (!isMaterialIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Material ID!").show();
            return false;
        }
        return true;
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
        String id = txtmaterialID.getText();
        int qut = Integer.parseInt(txtmaterialquantity.getText());

        materialDto dto = new materialDto(
                id,
                txtmaterialName.getText(),
                commaterialtype.getValue(),
                qut
        );
        try {
            boolean isUpdated = MaterialModel.updateMaterial(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                getAllMaterials();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
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
            if (!MaterialModel.deleteMaterial(id)) {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }
        //MaterialModel.deleteMaterial(id);
        getAllMaterials();
        txtmaterialName.setText("");
        txtmaterialquantity.setText("");
        txtmaterialID.setText("");
    }
    String id = "";

    //////////////////////////////////////

    public void clearFields() {
        txtmaterialID.clear();
        txtmaterialquantity.clear();
        txtmaterialName.clear();
    }


    public void tableMaterialOnmouseClicked(MouseEvent mouseEvent) {
        Integer index = tableMaterial.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id = tableMaterial.getItems().get(index).getMaterialID();
        txtmaterialID.setText(colmaterialID.getCellData(index).toString());
        txtmaterialName.setText(colmatName.getCellData(index).toString());
        commaterialtype.setValue(colmatType.getCellData(index).toString());
        txtmaterialquantity.setText(colmatQuantity.getCellData(index).toString());
    }

    public boolean isValidate(){
        if (!Regex.setTextColor(TextFilds.INT, txtmaterialquantity)) return false;
        if (!Regex.setTextColor(TextFilds.NAME, txtmaterialName)) return false;
        return true;
    }

    public void txtmaterialqty(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.INT, txtmaterialquantity);
    }

    public void txtmaterialname(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME, txtmaterialName);
    }
}

