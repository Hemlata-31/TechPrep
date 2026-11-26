package com.techprep.service.impl;

import com.techprep.dto.NoteDto;
import com.techprep.entity.Note;
import com.techprep.entity.Topic;
import com.techprep.entity.User;
import com.techprep.repository.NoteRepository;
import com.techprep.repository.TopicRepository;
import com.techprep.repository.UserRepository;
import com.techprep.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Override
    @Transactional
    public NoteDto saveOrUpdateNote(Long topicId, String content, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + topicId));

        Note note = noteRepository.findByUserIdAndTopicId(user.getId(), topicId)
                .orElse(Note.builder().user(user).topic(topic).build());

        note.setContent(content);
        Note saved = noteRepository.save(note);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NoteDto> getNoteForTopic(Long topicId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return noteRepository.findByUserIdAndTopicId(user.getId(), topicId)
                .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteDto> getAllNotesForUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return noteRepository.findByUserIdOrderByUpdatedAtDesc(user.getId())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private NoteDto toDto(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .topicId(note.getTopic().getId())
                .topicName(note.getTopic().getName())
                .content(note.getContent())
                .updatedAt(note.getUpdatedAt())
                .build();
    }
}

