package service.custom.impl;

import Entity.Category;
import mapper.CategoryMapper;
import dto.CategoryDTO;
import jakarta.inject.Inject;
import repository.custom.CategoryRepository;
import service.custom.CategoryService;

import java.sql.SQLException;
import java.util.List;


public class CategoryServiceImpl implements CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    CategoryMapper categoryMapper;

    @Override
    public boolean creatCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryRepository.create(category);
    }

    @Override
    public boolean updateCategory(CategoryDTO categoryDTO) throws SQLException {
        // 1. FETCH the existing category from the database using the unique code
        // This pulls the object WITH its hidden @Id intact!
        Category existingCategory = categoryRepository.getById(categoryDTO.getCategoryCode());

        if (existingCategory == null) {
            System.out.println("Update failed: Category not found.");
            return false;
        }

        // 2. OVERWRITE ONLY the fields you want to change
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        existingCategory.setDescription(categoryDTO.getDescription());
        existingCategory.setParentCategory(categoryDTO.getParentCategory());
        existingCategory.setStatus(categoryDTO.getStatus());

        // Notice we do NOT touch the categoryCode or the @Id!

        // 3. Send the fully loaded object to your fixed repository method
        // Because this object has its @Id, Hibernate will know to run an UPDATE, not an INSERT.
        return categoryRepository.update(existingCategory);
    }

    @Override
    public boolean deleteCategory(String categoryCode) {
        return categoryRepository.deleteById(categoryCode);
    }

    @Override
    public CategoryDTO searchCategory(String categoryCode) throws SQLException {
        Category category = categoryRepository.getById(categoryCode);
        return categoryMapper.toDto(category);

    }

    @Override
    public List<CategoryDTO> getAllCategories() throws SQLException {
        List<Category> categories = categoryRepository.getAll();
        return categoryMapper.toDtoList(categories);

    }
}
