package com.example.urlShortener.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.dto.response.UrlMappingResponseDTO;
import com.example.urlShortener.model.UrlMapping;

@Component
@Mapper(componentModel = "spring")
public interface UrlMappingMapper extends BaseMapper<UrlMapping, UrlMappingRequestDTO, UrlMappingResponseDTO> {
	UrlMappingResponseDTO toDTO(UrlMapping entity);

	UrlMapping toEntity(UrlMappingRequestDTO dto);
}
