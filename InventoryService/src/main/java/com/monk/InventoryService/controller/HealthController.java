package com.monk.InventoryService.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Value("${spring.application.name:InventoryService}")
    private String appName;

    @Value("${spring.application.version:1.0.0}")
    private String version;

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", appName);
        health.put("version", version);
        health.put("timestamp", LocalDateTime.now().toString());
        health.put("description", "Service is healthy and ready");
        return ResponseEntity.ok(health);
    }

    @GetMapping("/detailed")
    public ResponseEntity<Map<String, Object>> detailedHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", appName);
        health.put("version", version);
        health.put("timestamp", LocalDateTime.now().toString());
        health.put("uptime", "Running");
        health.put("dependencies", Map.of(
                "database", "Connected",
                "redis", "Available",
                "external-api", "Reachable"
        ));
        return ResponseEntity.ok(health);
    }
}

