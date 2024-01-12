/*package lk.ijse.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dto.employeeDto;
import lk.ijse.dto.tm.employeeTm;
import lk.ijse.model.EmployeeModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeeFormController  implements Initializable {
    public TextField txtemployeeID;
    public TextField txtsalaryId;
    public TextField txtemployeecontactnum;
    public TextField txtemployeeAddress;
    public TextField txtemployeeName;
    public TableView tableEmployee;
    public TableColumn colempID;
    public TableColumn colempName;
    public TableColumn colempAddress;
    public TableColumn colempContact;
    public TableColumn colsalaryId;

    public JFXButton test;
    public AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colempID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colempName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colempAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colempContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        tableListener();
        try {

            getAllEmployees();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableListener() {
    }

    public void buttonOnActionClear(ActionEvent actionEvent) {
        clearFeilds();

    }
    private void clearFeilds() {
        txtemployeeID.setText("");
        txtemployeeName.setText("");
        txtemployeeAddress.setText("");
        txtemployeecontactnum.setText("");
    }

    public void buttonOnActionSave(ActionEvent actionEvent) {
        String id = txtemployeeID.getText();
        String name = txtemployeeName.getText();
        String address = txtemployeeAddress.getText();
        String contact = txtemployeecontactnum.getText();
        if (id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Field Not found").showAndWait();
            return;
        }
        employeeDto dto = new employeeDto(
                id,
                name,
                address,
                contact
        );
        try {
            boolean isSaved = EmployeeModel.saveEmployee(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Success").showAndWait();
                getAllEmployees();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
        String id = txtemployeeID.getText();
        String name = txtemployeeName.getText();
        String address = txtemployeeAddress.getText();
        String tel = txtemployeecontactnum.getText();

        var dto = new employeeDto(id, name, address, tel);

//        var model = new EmployeeModel();
        try {
            boolean isUpdated = EmployeeModel.updateEmployee(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



    public void buttonOnActionDelete(ActionEvent actionEvent) {
    }

    public void buttonOnActionBack(ActionEvent actionEvent) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            //stage.setTitle("Payment Manage");
            stage.show();
    }
    private void getAllEmployees() throws SQLException, ClassNotFoundException {
        List<employeeDto> employees = EmployeeModel.getEmployees();
        List<employeeTm> tmList;
        tmList = new ArrayList<>();
        for(employeeDto dto: employees){
            tmList.add(new employeeTm(
                    dto.getEmployeeID(),
                    dto.getEmployeeName(),
                    dto.getEmployeeAddress(),
                    dto.getEmployeeContactNumber()
            ));
        }
        updateEmployeeTable(tmList);
    }
   public void btnSalaryOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard/salary_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Salary Management");
        stage.show();
    }

    private void updateEmployeeTable(List<employeeTm> list){
//        tableCustomer.setItems(FXCollections.observableArrayList(list));
//        tableCustomer.refresh();
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/veiw/salary_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new javafx.scene .Scene(root));
        stage.setTitle("Salary Form");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(test.getScene().getWindow());

    }
    private boolean validateEmployee() {
        String idText = txtemployeeID.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isCustomerIDValidated = Pattern.matches("[E][0-9]{3,}", idText);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee ID!").show();
            return false;
        }

        String nameText = txtemployeeName.getText();
//        boolean isCustomerNameValidated = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        boolean isCustomerNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isCustomerNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee name").show();
            return false;
        }

        String addressText = txtemployeeAddress.getText();
//        boolean isCustomerAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isCustomerAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isCustomerAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee address").show();
            return false;
        }

        String postalCodeText = txtemployeecontactnum.getText();
//        boolean isCustomerPostalCodeValidated = Pattern.compile("[0-9]{5}").matcher(postalCodeText).matches();
        boolean isCustomerPostalCodeValidated = Pattern.matches("[0-9]{10}", postalCodeText);
        if (!isCustomerPostalCodeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee contact").show();
            return false;
        }
        return true;

    }


    public void setTableData() throws SQLException {
        //tblCustomer.setItems(FXCollections.observableArrayList(CusModel.getAllCustomers().stream().map(e -> new CustomerTM(e.getId(), e.getName(), e.getAddress(), e.getTel(), new Button())).collect(Collectors.toList()))); ;
        List<employeeTm> list = new ArrayList<>();
        List<employeeDto> allCustomers = EmployeeModel.getAllEmployee();
        System.out.println(allCustomers.size());
        for (employeeDto allEmployees : allEmployees) {
            employeeTm employeeTm = new employeeTm();
            employeeTm.setEmployeeID(allEmployees.getEmployeeID());
            employeeTm.setEmployeeName(allEmployees.getEmployeeName());
            employeeTm.setEmployeeAddress(allEmployees.getEmployeeAddress());
            employeeTm.setEmployeeContactNumber(allEmployees.getEmployeeContactNumber());
            Button button = new Button("Delete");
            button.setOnAction(e -> {
                try {
                    boolean isDeleted = EmployeeModel.deleteEmployee(employeeTm.getEmployeeID());

                    if(isDeleted){
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted!").show();
                    }
                } catch (SQLException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
            button.getStyleClass().add("delete-button");
            lk.ijse.dto.tm.employeeTm.setOption(button);
            list.add(employeeTm);
        }
        ObservableList<employeeTm> employeeTMS = FXCollections.observableArrayList(list);
        tableEmployee.setItems(employeeTMS);*/

package lk.ijse.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.EmployeeBo;
import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.employeeDto;
import lk.ijse.dto.tm.employeeTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.EmployeeModel;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeeFormController  {
    public TextField txtemployeeID;
    public TextField txtsalaryId;
    public TextField txtemployeecontactnum;
    public TextField txtemployeeAddress;
    public TextField txtemployeeName;
    public TableView<employeeTm> tableEmployee;
    public TableColumn<employeeTm, String> colempID;
    public TableColumn<employeeTm, String> colempName;
    public TableColumn<employeeTm, String> colempAddress;
    public TableColumn<employeeTm, String> colempContact;
    public TableColumn<employeeTm, String> colsalaryId;
    public JFXButton test;
    public AnchorPane root;

    EmployeeBo employeeBo = (EmployeeBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colempID.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("employeeID"));
        colempName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("employeeName"));
        colempAddress.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("employeeAddress"));
        colempContact.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("employeeContactNumber"));

        tableListener();
        try {
            getAllEmployees();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableListener() {
        // Add your table listener logic here if needed
    }

    public void buttonOnActionClear(ActionEvent actionEvent) {
        clearFields();
    }

   /* private void clearFields() {
        txtemployeeID.clear();
        txtemployeeName.clear();
        txtemployeeAddress.clear();
        txtemployeecontactnum.clear();
    }*/

    public void buttonOnActionSave(ActionEvent actionEvent) {
        //if (validateEmployee()) {
        String id = txtemployeeID.getText();
        String name = txtemployeeName.getText();
        String address = txtemployeeAddress.getText();
        String contact = txtemployeecontactnum.getText();

        if (id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Field Not found").showAndWait();
            return;
        }

        employeeDto dto = new employeeDto(id, name, address, contact);
        try {
            boolean isSaved = EmployeeModel.saveEmployee(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                getAllEmployees();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Duplicate entry").show();
        }
    }

    private void getAllEmployees() throws SQLException, ClassNotFoundException {
        ObservableList<employeeTm> observavleList = FXCollections.observableArrayList();
        List<employeeDto> employees = EmployeeModel.getEmployees();

        for (employeeDto dto : employees) {
            observavleList.add(new employeeTm(
                    dto.getEmployeeID(),
                    dto.getEmployeeName(),
                    dto.getEmployeeAddress(),
                    dto.getEmployeeContactNumber()
            ));
        }
        tableEmployee.setItems(observavleList);
    }

    private boolean validateEmployee() {
        String idText = txtemployeeID.getText();
        boolean isEmployeeIDValidated = Pattern.matches("[E][0-9]{3,}", idText);
        if (!isEmployeeIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
            return false;
        }

        String nameText = txtemployeeName.getText();
        boolean isEmployeeNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isEmployeeNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee name").show();
            return false;
        }

        String addressText = txtemployeeAddress.getText();
        boolean isEmployeeAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isEmployeeAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee address").show();
            return false;
        }


        String contactnumText = txtemployeecontactnum.getText();
        boolean isEmployeeContactNum = Pattern.matches("[0-9]{10}", contactnumText);
        if (!isEmployeeContactNum) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee contact").show();
            return false;
        }
        return true;
    }

    public void buttonOnActionUpdate(ActionEvent actionEvent) {
        String id = txtemployeeID.getText();
        employeeDto dto = new employeeDto(
                id,
                txtemployeeName.getText(),
                txtemployeeAddress.getText(),
                txtemployeecontactnum.getText()
        );
        try {
            boolean isUpdated = EmployeeModel.updateEmployee(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                getAllEmployees();
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
            if (!EmployeeModel.deleteEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }

        getAllEmployees();
        txtemployeeAddress.setText("");
        txtemployeeName.setText("");
        txtemployeeID.setText("");
        txtemployeecontactnum.setText("");

    }

    public void btnReportOnAction(ActionEvent actionEvent) throws SQLException, JRException {

    }

    String id = "";

    public void tableEmployeeOnmouseClicked(MouseEvent mouseEvent) {
        Integer index = tableEmployee.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id = tableEmployee.getItems().get(index).getEmployeeID();
        txtemployeeID.setText(colempID.getCellData(index).toString());
        txtemployeecontactnum.setText(colempContact.getCellData(index).toString());
        txtemployeeName.setText(colempName.getCellData(index).toString());
        txtemployeeAddress.setText(colempAddress.getCellData(index).toString());
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard/salary_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Salary Management");
        stage.show();
    }

    private void updateEmployeeTable(List<employeeTm> list) {
        ObservableList<employeeTm> employeeTMS = FXCollections.observableArrayList(list);
        tableEmployee.setItems(employeeTMS);
    }
    public void clearFields() {
        txtemployeeID.clear();
        txtemployeeName.clear();
        txtemployeeAddress.clear();
        txtemployeecontactnum.clear();

    }

    public void btnBackOnAction(ActionEvent actionEvent) {
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtemployeeName))return false;
        if (!Regex.setTextColor(TextFilds.ADDRESS,txtemployeeAddress))return false;
        if (!Regex.setTextColor(TextFilds.INT,txtemployeecontactnum))return false;
        return true;
    }

    public void txtemployeecontactnumber(KeyEvent keyEvent) { Regex.setTextColor(TextFilds.INT, txtemployeecontactnum);}

    public void txtemployeename(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME, txtemployeeName);
    }

    public void txtemployeeaddress(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.ADDRESS, txtemployeeAddress);
    }

}






