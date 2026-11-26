package com.techprep.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDto {
    private Long id;
    private Long topicId;
    private String topicName;
    private String content;
    private LocalDateTime updatedAt;
}

