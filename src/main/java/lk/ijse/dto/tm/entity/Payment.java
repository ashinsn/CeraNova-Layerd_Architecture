package lk.ijse.dto.tm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Payment {
    private String paymentID;
    private String paymentDate;
    private String paymentMethod;
    private String paymentCost;
    private String paymentTime;
    private String orderID;
    private String itemID;
    private String customerID;
}
