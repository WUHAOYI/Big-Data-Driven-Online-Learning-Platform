package com.goodstudy.content.api;

import com.goodstudy.content.model.dto.ContentDto;
import com.goodstudy.content.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService svc;

    public ContentController(ContentService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<ContentDto> create(@RequestParam String name) {
        ContentDto dto = svc.create(name);
        return ResponseEntity.created(URI.create("/api/content/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDto> get(@PathVariable Long id) {
        return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ContentDto> list() {
        return svc.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
