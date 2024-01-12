package lk.ijse.bo.custom.impl;

import lk.ijse.bo.MaterialBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.MaterialDAO;
import lk.ijse.dto.materialDto;
import lk.ijse.dto.tm.entity.Material;

import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialBoImpl implements MaterialBo {
    MaterialDAO materialDAO = (MaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MATERIAL);

    @Override
    public boolean saveMaterial(materialDto dto) throws SQLException, ClassNotFoundException {
        return MaterialDAO.save(new Material(dto.getMaterialID(),dto.getMaterialName(),dto.getMaterialType(),dto.getMaterialQuantity()));
    }
    @Override
    public boolean updateMaterial(materialDto dto) throws SQLException, ClassNotFoundException {
        return MaterialDAO.update(new Material(dto.getMaterialName(),dto.getMaterialType(),dto.getMaterialQuantity(),dto.getMaterialID()));
    }
    @Override
    public boolean deleteMaterial(String id) throws SQLException, ClassNotFoundException {
        return MaterialDAO.delete(id);
    }
    @Override
    public ArrayList<materialDto> getAllMaterials() throws SQLException, ClassNotFoundException {
        ArrayList<Material> materials = MaterialDAO.getAll();
        ArrayList<materialDto> materialDtos = new ArrayList<>();

        for (Material material : materials) {
            materialDtos.add(new materialDto(
                    material.getMaterialID(),
                    material.getMaterialName(),
                    material.getMaterialType(),
                    material.getMaterialQuantity()
            ));
        }
        return materialDtos;
    }
}
