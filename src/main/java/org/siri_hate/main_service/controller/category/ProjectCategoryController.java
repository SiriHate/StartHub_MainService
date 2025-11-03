package org.siri_hate.main_service.controller.category;

import jakarta.validation.constraints.Positive;
import org.siri_hate.main_service.model.dto.request.category.ProjectCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.ProjectCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.ProjectCategorySummaryResponse;
import org.siri_hate.main_service.service.ProjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/main_service/project_categories")
public class ProjectCategoryController {

    private final ProjectCategoryService projectCategoryService;

    @Autowired
    public ProjectCategoryController(ProjectCategoryService projectCategoryService) {
        this.projectCategoryService = projectCategoryService;
    }

    @PostMapping
    public ResponseEntity<String> createProjectCategory(@RequestBody ProjectCategoryRequest request) {
        projectCategoryService.createProjectCategory(request);
        return new ResponseEntity<>("Project category was successfully created!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectCategorySummaryResponse>> getAllProjectCategory() {
        List<ProjectCategorySummaryResponse> projectCategories =
                projectCategoryService.getAllProjectCategory();
        return new ResponseEntity<>(projectCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectCategoryFullResponse> getProjectCategoryById(
            @Positive @PathVariable Long id) {
        ProjectCategoryFullResponse projectCategory = projectCategoryService.getProjectCategoryById(id);
        return new ResponseEntity<>(projectCategory, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProjectCategory(
            @Positive @PathVariable Long id, @RequestBody ProjectCategoryRequest request) {
        projectCategoryService.updateProjectCategory(id, request);
        return new ResponseEntity<>("Project category was successfully updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjectCategory(@Positive @PathVariable Long id) {
        projectCategoryService.deleteProjectCategory(id);
        return new ResponseEntity<>(
                "Project category was successfully deleted!", HttpStatus.NO_CONTENT);
    }
}
