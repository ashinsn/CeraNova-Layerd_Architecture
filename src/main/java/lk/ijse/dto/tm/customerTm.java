package lk.ijse.dto.tm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class customerTm {
    private String customerID;
    private String customerName;
    private String customerAddress;
    private String customerContactNumber;

}
