package lk.ijse.model;

import lk.ijse.db.dbconnection;
import lk.ijse.dto.employeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static List<employeeDto> getEmployees() throws SQLException, ClassNotFoundException {
        List<employeeDto> list = new ArrayList<>();
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM employee");
        ResultSet re = pstm.executeQuery();
        while (re.next()) {
            list.add(new employeeDto(
                    re.getString("employeeID"),
                    re.getString("employeeName"),
                    re.getString("employeeAddress"),
                    re.getString("employeeContactNumber")

            ));
        }
        return list;
    }

    public static boolean saveEmployee(employeeDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO employee (employeeID,employeeName,employeeAddress,employeeContactNumber) VALUES (?,?,?,?)");
        pstm.setString(1, dto.getEmployeeID());
        pstm.setString(2, dto.getEmployeeName());
        pstm.setString(3, dto.getEmployeeAddress());
        pstm.setString(4, dto.getEmployeeContactNumber());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateEmployee(employeeDto dto) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE employee SET employeeName=?, employeeAddress=?, employeeContactNumber=? WHERE  employeeID= ?");
        pstm.setString(1, dto.getEmployeeName());
        pstm.setString(2, dto.getEmployeeAddress());
        pstm.setString(3, dto.getEmployeeContactNumber());
        pstm.setString(4, dto.getEmployeeID());
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        Connection con = dbconnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM employee WHERE employeeID = ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

}
