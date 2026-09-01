package com.techprep.service;

import com.techprep.dto.TopicDto;
import java.util.List;

public interface TopicService {
    TopicDto createTopic(Long subCategoryId, TopicDto topicDto);
    TopicDto updateTopic(Long id, TopicDto topicDto);
    void deleteTopic(Long id);
    TopicDto getTopicById(Long id);
    List<TopicDto> getTopicsBySubCategoryId(Long subCategoryId);
}
