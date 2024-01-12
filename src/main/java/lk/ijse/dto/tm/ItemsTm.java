package lk.ijse.dto.tm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class ItemsTm {
    private String itemID;
    private String itemName;
    private String itemType;
    private double unitPrice;
    private String itemSize;
    private double quantityOfItem;
}
