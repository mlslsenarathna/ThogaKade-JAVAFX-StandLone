package model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private Double UnitPrice;
    private Integer qtyOnHand;



}
