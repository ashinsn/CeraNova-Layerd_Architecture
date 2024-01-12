package lk.ijse.dto.tm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class deliveryTm {
    private String deliveryId;
    private String deliveryDate;
    private String deliveryDescription;
    private double deliveryCost;
    private String customerID;
}
