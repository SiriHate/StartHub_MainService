package org.siri_hate.main_service.model.dto.request.survey;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class SurveySubmissionRequest {

    @Valid
    @NotEmpty(message = "Список ответов не может быть пустым")
    @Size(min = 1, message = "Должен быть хотя бы один ответ")
    private List<AnswerRequest> answers;

    public List<AnswerRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerRequest> answers) {
        this.answers = answers;
    }

    public static class AnswerRequest {
        @NotNull(message = "ID вопроса обязателен")
        private Long questionId;

        @NotNull(message = "Текст ответа обязателен")
        @NotEmpty(message = "Текст ответа не может быть пустым")
        private String answerText;

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }
    }
} 