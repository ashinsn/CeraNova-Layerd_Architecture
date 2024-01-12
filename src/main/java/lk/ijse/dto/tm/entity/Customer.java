package lk.ijse.dto.tm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Customer {

    private String customerID;
    private String customerName;
    private String customerAddress;
    private String customerContactNumber;

    public Customer(){}

    public Customer(String customerID,String customerName,String customerAddress,String customerContactNumber) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerContactNumber = customerContactNumber;
    }

}

