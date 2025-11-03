package org.siri_hate.main_service.model.entity.category;

import jakarta.persistence.*;

@Entity
@Table(name = "project_categories")
public class ProjectCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public ProjectCategory() {
    }

    public ProjectCategory(Long id, String name) {
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
        ProjectCategory projectCategory = (ProjectCategory) o;
        return id.equals(projectCategory.id) && name.equals(projectCategory.name);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
