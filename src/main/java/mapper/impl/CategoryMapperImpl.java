package mapper.impl;

import Entity.Category;
import mapper.CategoryMapper;
import dto.CategoryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO toDto(Category category) {
        // 1. Always check for null to prevent app crashes!
        if (category == null) {
            return null;
        }

        // 2. Create and populate the DTO
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryCode(category.getCategoryCode());
        dto.setCategoryName(category.getCategoryName());
        dto.setParentCategory(category.getParentCategory());
        dto.setStatus(category.getStatus());
        dto.setDescription(category.getDescription());

        return dto;
    }

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        // 1. Safety check
        if (categoryDTO == null) {
            return null;
        }

        // 2. Create and populate the Entity
        Category entity = new Category();
        entity.setCategoryCode(categoryDTO.getCategoryCode());
        entity.setCategoryName(categoryDTO.getCategoryName());
        entity.setParentCategory(categoryDTO.getParentCategory());
        entity.setStatus(categoryDTO.getStatus());
        entity.setDescription(categoryDTO.getDescription());

        return entity;
    }

    @Override
    public List<CategoryDTO> toDtoList(List<Category> categoryList) {
        // Return an empty list instead of null to keep your JavaFX tables happy
        if (categoryList == null || categoryList.isEmpty()) {
            return new ArrayList<>();
        }

        // Java Streams cleanly convert the whole list using your toDto method above
        return categoryList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}