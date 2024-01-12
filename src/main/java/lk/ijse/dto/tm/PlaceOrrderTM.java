package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PlaceOrrderTM {
    private String itemName;
    private int quantity;
    private double unitPrice;
    private double total;
    private Button btn;
}
