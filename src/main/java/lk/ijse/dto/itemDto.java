package lk.ijse.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class itemDto {
    private String itemID;
    private String itemName;
    private String itemType;
    private double unitPrice;
    private String itemSize;
    private double quantityOfItem;
}
