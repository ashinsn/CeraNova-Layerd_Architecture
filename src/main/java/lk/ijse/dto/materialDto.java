package lk.ijse.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class materialDto {
    private String materialID;
    private String materialName;
    private String materialType;
    private int materialQuantity;

}
