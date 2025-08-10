package com.goodstudy.learning.controller;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.service.GradeService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService svc;
    public GradeController(GradeService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<GradeDto> award(@RequestParam Long learningId, @RequestParam Long examId, @RequestParam double score) {
        GradeDto g = svc.awardGrade(learningId, examId, score);
        return ResponseEntity.ok(g);
    }

    @GetMapping("/learning/{learningId}")
    public List<GradeDto> byLearning(@PathVariable Long learningId) {
        return svc.findGradesByLearning(learningId);
    }
}
