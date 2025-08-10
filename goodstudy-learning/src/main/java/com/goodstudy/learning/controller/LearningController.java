package com.goodstudy.learning.controller;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.service.LearningService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.*;

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
    public List<LearningDto> list(@RequestParam(name = "learner", required = false) String learner) {
        if (learner == null) {
            return svc.findAllLearnings();
        } else {
            // new API to search by learner name
            return svc.findAllLearnings().stream().filter(l -> l.getLearnerName().equalsIgnoreCase(learner)).toList();
        }
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

    @PostMapping("/course")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto dto) {
        CourseDto created = svc.createCourse(dto);
        return ResponseEntity.created(URI.create("/api/learning/course/" + created.getId())).body(created);
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto dto) {
        CourseDto updated = svc.updateCourse(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long id) {
        return svc.findCourse(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/courses")
    public List<CourseDto> listCourses(@RequestParam(name = "published", defaultValue = "false") boolean published) {
        return svc.listCourses(published);
    }
}
