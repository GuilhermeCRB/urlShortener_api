package com.example.urlShortener.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "URL_MAPPING")
public class UrlMapping {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private String id;

    @Column(name = "LONG_URL", nullable = false)
    private String longUrl;

    @Column(name = "SHORT_CODE", nullable = false, length = 5)
    private String shortCode;
}
