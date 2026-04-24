package com.example.urlShortener.unit.controller;

import com.example.urlShortener.core.security.WebSecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest
@Import(WebSecurityConfig.class)
public abstract class BaseControllerTest {}
