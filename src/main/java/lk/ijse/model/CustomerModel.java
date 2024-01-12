package lk.ijse.model;

import javafx.scene.control.Alert;
import lk.ijse.db.dbconnection;
import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.customerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static List<customerDto> getCustomers() throws SQLException, ClassNotFoundException {
        List<customerDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(new customerDto(
                    rs.getString("customerID"),
                    rs.getString("customerName"),
                    rs.getString("customerAddress"),
                    rs.getString("customerContactNumber")
            ));
        }
        return list;
    }

    public static boolean saveCustomer(customerDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO customer (customerID,customerName,customerAddress,customerContactNumber) VALUES (?,?,?,?)");
        pstm.setString(1, dto.getCustomerID());
        pstm.setString(2, dto.getCustomerName());
        pstm.setString(3, dto.getCustomerAddress());
        pstm.setString(4, dto.getCustomerContactNumber());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateCustomer(customerDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE customer SET customerName=?, customerAddress=?, customerContactNumber=? WHERE customerID = ?");

        pstm.setString(1, dto.getCustomerName());
        pstm.setString(2, dto.getCustomerContactNumber());
        pstm.setString(3, dto.getCustomerAddress());
        pstm.setString(4, dto.getCustomerID());
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM customer WHERE customerID = ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static customerDto getCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer WHERE id = ?");
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return new customerDto(
                    rs.getString("ID"),
                    rs.getString("Name"),
                    rs.getString("Address"),
                    rs.getString("Contact")
            );
        }
        return null;
    }

    //=================================================================================================================
    public static boolean updateCustomerSaving(customerDto dto,String userId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbconnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            Connection con = dbconnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("INSERT INTO customer (customerID,customerName,customerAddress,customerContactNumber) VALUES (?,?,?,?)");
            pstm.setString(1, dto.getCustomerID());
            pstm.setString(2, dto.getCustomerName());
            pstm.setString(3, dto.getCustomerContactNumber());
            pstm.setString(4, dto.getCustomerAddress());
            int isSaved = pstm.executeUpdate();

            PreparedStatement pstm1 = con.prepareStatement("INSERT INTO user (userID,employeeID) VALUES (?,?)");
            pstm1.setString(1, userId);
            pstm1.setString(2, dto.getCustomerID());
            int isUserUpdated = pstm1.executeUpdate();

            if (isSaved > 0 && isUserUpdated > 0) {
                connection.commit();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Customer Not Saved").show();
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }

    public static List<String> getCustomerID() throws SQLException {
        List<String> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT customerID FROM customer");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }
}

