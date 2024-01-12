package lk.ijse.bo;

import lk.ijse.dto.customerDto;
import lk.ijse.dto.materialDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaterialBo extends SuperBO{
    boolean saveMaterial(materialDto dto) throws SQLException,ClassNotFoundException;

    boolean updateMaterial(materialDto dto) throws SQLException,ClassNotFoundException;
    boolean deleteMaterial(String id) throws SQLException,ClassNotFoundException;
    ArrayList<materialDto> getAllMaterials() throws SQLException, ClassNotFoundException;

}
