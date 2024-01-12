package lk.ijse.model;

import lk.ijse.db.dbconnection;
import lk.ijse.dto.supplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static List<supplierDto> getSuppliers() throws SQLException {
        List<supplierDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM supplier");
        ResultSet resultset = pstm.executeQuery();
        while (resultset.next()) {
            list.add(new supplierDto(
                    resultset.getString("supplierID"),
                    resultset.getString("supplierName"),
                    resultset.getString("supplierAddress"),
                    resultset.getString("supplierContactNumber")
            ));
        }
        return list;
    }

    public static boolean saveSupplier(supplierDto dto) throws SQLException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO supplier (supplierID,supplierName,supplierAddress,supplierContactNumber) VALUES (?,?,?,?)");
        pstm.setString(1, dto.getSupplierID());
        pstm.setString(2, dto.getSupplierName());
        pstm.setString(3, dto.getSupplierAddress());
        pstm.setString(4, dto.getSupplierContactNumber());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateSupplier(supplierDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE supplier SET supplierName=?, supplierAddress=?, supplierContactNumber=? WHERE supplierID= ?");
        pstm.setString(1, dto.getSupplierName());
        pstm.setString(2, dto.getSupplierAddress());
        pstm.setString(3, dto.getSupplierContactNumber());
        pstm.setString(4, dto.getSupplierID());
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM supplier WHERE  supplierID= ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    /*public static supplierDto getSupplier(Str @Override
    public boolean saveO(orderDetailDto dto) throws SQLException, ClassNotFoundException {
        return OrderDetailDAO.save(new OrderDetail(dto.getOrderID(),dto.getOrderQuantity(),dto.getOrderPrice()));
    }*/
}
