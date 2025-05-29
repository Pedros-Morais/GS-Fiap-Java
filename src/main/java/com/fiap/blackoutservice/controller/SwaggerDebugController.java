package com.fiap.blackoutservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@Hidden
public class SwaggerDebugController {

    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerPath;
    
    @GetMapping("/api-docs-redirect")
    public RedirectView redirectToApiDocs() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
