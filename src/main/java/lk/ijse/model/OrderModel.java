package lk.ijse.model;

import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.orderDto;
import lk.ijse.dto.tm.CartTM;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/*import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;*/

public class OrderModel {
    public static List<orderDto> getOrder() throws SQLException, ClassNotFoundException {
        List<orderDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM order");
        ResultSet result = pstm.executeQuery();
        while (result.next()) {
            list.add(new orderDto(
                    result.getString("ID"),
                    result.getString("Date"),
                    result.getString("Description"),
                    result.getString("deliveryID")

            ));
        }
        return list;
    }

    public static boolean saveOrder(orderDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO orders (orderID,orderDate,orderDescription,deliveryID) VALUES (?,?,?,?)");
        pstm.setString(1, dto.getOrderID());
        pstm.setString(2, dto.getOrderDate());
        pstm.setString(3, dto.getOrderDescription());
        pstm.setString(4, dto.getDeliveryID());

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateOrder(orderDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE orders SET orderDate=?, orderDescription=?, deliveryID=? WHERE orderID = ?");
        pstm.setString(1, dto.getOrderDate());
        pstm.setString(2, dto.getOrderDescription());
        pstm.setString(3, dto.getDeliveryID());
        pstm.setString(4, dto.getOrderID());
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteOrder(String id) throws SQLException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM orders WHERE orderID = ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static customerDto getOrder(String id) throws SQLException, ClassNotFoundException {
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

    public static List<orderDto> getOrders() throws SQLException {
        List<orderDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM orders");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(new orderDto(
                    rs.getString("orderID"),
                    rs.getString("orderDate"),
                    rs.getString("orderDescription"),
                    rs.getString("deliveryID")

            ));
        }
        return list;
    }

    public static List<String> getOrderID() throws SQLException {
        List<String> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT orderID FROM orders");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }

    public static List<String> getDeliveryID() throws SQLException {
        List<String> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT deliveryID FROM delivery");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }

    public static List<String> getOrderIds() throws SQLException {
        List<String> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT orderID FROM orders");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }

    public static boolean placeOrder(String oid, List<CartTM> cartDTOList) throws SQLException {
        Connection con = null;
        try {
            con = dbconnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isUpdated = ItemModel.updateQty(cartDTOList);
            if (isUpdated) {
                boolean isOrderDetailSaved = OrderModel.save(oid, cartDTOList);
                if (isOrderDetailSaved) {
                    con.commit();
                    System.out.println("isOrderDetailSaved Done");
                    return true;
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }

    private static boolean save(String oid, List<CartTM> cartDTOList) throws SQLException {
        Connection connection = dbconnection.getInstance().getConnection();
        for (CartTM cartTM : cartDTOList) {
            String itemID = ItemModel.getItemID(cartTM.getItemName());
            System.out.println(itemID);
            String sql = "INSERT INTO orderdetail (orderID,quantity,unitPrice,itemID) VALUES(?, ?, ?, ?)";
            try {
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1, oid);
                pstm.setInt(2, cartTM.getQuantity());
                pstm.setDouble(3, cartTM.getUnitPrice());
                pstm.setString(4, itemID);

                if(pstm.executeUpdate()<=0){
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public static String generateNextOrderId() throws SQLException, SQLException, ClassNotFoundException {
        Connection connection = dbconnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "O00" + id;
        } else {
            return "O001";
        }
    }

    public static boolean saveOrder(String orderId, String customerId, LocalDate date) throws SQLException, ClassNotFoundException {
        Connection connection = dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, customerId);
        pstm.setDate(3, Date.valueOf(date));

        return pstm.executeUpdate() > 0;
    }

    /*public static boolean updateOrder(orderDto orderDto) throws SQLException {
        Connection connection = dbconnection.getInstance().getConnection();
    }*/



}
