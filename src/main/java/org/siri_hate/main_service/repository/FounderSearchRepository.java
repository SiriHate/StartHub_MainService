package org.siri_hate.main_service.repository;

import org.siri_hate.main_service.model.entity.project.search.FounderSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FounderSearchRepository extends JpaRepository<FounderSearch, Long> {
    List<FounderSearch> findByProjectId(Long projectId);
}
