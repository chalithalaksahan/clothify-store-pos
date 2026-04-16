package dto;

import Entity.Supplier;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private String skuCode;
    private String name;
    private CategoryDTO category;
    private Supplier supplier;
    private String costPrice;
    private String sellingPrice;
    private String minQty;
    private String reOrderLvl;
    private String description;
    private String color;
    private String size;
}
