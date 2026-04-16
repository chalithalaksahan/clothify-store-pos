package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {
    private String categoryCode;
    private String categoryName;
    private String parentCategory;
    private String status;
    private String description;
}
