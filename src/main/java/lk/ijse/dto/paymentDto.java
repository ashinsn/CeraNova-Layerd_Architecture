package lk.ijse.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class paymentDto {
    private String paymentID;
    private String paymentDate;
    private String paymentMethod;
    private String paymentCost;
    private String paymentTime;
    private String orderID;
    private String itemID;
    private String customerID;
}
