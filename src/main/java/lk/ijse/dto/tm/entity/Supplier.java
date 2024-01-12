package lk.ijse.dto.tm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Supplier {
    private String supplierID;
    private String supplierName;
    private String supplierAddress;
    private String supplierContactNumber;
}
