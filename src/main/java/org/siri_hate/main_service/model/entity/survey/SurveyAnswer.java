package org.siri_hate.main_service.model.entity.survey;

import jakarta.persistence.*;

@Entity
@Table(name = "survey_answers")
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    private SurveySubmission submission;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private SurveyQuestion question;

    @Column(name = "response_text", nullable = false)
    private String responseText;

    public SurveyAnswer() {
    }

    public SurveyAnswer(
            Long id, SurveySubmission submission, SurveyQuestion question, String responseText) {
        this.id = id;
        this.submission = submission;
        this.question = question;
        this.responseText = responseText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SurveySubmission getSubmission() {
        return submission;
    }

    public void setSubmission(SurveySubmission submission) {
        this.submission = submission;
    }

    public SurveyQuestion getQuestion() {
        return question;
    }

    public void setQuestion(SurveyQuestion question) {
        this.question = question;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
