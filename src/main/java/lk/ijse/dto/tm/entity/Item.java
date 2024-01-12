package lk.ijse.dto.tm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Item {
    private String itemID;
    private String itemName;
    private String itemType;
    private double unitPrice;
    private String itemSize;
    private double quantityOfItem;

    public Item(){}

    public Item(String itemID,String itemName,String itemType,double unitPrice,String itemSize,double quantityOfItem){
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.itemSize = itemSize;
        this.quantityOfItem = quantityOfItem;
    }
}
