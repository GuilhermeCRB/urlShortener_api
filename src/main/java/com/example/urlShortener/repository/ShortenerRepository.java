package com.example.urlShortener.repository;

import com.example.urlShortener.model.UrlMapping;

public interface ShortenerRepository extends BaseRepository<UrlMapping, Long>  {
    boolean existsByShortCode(String shortCode);
}
