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
    public boolean updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryRepository.update(category);
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
