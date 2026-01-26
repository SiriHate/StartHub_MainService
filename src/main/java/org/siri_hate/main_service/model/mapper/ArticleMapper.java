package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.ArticleFullResponseDTO;
import org.siri_hate.main_service.dto.ArticlePageResponseDTO;
import org.siri_hate.main_service.dto.ArticleRequestDTO;
import org.siri_hate.main_service.dto.ArticleSummaryResponseDTO;
import org.siri_hate.main_service.model.entity.article.Article;
import org.springframework.data.domain.Page;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ArticleMapper {
    Article toArticle(ArticleRequestDTO request);

    @Mapping(target = "category", ignore = true)
    ArticleFullResponseDTO toArticleFullResponseDTO(Article article);

    @Mapping(target = "category", ignore = true)
    ArticleSummaryResponseDTO toArticleSummaryResponseDTO(Article article);

    Article articleUpdate(ArticleRequestDTO request, @MappingTarget Article article);

    ArticlePageResponseDTO toArticlePageResponseDTO(Page<Article> articles);
}