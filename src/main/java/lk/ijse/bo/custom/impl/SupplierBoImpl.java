package lk.ijse.bo.custom.impl;

import lk.ijse.bo.SupplierBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.supplierDto;
import lk.ijse.dto.tm.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBoImpl implements SupplierBo {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public boolean saveSupplier(supplierDto dto) throws SQLException, ClassNotFoundException {
        return SupplierDAO.save(new Supplier(dto.getSupplierID(),dto.getSupplierName(),dto.getSupplierAddress(),dto.getSupplierContactNumber()));
    }
    @Override
    public boolean updateSupplier(supplierDto dto) throws SQLException, ClassNotFoundException {
        return SupplierDAO.update(new Supplier(dto.getSupplierName(),dto.getSupplierAddress(),dto.getSupplierContactNumber(),dto.getSupplierID()));
    }
    @Override
    public ArrayList<supplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = SupplierDAO.getAll();
        ArrayList<supplierDto> supplierDtos = new ArrayList<>();

        for (Supplier supplier : suppliers) {
            supplierDtos.add(new supplierDto(
                    supplier.getSupplierID(),
                    supplier.getSupplierName(),
                    supplier.getSupplierAddress(),
                    supplier.getSupplierContactNumber()
            ));
        }
        return supplierDtos;
    }
}
