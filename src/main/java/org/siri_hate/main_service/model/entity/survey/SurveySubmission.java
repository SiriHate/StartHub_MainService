package org.siri_hate.main_service.model.entity.survey;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.siri_hate.main_service.model.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "survey_submissions")
public class SurveySubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private ProjectSurvey survey;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User respondent;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyAnswer> responses = new ArrayList<>();

    @Min(1)
    @Max(5)
    @Column(name = "average_rating")
    private Integer averageRating;

    public SurveySubmission() {
    }

    public SurveySubmission(
            Long id,
            ProjectSurvey survey,
            User respondent,
            LocalDateTime submittedAt,
            List<SurveyAnswer> responses,
            Integer averageRating) {
        this.id = id;
        this.survey = survey;
        this.respondent = respondent;
        this.submittedAt = submittedAt;
        this.responses = responses;
        this.averageRating = averageRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectSurvey getSurvey() {
        return survey;
    }

    public void setSurvey(ProjectSurvey survey) {
        this.survey = survey;
    }

    public User getRespondent() {
        return respondent;
    }

    public void setRespondent(User respondent) {
        this.respondent = respondent;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public List<SurveyAnswer> getResponses() {
        return responses;
    }

    public void setResponses(List<SurveyAnswer> responses) {
        this.responses = responses;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }
}
