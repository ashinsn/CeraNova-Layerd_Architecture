package lk.ijse.dto.tm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class supplierTm {
    private String supplierID;
    private String supplierName;
    private String supplierAddress;
    private String supplierContactNumber;

}
