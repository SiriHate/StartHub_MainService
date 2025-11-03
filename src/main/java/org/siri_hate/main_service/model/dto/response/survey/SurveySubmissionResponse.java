package org.siri_hate.main_service.model.dto.response.survey;

import java.time.LocalDateTime;
import java.util.List;

public class SurveySubmissionResponse {

    private Long id;
    private Long surveyId;
    private String respondentUsername;
    private LocalDateTime submittedAt;
    private List<AnswerResponse> answers;
    private Integer averageRating;

    public SurveySubmissionResponse() {
    }

    public SurveySubmissionResponse(
            Long id,
            Long surveyId,
            String respondentUsername,
            LocalDateTime submittedAt,
            List<AnswerResponse> answers,
            Integer averageRating) {
        this.id = id;
        this.surveyId = surveyId;
        this.respondentUsername = respondentUsername;
        this.submittedAt = submittedAt;
        this.answers = answers;
        this.averageRating = averageRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getRespondentUsername() {
        return respondentUsername;
    }

    public void setRespondentUsername(String respondentUsername) {
        this.respondentUsername = respondentUsername;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public List<AnswerResponse> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResponse> answers) {
        this.answers = answers;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public static class AnswerResponse {
        private Long id;
        private String questionText;
        private String answerText;

        public AnswerResponse() {
        }

        public AnswerResponse(Long id, String questionText, String answerText) {
            this.id = id;
            this.questionText = questionText;
            this.answerText = answerText;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }
    }
}
