package lk.ijse.dto.tm.entity;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter

public class Employee {

    private String employeeID;
    private String employeeName;
    private String employeeAddress;
    private String employeeContactNumber;

    public Employee(){}

    public Employee(String employeeID,String employeeName,String employeeAddress,String employeeContactNumber){
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeContactNumber = employeeContactNumber;
    }
}
