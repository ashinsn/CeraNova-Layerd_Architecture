package lk.ijse.model;

import lk.ijse.db.dbconnection;
import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.paymentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    public static List<paymentDto> getPayment() throws SQLException, ClassNotFoundException {
        List<paymentDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM payment");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(new paymentDto(
                    rs.getString("paymentID"),
                    rs.getString("PaymentDate"),
                    rs.getString("paymentMethod"),
                    rs.getString("paymentCost"),
                    rs.getString("paymentTime"),
                    rs.getString("orderID"),
                    rs.getString("itemID"),
                    rs.getString("customerID")
            ));
        }
        return list;
    }

    public static boolean savePayment(paymentDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO payment (paymentID,paymentDate,paymentMethod,paymentCost,paymentTime,orderID,itemID,customerID) VALUES (?,?,?,?,?,?,?,?)");
        pstm.setString(1, dto.getPaymentID());
        pstm.setString(2, dto.getPaymentDate());
        pstm.setString(3, dto.getPaymentMethod());
        pstm.setString(4, dto.getPaymentCost());
        pstm.setString(5, dto.getPaymentTime());
        pstm.setString(6, dto.getOrderID());
        pstm.setString(7, dto.getItemID());
        pstm.setString(8, dto.getCustomerID());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updatePayment(paymentDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE payment SET paymentDate=?, paymentMethod=?,paymentCost=?,paymentTime=?, orderID=?, itemID=?, customerID=? WHERE paymentID = ?");

        pstm.setString(1, dto.getPaymentDate());
        pstm.setString(2, dto.getPaymentMethod());
        pstm.setString(3, dto.getPaymentCost());
        pstm.setString(4, dto.getPaymentTime());
        pstm.setString(5, dto.getOrderID());
        pstm.setString(6, dto.getItemID());
        pstm.setString(7, dto.getCustomerID());
        pstm.setString(8, dto.getPaymentID());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM payment WHERE paymentID = ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static paymentDto getPayment(String id) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM payment WHERE id = ?");
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return new paymentDto(
                    rs.getString("paymentID"),
                    rs.getString("PaymentDate"),
                    rs.getString("paymentMethod"),
                    rs.getString("paymentTime"),
                    rs.getString("paymentCost"),
                    rs.getString("orderID"),
                    rs.getString("itemID"),
                    rs.getString("customerID")
            );
        }
        return null;

    }
}
