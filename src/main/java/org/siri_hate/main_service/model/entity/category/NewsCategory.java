package org.siri_hate.main_service.model.entity.category;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "news_categories")
public class NewsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public NewsCategory() {
    }

    public NewsCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewsCategory newsCategory = (NewsCategory) o;
        return Objects.equals(id, newsCategory.id) && Objects.equals(name, newsCategory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
