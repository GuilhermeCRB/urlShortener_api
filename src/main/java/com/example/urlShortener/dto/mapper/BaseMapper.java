package com.example.urlShortener.dto.mapper;

public interface BaseMapper<T, F, R> {
	R toDTO(T entity);

	T toEntity(F dto);
}
