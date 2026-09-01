package com.techprep.service;

import com.techprep.dto.SubCategoryDto;
import java.util.List;

public interface SubCategoryService {
    SubCategoryDto createSubCategory(Long categoryId, SubCategoryDto subCategoryDto);
    SubCategoryDto updateSubCategory(Long id, SubCategoryDto subCategoryDto);
    void deleteSubCategory(Long id);
    SubCategoryDto getSubCategoryById(Long id);
    List<SubCategoryDto> getSubCategoriesByCategoryId(Long categoryId);
}
