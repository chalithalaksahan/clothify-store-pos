package service.custom;

import dto.CategoryDTO;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    boolean creatCategory(CategoryDTO categoryDTO);
    boolean updateCategory(CategoryDTO categoryDTO) throws SQLException;
    boolean deleteCategory(String categoryCode);
    CategoryDTO searchCategory(String categoryCode);
    List<CategoryDTO> getAllCategories() throws SQLException;

}
