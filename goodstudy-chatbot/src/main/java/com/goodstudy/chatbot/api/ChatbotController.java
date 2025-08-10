package com.goodstudy.chatbot.api;

import com.goodstudy.chatbot.model.dto.ChatbotDto;
import com.goodstudy.chatbot.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final ChatbotService svc;

    public ChatbotController(ChatbotService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<ChatbotDto> create(@RequestParam String name) {
        ChatbotDto dto = svc.create(name);
        return ResponseEntity.created(URI.create("/api/chatbot/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatbotDto> get(@PathVariable Long id) {
        return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ChatbotDto> list() {
        return svc.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
