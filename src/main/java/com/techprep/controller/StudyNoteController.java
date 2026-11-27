package com.techprep.controller;

import com.techprep.dto.StudyNoteDto;
import com.techprep.entity.StudyNote;
import com.techprep.repository.StudyNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyNoteController {

    private final StudyNoteRepository studyNoteRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/study-notes")
    public ResponseEntity<List<StudyNoteDto>> getAllStudyNotes() {
        List<StudyNoteDto> list = studyNoteRepository.findAllByOrderByIdAsc().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/study-notes/{id}/pdf")
    public ResponseEntity<byte[]> downloadUploadedPdf(@PathVariable Long id) {
        StudyNote note = studyNoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study note not found"));
        if (note.getPdfBytes() == null || note.getPdfBytes().length == 0) {
            return ResponseEntity.notFound().build();
        }
        String fname = note.getFileName() != null ? note.getFileName() : "study-note.pdf";
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fname + "\"")
                .body(note.getPdfBytes());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @PostMapping(value = "/admin/study-notes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudyNoteDto> uploadStudyNote(
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "highlights", required = false) String highlights,
            @RequestParam("file") MultipartFile file) throws IOException {

        StudyNote note = StudyNote.builder()
                .title(title)
                .category(category)
                .description(description)
                .highlights(highlights)
                .fileName(file.getOriginalFilename())
                .pdfBytes(file.getBytes())
                .build();

        StudyNote saved = studyNoteRepository.save(note);
        return ResponseEntity.ok(toDto(saved));
    }

    private StudyNoteDto toDto(StudyNote n) {
        boolean isUploaded = (n.getPdfUrl() == null || n.getPdfUrl().isBlank());
        String url = isUploaded ? "/api/study-notes/" + n.getId() + "/pdf" : n.getPdfUrl();
        return StudyNoteDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .category(n.getCategory())
                .description(n.getDescription())
                .highlights(n.getHighlights())
                .downloadUrl(url)
                .fileName(n.getFileName())
                .uploadedPdf(isUploaded)
                .createdAt(n.getCreatedAt())
                .build();
    }
}

