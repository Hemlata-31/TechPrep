package com.techprep.controller;

import com.techprep.dto.SubCategoryDto;
import com.techprep.dto.TopicDto;
import com.techprep.service.SubCategoryService;
import com.techprep.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;
    private final TopicService topicService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<SubCategoryDto> createSubCategory(@PathVariable Long categoryId, @Valid @RequestBody SubCategoryDto subCategoryDto) {
        return new ResponseEntity<>(subCategoryService.createSubCategory(categoryId, subCategoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryDto> getSubCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryService.getSubCategoryById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SubCategoryDto> updateSubCategory(@PathVariable Long id, @Valid @RequestBody SubCategoryDto subCategoryDto) {
        return ResponseEntity.ok(subCategoryService.updateSubCategory(id, subCategoryDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubCategory(@PathVariable Long id) {
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.ok("SubCategory deleted successfully.");
    }

    @GetMapping("/{id}/topics")
    public ResponseEntity<List<TopicDto>> getTopicsBySubCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicsBySubCategoryId(id));
    }
}
