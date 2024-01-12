package lk.ijse.dto.tm.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Delivery {

    private String deliveryId;
    private String deliveryDate;
    private String deliveryDescription;
    private double deliveryCost;
    private String customerID;

    public Delivery(){}
    public Delivery(String deliveryId,String deliveryDate,String deliveryDescription,String deliveryCost,String customerID){
        this.deliveryId = deliveryId;
        this.deliveryDate = deliveryDate;
        this.deliveryDescription = deliveryDescription;
        this.deliveryCost = Double.parseDouble(deliveryCost);
        this.customerID = customerID;
    }
}
