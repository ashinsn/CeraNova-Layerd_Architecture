package lk.ijse.dto.tm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class orderTm {
    private String orderID;
    private String orderDate;
    private String orderDescription;
    private String deliveryID;
}

