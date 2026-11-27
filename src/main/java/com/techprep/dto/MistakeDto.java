package com.techprep.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MistakeDto {
    private Long questionId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private String selectedAnswer;
    private String explanation;
    private String difficulty;
    private String topicName;
    private String categoryName;
    private String source; // PRACTICE or MOCK_TEST
    private LocalDateTime answeredAt;
}

