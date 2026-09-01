package com.techprep.service.impl;

import com.techprep.dto.SubCategoryDto;
import com.techprep.entity.Category;
import com.techprep.entity.SubCategory;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.CategoryRepository;
import com.techprep.repository.SubCategoryRepository;
import com.techprep.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SubCategoryDto createSubCategory(Long categoryId, SubCategoryDto subCategoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryDto.getName());
        subCategory.setDescription(subCategoryDto.getDescription());
        subCategory.setCategory(category);

        SubCategory saved = subCategoryRepository.save(subCategory);
        return mapToDto(saved);
    }

    @Override
    public SubCategoryDto updateSubCategory(Long id, SubCategoryDto subCategoryDto) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory", "id", id));

        subCategory.setName(subCategoryDto.getName());
        subCategory.setDescription(subCategoryDto.getDescription());

        SubCategory updated = subCategoryRepository.save(subCategory);
        return mapToDto(updated);
    }

    @Override
    public void deleteSubCategory(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory", "id", id));
        subCategoryRepository.delete(subCategory);
    }

    @Override
    public SubCategoryDto getSubCategoryById(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory", "id", id));
        return mapToDto(subCategory);
    }

    @Override
    public List<SubCategoryDto> getSubCategoriesByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
        return subCategoryRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SubCategoryDto mapToDto(SubCategory subCategory) {
        SubCategoryDto dto = new SubCategoryDto();
        dto.setId(subCategory.getId());
        dto.setName(subCategory.getName());
        dto.setDescription(subCategory.getDescription());
        dto.setCategoryId(subCategory.getCategory().getId());
        dto.setCreatedAt(subCategory.getCreatedAt());
        dto.setUpdatedAt(subCategory.getUpdatedAt());
        return dto;
    }
}
