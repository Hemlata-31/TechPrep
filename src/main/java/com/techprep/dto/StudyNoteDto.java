package com.techprep.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudyNoteDto {
    private Long id;
    private String title;
    private String category;
    private String description;
    private String highlights;
    private String downloadUrl;
    private String fileName;
    private boolean uploadedPdf;
    private LocalDateTime createdAt;
}

