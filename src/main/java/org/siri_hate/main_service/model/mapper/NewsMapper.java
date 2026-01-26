package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.NewsFullResponseDTO;
import org.siri_hate.main_service.dto.NewsPageResponseDTO;
import org.siri_hate.main_service.dto.NewsRequestDTO;
import org.siri_hate.main_service.dto.NewsSummaryResponseDTO;
import org.siri_hate.main_service.model.entity.news.News;
import org.springframework.data.domain.Page;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NewsMapper {
    News toNews(NewsRequestDTO news);

    @Mapping(target = "category", ignore = true)
    NewsSummaryResponseDTO toNewsSummaryResponse(News news);

    @Mapping(target = "category", ignore = true)
    NewsFullResponseDTO toNewsFullResponse(News news);

    News newsUpdate(NewsRequestDTO newsFullRequest, @MappingTarget News news);

    NewsPageResponseDTO toNewsPageResponse(Page<News> newsPage);
}