package com.techprep.service;

import com.techprep.dto.NoteDto;
import java.util.List;
import java.util.Optional;

public interface NoteService {
    NoteDto saveOrUpdateNote(Long topicId, String content, String username);
    Optional<NoteDto> getNoteForTopic(Long topicId, String username);
    List<NoteDto> getAllNotesForUser(String username);
}

