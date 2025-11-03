package org.siri_hate.main_service.model.dto.response.survey;

import java.util.List;

public class ProjectSurveyResponse {
    private Long id;
    private Long projectId;
    private List<QuestionResponse> questions;

    public ProjectSurveyResponse() {
    }

    public ProjectSurveyResponse(Long id, Long projectId, List<QuestionResponse> questions) {
        this.id = id;
        this.projectId = projectId;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponse> questions) {
        this.questions = questions;
    }

    public static class QuestionResponse {
        private Long id;
        private String questionText;

        public QuestionResponse() {
        }

        public QuestionResponse(Long id, String questionText) {
            this.id = id;
            this.questionText = questionText;
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
    }
}
