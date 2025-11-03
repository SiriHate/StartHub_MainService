package org.siri_hate.main_service.model.entity;

import jakarta.persistence.*;
import org.siri_hate.main_service.model.entity.category.NewsCategory;

import java.time.LocalDate;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "preview_url")
    private String previewUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private NewsCategory category;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "moderation_passed")
    private Boolean moderationPassed;

    public News() {
    }

    public News(
            String title,
            User user,
            String previewUrl,
            NewsCategory category,
            String content,
            LocalDate publicationDate,
            Boolean moderationPassed) {
        this.title = title;
        this.user = user;
        this.previewUrl = previewUrl;
        this.category = category;
        this.content = content;
        this.publicationDate = publicationDate;
        this.moderationPassed = moderationPassed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public NewsCategory getCategory() {
        return category;
    }

    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Boolean getModerationPassed() {
        return moderationPassed;
    }

    public void setModerationPassed(Boolean moderationPassed) {
        this.moderationPassed = moderationPassed;
    }
}
