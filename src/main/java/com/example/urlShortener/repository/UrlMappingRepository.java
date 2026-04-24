package com.example.urlShortener.repository;

import com.example.urlShortener.model.UrlMapping;

public interface UrlMappingRepository extends BaseRepository<UrlMapping, Long>  {
    boolean existsByCode(String shortCode);
}
