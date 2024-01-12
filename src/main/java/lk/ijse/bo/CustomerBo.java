package lk.ijse.bo;

import lk.ijse.dto.customerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo extends SuperBO{
    boolean saveCustomer(customerDto dto) throws SQLException,ClassNotFoundException;

    boolean updateCustomer(customerDto dto) throws SQLException,ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException,ClassNotFoundException;
    ArrayList<customerDto> getAllCustomers() throws SQLException, ClassNotFoundException;

}
