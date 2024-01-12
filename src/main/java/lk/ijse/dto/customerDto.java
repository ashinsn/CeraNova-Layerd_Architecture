package lk.ijse.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class customerDto {
    private String customerID;
    private String customerName;
    private String customerAddress;
    private String customerContactNumber;
}
