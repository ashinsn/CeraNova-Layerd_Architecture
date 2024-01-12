package lk.ijse.model;

import lk.ijse.db.dbconnection;
import lk.ijse.dto.deliveryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryModel {
    public static List<deliveryDto> getDelivery() throws SQLException, ClassNotFoundException {
        List<deliveryDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM delivery");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(new deliveryDto(
                    rs.getString("DeliveryID"),
                    rs.getString("DeliveryDate"),
                    rs.getString("DeliveryDescription"),
                    rs.getDouble("DeliveryCost"),
                    rs.getString("CustomerID")
            ));
        }
        return list; // Use the variable name 'list' here, not 'List'
    }


    public static boolean saveDelivery(deliveryDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO delivery (deliveryID,deliveryDate,deliveryDescription,deliveryCost,customerID) VALUES (?,?,?,?,?)");
        pstm.setString(1, dto.getDeliveryId());
        pstm.setString(2, dto.getDeliveryDate());
        pstm.setString(3, dto.getDeliveryDescription());
        pstm.setString(4, String.valueOf(dto.getDeliveryCost()));
        pstm.setString(5, dto.getCustomerID());

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateDelivery(deliveryDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE delivery SET DeliveryDate=?, DeliveryDescription=?, DeliveryCost=?, CustomerID=? WHERE DeliveryID = ?");

        pstm.setString(1, dto.getDeliveryDate());
        pstm.setString(2, dto.getDeliveryDescription());
        pstm.setString(3, String.valueOf(dto.getDeliveryCost()));
        pstm.setString(4, dto.getCustomerID());
        pstm.setString(5, dto.getDeliveryId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteDelivery(String id) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM delivery WHERE deliveryID = ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static deliveryDto getDelivery(String id) throws SQLException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM delivery WHERE deliveryid = ?");
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return new deliveryDto(
                    rs.getString("DeliveryID"),
                    rs.getString("DeliveryDate"),
                    rs.getString("DeliveryDescription"),
                    rs.getDouble("DeliveryCost"),
                    rs.getString("CustomerID")
            );
        }
        return null;
    }

}
