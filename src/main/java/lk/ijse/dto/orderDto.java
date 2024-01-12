package lk.ijse.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class orderDto {
    private String orderID;
    private String orderDate;
    private String orderDescription;
    private String deliveryID;
}
