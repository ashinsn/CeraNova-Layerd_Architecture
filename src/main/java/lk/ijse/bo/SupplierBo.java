package lk.ijse.bo;

import lk.ijse.dto.orderDto;
import lk.ijse.dto.supplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBo {
    boolean saveSupplier(supplierDto dto) throws SQLException,ClassNotFoundException;

    boolean updateSupplier(supplierDto dto) throws SQLException,ClassNotFoundException;
    boolean deleteSupplier(String id) throws SQLException,ClassNotFoundException;
    ArrayList<supplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException;
}
