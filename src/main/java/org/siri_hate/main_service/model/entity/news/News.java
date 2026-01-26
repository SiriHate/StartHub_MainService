package org.siri_hate.main_service.model.entity.news;

import jakarta.persistence.*;
import org.siri_hate.main_service.model.entity.User;

import java.time.LocalDate;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "logo_url")
    private String logoUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private NewsCategory category;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Column(name = "created_at",  nullable = false)
    private final LocalDate createdAt;

    @Column(name = "moderation_passed",  nullable = false)
    private Boolean moderationPassed;

    public News() {
        this.createdAt = LocalDate.now();
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Boolean getModerationPassed() {
        return moderationPassed;
    }

    public void setModerationPassed(Boolean moderationPassed) {
        this.moderationPassed = moderationPassed;
    }
}
