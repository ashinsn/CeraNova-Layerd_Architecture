package lk.ijse.model;

import lk.ijse.db.dbconnection;
import lk.ijse.db.dbconnection;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.itemDto;
import lk.ijse.dto.materialDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialModel {
    public static List<materialDto> getMaterials() throws SQLException {
        List<materialDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM material");
        ResultSet resul = pstm.executeQuery();
        while (resul.next()) {
            list.add(new materialDto(
                    resul.getString("MaterialID"),
                    resul.getString("materialName"),
                    resul.getString("materialType"),
                    resul.getInt("materialQuantity")
            ));
        }
        return list;
    }

    public static boolean saveMaterial(materialDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO material (materialID, materialName, materialType, materialQuantity) VALUES (?,?,?,?)");
        pstm.setString(1, dto.getMaterialID());
        pstm.setString(2, dto.getMaterialName());
        pstm.setString(3, dto.getMaterialType());
        pstm.setInt(4, dto.getMaterialQuantity());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateMaterial(materialDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE material SET materialName=?, materialType=?, materialQuantity=? WHERE materialID = ?");
        pstm.setString(1, dto.getMaterialName());
        pstm.setString(2, dto.getMaterialType());
        pstm.setInt(3, dto.getMaterialQuantity());
        pstm.setString(4, dto.getMaterialID());
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteMaterial(String id) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM material WHERE materialID = ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }
}
