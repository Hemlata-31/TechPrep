package com.techprep.controller;

import com.techprep.dto.TopicDto;
import com.techprep.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<TopicDto> createTopic(@PathVariable Long subCategoryId, @Valid @RequestBody TopicDto topicDto) {
        return new ResponseEntity<>(topicService.createTopic(subCategoryId, topicDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> updateTopic(@PathVariable Long id, @Valid @RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicService.updateTopic(id, topicDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.ok("Topic deleted successfully.");
    }
}
