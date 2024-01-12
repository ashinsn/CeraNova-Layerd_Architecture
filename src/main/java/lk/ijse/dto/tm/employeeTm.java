package lk.ijse.dto.tm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class employeeTm {
    private String employeeID;
    private String employeeName;
    private String employeeAddress;
    private String employeeContactNumber;

}

