package lk.ijse.model;

import lk.ijse.db.dbconnection;
import lk.ijse.dto.itemDto;
import lk.ijse.dto.tm.CartTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ItemModel {
    public static List<itemDto> getItems() throws SQLException {
        List<itemDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM item");
        ResultSet resu = pstm.executeQuery();
        while (resu.next()) {
            list.add(new itemDto(
                    resu.getString("itemID"),
                    resu.getString("itemName"),
                    resu.getString("itemType"),
                    resu.getDouble("unitPrice"),
                    resu.getString("itemSize"),
                    resu.getDouble("quantityOfItem")

            ));
        }
        return list;
    }

    public static boolean saveItem(itemDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO item (itemID,itemName,itemType,unitPrice,itemSize,quantityOfItem) VALUES (?,?,?,?,?,?)");
        pstm.setString(1, dto.getItemID());
        pstm.setString(2, dto.getItemName());
        pstm.setString(3, dto.getItemType());
        pstm.setDouble(4, dto.getUnitPrice());
        pstm.setString(5, dto.getItemSize());
        pstm.setDouble(6, dto.getQuantityOfItem());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateItem(itemDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE item SET itemName=?,itemType=?,unitPrice=?, itemSize=?, quantityOfItem=? WHERE  itemID= ?");
        pstm.setString(1, dto.getItemName());
        pstm.setString(2, dto.getItemType());
        pstm.setDouble(3, dto.getUnitPrice());
        pstm.setString(4, dto.getItemSize());
        pstm.setDouble(5, dto.getQuantityOfItem());
        pstm.setString(6, dto.getItemID());
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteItem(String id) throws SQLException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM item WHERE itemID = ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static itemDto getItems(String id) throws SQLException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM item WHERE itemID = ?");
        pstm.setString(1, id);
        ResultSet resu = pstm.executeQuery();
        if (resu.next()) {
            return new itemDto(
                    resu.getString("ID"),
                    resu.getString("itemName"),
                    resu.getString("Type"),
                    resu.getDouble("unitPrice"),
                    resu.getString("Size"),
                    resu.getDouble("QuantityOfItem")
            );
        }
        return null;
    }

    public static List<String> getitemIds() {
        List<String> list = new ArrayList<>();
        try {
            Connection con = dbconnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT itemID FROM item");
            ResultSet resu = pstm.executeQuery();
            while (resu.next()) {
                list.add(resu.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getitemNames() {
        List<String> list = new ArrayList<>();
        try {
            Connection con = dbconnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT itemName FROM item");
            ResultSet resu = pstm.executeQuery();
            while (resu.next()) {
                list.add(resu.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static itemDto getQuantity(String name) {
        try {
            Connection con = dbconnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM item WHERE itemName = ?");
            pstm.setString(1, name);
            ResultSet resu = pstm.executeQuery();
            if (resu.next()) {
                return new itemDto(
                        resu.getString("itemID"),
                        resu.getString("itemName"),
                        resu.getString("itemType"),
                        resu.getDouble("unitPrice"),
                        resu.getString("itemSize"),
                        resu.getDouble("QuantityOfItem")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateQty(List<CartTM> cartDTOList) {
        for (CartTM cartTM : cartDTOList) {
            try {
                Connection con = dbconnection.getInstance().getConnection();
                PreparedStatement pstm = con.prepareStatement("UPDATE item SET QuantityOfItem = (QuantityOfItem - ?) WHERE itemName = ?");
                pstm.setDouble(1, cartTM.getQuantity());
                pstm.setString(2, cartTM.getItemName());
                if(pstm.executeUpdate()<=0){
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String getItemID(String itemName) {
        try {
            Connection con = dbconnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT itemID FROM item WHERE itemName = ?");
            pstm.setString(1, itemName);
            ResultSet resu = pstm.executeQuery();
            if (resu.next()) {
                return resu.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


