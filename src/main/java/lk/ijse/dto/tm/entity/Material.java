package lk.ijse.dto.tm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class Material {
    private String materialID;
    private String materialName;
    private String materialType;
    private int materialQuantity;

    public Material(){}


}
