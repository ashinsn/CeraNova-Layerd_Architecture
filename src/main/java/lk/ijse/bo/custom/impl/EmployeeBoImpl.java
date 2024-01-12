package lk.ijse.bo.custom.impl;

import lk.ijse.bo.EmployeeBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dto.employeeDto;
import lk.ijse.dto.tm.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBoImpl implements EmployeeBo {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean saveEmployee(employeeDto dto) throws SQLException, ClassNotFoundException {
        return EmployeeDAO.save(new Employee(dto.getEmployeeID(),dto.getEmployeeName(),dto.getEmployeeAddress(),dto.getEmployeeContactNumber()));
    }
    @Override
    public boolean updateEmployee(employeeDto dto) throws SQLException, ClassNotFoundException {
        return EmployeeDAO.update(new Employee(dto.getEmployeeName(),dto.getEmployeeAddress(),dto.getEmployeeContactNumber(),dto.getDeliveryID()));
    }
    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return EmployeeDAO.delete(id);
    }
    public ArrayList<employeeDto> getAllEmployee() throws SQLException, ClassNotFoundException{
        ArrayList<Employee> employees = EmployeeDAO.getAll();
        ArrayList<employeeDto> employeeDtos = new ArrayList<>();

        for (Employee employee : employees) {
            employeeDtos.add(new employeeDto(
                    employee.getEmployeeID(),
                    employee.getEmployeeName(),
                    employee.getEmployeeAddress(),
                    employee.getEmployeeContactNumber()
            ));
        }
        return employeeDtos;
    }

}
