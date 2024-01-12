package lk.ijse.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter

public class employeeDto {
    private String employeeID;
    private String employeeName;
    private String employeeAddress;
    private String employeeContactNumber;
}
