package com.techprep.controller;

import com.techprep.dto.NoteDto;
import com.techprep.dto.NoteRequestDto;
import com.techprep.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    // GET /api/notes/{topicId} — fetch note for a specific topic
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{topicId}")
    public ResponseEntity<?> getNoteForTopic(
            @PathVariable Long topicId,
            Authentication authentication) {
        return noteService.getNoteForTopic(topicId, authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // PUT /api/notes/{topicId} — create or update note for a topic
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/{topicId}")
    public ResponseEntity<NoteDto> saveOrUpdateNote(
            @PathVariable Long topicId,
            @RequestBody NoteRequestDto request,
            Authentication authentication) {
        NoteDto saved = noteService.saveOrUpdateNote(topicId, request.getContent(), authentication.getName());
        return ResponseEntity.ok(saved);
    }

    // GET /api/notes — get all notes for the logged-in student
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes(Authentication authentication) {
        return ResponseEntity.ok(noteService.getAllNotesForUser(authentication.getName()));
    }
}

