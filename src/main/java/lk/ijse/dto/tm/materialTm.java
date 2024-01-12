package lk.ijse.dto.tm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class materialTm {
    private String materialID;
    private String materialName;
    private String materialType;
    private int materialQuantity;

}
