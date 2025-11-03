package org.siri_hate.main_service.model.dto.request.survey;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProjectSurveyRequest {

    @NotEmpty(message = "Questions is required")
    private List<QuestionRequest> questions;

    public List<QuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequest> questions) {
        this.questions = questions;
    }

    public static class QuestionRequest {

        @NotNull(message = "The text of the question is required")
        private String questionText;

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }
    }
}
