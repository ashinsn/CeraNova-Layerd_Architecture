package lk.ijse.dto.tm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class paymentTm {
    private String paymentID;
    private String paymentDate;
    private String paymentMethod;
    private String paymentCost;
    private String paymentTime;
    private String orderID;
    private String itemID;
    private String customerID;
}
