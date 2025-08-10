package com.goodstudy.learning.api;

import com.goodstudy.learning.model.dto.LearningDto;
import com.goodstudy.learning.model.dto.ProgressDto;
import com.goodstudy.learning.service.LearningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/learning")
public class LearningController {

    private final LearningService svc;

    public LearningController(LearningService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<LearningDto> create(@RequestParam String learnerName, @RequestParam Long courseId) {
        LearningDto dto = svc.createLearning(learnerName, courseId);
        return ResponseEntity.created(URI.create("/api/learning/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningDto> get(@PathVariable Long id) {
        return svc.findLearningById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<LearningDto> list() {
        return svc.findAllLearnings();
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<ProgressDto> updateProgress(@PathVariable Long id, @RequestParam int percentage) {
        return ResponseEntity.ok(svc.updateProgress(id, percentage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteLearning(id);
        return ResponseEntity.noContent().build();
    }
}
