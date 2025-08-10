package com.goodstudy.learning.controller;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.service.LearningAdvancedService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/learning/advanced")
public class LearningAdvancedController {

    private final LearningAdvancedService svc;

    public LearningAdvancedController(LearningAdvancedService svc) {
        this.svc = svc;
    }

    @PostMapping("/{id}/transfer/{toCourseId}")
    public ResponseEntity<LearningDto> transfer(@PathVariable Long id, @PathVariable Long toCourseId) {
        return ResponseEntity.ok(svc.transferEnrollment(id, toCourseId));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id) {
        svc.withdraw(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-learner/{name}")
    public ResponseEntity<List<LearningDto>> byLearner(@PathVariable String name) {
        return ResponseEntity.ok(svc.findByLearner(name));
    }
}
