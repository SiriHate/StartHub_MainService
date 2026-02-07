package org.siri_hate.main_service.model.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.siri_hate.main_service.model.entity.Comment;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.model.entity.survey.ProjectSurvey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectMember> members = new HashSet<>();

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "image_key")
    private String imageKey;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProjectCategory category;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectLike> projectLikes = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "moderation_passed")
    private Boolean moderationPassed;

    @JsonIgnore
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProjectSurvey survey;

    public Project() {
        this.moderationPassed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<ProjectMember> getMembers() {
        return members;
    }

    public void setMembers(Set<ProjectMember> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public ProjectCategory getCategory() {
        return category;
    }

    public void setCategory(ProjectCategory category) {
        this.category = category;
    }

    public List<ProjectLike> getProjectLikes() {
        return projectLikes;
    }

    public void setProjectLikes(List<ProjectLike> projectLikes) {
        this.projectLikes = projectLikes;
    }

    public void addLike(ProjectLike like) {
        projectLikes.add(like);
    }

    public Boolean getModerationPassed() {
        return moderationPassed;
    }

    public void setModerationPassed(Boolean moderationPassed) {
        this.moderationPassed = moderationPassed;
    }

    public ProjectSurvey getSurvey() {
        return survey;
    }

    public void setSurvey(ProjectSurvey survey) {
        this.survey = survey;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
