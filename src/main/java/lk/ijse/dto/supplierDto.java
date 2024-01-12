package lk.ijse.dto;
import javafx.scene.control.Alert;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class supplierDto {
    private String supplierID;
    private String supplierName;
    private String supplierAddress;
    private String supplierContactNumber;
}
