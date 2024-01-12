package lk.ijse.dto.tm;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CartTM {
    private String itemName;
    private int quantity;
    private double unitPrice;
    private double total;
}
