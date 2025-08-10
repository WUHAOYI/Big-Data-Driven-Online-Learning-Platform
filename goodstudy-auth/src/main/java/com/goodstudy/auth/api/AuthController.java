package com.goodstudy.auth.api;

import com.goodstudy.auth.model.dto.AuthDto;
import com.goodstudy.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService svc;

    public AuthController(AuthService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<AuthDto> create(@RequestParam String name) {
        AuthDto dto = svc.create(name);
        return ResponseEntity.created(URI.create("/api/auth/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthDto> get(@PathVariable Long id) {
        return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<AuthDto> list() {
        return svc.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
