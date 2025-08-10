package com.goodstudy.learning.controller;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.service.ExamService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
    private final ExamService svc;
    public ExamController(ExamService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<ExamDto> create(@RequestBody ExamDto dto) {
        ExamDto created = svc.createExam(dto);
        return ResponseEntity.created(URI.create("/api/exams/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDto> get(@PathVariable Long id) {
        return svc.findExam(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-course/{courseId}")
    public List<ExamDto> listByCourse(@PathVariable Long courseId) {
        return svc.listByCourse(courseId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
