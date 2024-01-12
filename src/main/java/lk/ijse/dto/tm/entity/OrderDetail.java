package lk.ijse.dto.tm.entity;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class OrderDetail {
    private String itemName;
    private int quantity;
    private double unitPrice;
    private double total;
    private Button btn;
}
