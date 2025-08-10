package com.goodstudy.notification.api;

import com.goodstudy.notification.model.dto.NotificationDto;
import com.goodstudy.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService svc;

    public NotificationController(NotificationService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<NotificationDto> create(@RequestParam String name) {
        NotificationDto dto = svc.create(name);
        return ResponseEntity.created(URI.create("/api/notification/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> get(@PathVariable Long id) {
        return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<NotificationDto> list() {
        return svc.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
