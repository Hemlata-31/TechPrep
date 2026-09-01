package com.techprep.service.impl;

import com.techprep.dto.TopicDto;
import com.techprep.entity.SubCategory;
import com.techprep.entity.Topic;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.SubCategoryRepository;
import com.techprep.repository.TopicRepository;
import com.techprep.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public TopicDto createTopic(Long subCategoryId, TopicDto topicDto) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory", "id", subCategoryId));

        Topic topic = new Topic();
        topic.setName(topicDto.getName());
        topic.setDescription(topicDto.getDescription());
        topic.setSubCategory(subCategory);

        Topic saved = topicRepository.save(topic);
        return mapToDto(saved);
    }

    @Override
    public TopicDto updateTopic(Long id, TopicDto topicDto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", id));

        topic.setName(topicDto.getName());
        topic.setDescription(topicDto.getDescription());

        Topic updated = topicRepository.save(topic);
        return mapToDto(updated);
    }

    @Override
    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", id));
        topicRepository.delete(topic);
    }

    @Override
    public TopicDto getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", id));
        return mapToDto(topic);
    }

    @Override
    public List<TopicDto> getTopicsBySubCategoryId(Long subCategoryId) {
        if (!subCategoryRepository.existsById(subCategoryId)) {
            throw new ResourceNotFoundException("SubCategory", "id", subCategoryId);
        }
        return topicRepository.findBySubCategoryId(subCategoryId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private TopicDto mapToDto(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setDescription(topic.getDescription());
        dto.setSubCategoryId(topic.getSubCategory().getId());
        dto.setCreatedAt(topic.getCreatedAt());
        dto.setUpdatedAt(topic.getUpdatedAt());
        return dto;
    }
}
