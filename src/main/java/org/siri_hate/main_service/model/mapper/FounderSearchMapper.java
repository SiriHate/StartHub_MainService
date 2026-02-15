package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.FounderSearchFullResponseDTO;
import org.siri_hate.main_service.dto.FounderSearchRequestDTO;
import org.siri_hate.main_service.model.entity.project.search.FounderSearch;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FounderSearchMapper {
    FounderSearch toEntity(FounderSearchRequestDTO request);

    void updateEntity(FounderSearchRequestDTO request, @MappingTarget FounderSearch entity);

    FounderSearchFullResponseDTO toFullResponse(FounderSearch entity);

    List<FounderSearchFullResponseDTO> toFullResponseList(List<FounderSearch> entities);
}
