package mapper;

import Entity.Category;
import dto.CategoryDTO;
import java.util.List;

public interface CategoryMapper {

    CategoryDTO toDto(Category category);

    Category toEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> toDtoList(List<Category> categoryList);
}