package lk.ijse.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class deliveryDto {
    private String deliveryId;
    private String deliveryDate;
    private String deliveryDescription;
    private double deliveryCost;
    private String customerID;

}
