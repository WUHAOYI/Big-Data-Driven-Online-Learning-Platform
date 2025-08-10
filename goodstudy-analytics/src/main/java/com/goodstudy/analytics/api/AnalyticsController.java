package com.goodstudy.analytics.api;

import com.goodstudy.analytics.model.dto.AnalyticsDto;
import com.goodstudy.analytics.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService svc;

    public AnalyticsController(AnalyticsService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<AnalyticsDto> create(@RequestParam String name) {
        AnalyticsDto dto = svc.create(name);
        return ResponseEntity.created(URI.create("/api/analytics/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyticsDto> get(@PathVariable Long id) {
        return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<AnalyticsDto> list() {
        return svc.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
