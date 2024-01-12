package lk.ijse.dto.tm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Order {
    private String orderID;
    private String orderDate;
    private String orderDescription;
    private String deliveryID;

}
