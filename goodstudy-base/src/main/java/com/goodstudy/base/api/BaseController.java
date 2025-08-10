package com.goodstudy.base.api;

import com.goodstudy.base.model.dto.BaseDto;
import com.goodstudy.base.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/base")
public class BaseController {

    private final BaseService svc;

    public BaseController(BaseService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<BaseDto> create(@RequestParam String name) {
        BaseDto dto = svc.create(name);
        return ResponseEntity.created(URI.create("/api/base/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDto> get(@PathVariable Long id) {
        return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<BaseDto> list() {
        return svc.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
