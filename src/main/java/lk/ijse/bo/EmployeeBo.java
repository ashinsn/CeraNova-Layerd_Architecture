package lk.ijse.bo;

import lk.ijse.dto.customerDto;
import lk.ijse.dto.employeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBo extends SuperBO {
    boolean saveEmployee(employeeDto dto) throws SQLException,ClassNotFoundException;

    boolean updateEmployee(employeeDto dto) throws SQLException,ClassNotFoundException;
    boolean deleteEmployee(String id) throws SQLException,ClassNotFoundException;
    ArrayList<employeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;

}
