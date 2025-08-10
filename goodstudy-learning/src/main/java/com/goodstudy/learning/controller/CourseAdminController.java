package com.goodstudy.learning.controller;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.service.CourseExtendedService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/admin/courses")
public class CourseAdminController {

    private final CourseExtendedService svc;

    public CourseAdminController(CourseExtendedService svc) {
        this.svc = svc;
    }

    @PostMapping("/search")
    public ResponseEntity<PagedResult<CourseDto>> search(@RequestBody CourseSearchRequest req) {
        return ResponseEntity.ok(svc.searchCourses(req));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String,Long>> stats() {
        return ResponseEntity.ok(svc.courseStats());
    }

    @PostMapping("/bulk")
    public ResponseEntity<Map<String,Integer>> bulkCreate(@RequestBody List<CourseDto> dtos) throws Exception {
        Future<Map<String,Integer>> f = svc.bulkCreateCoursesAsync(dtos);
        Map<String,Integer> res = f.get();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<Void> archive(@PathVariable Long id) {
        svc.archiveCourse(id);
        return ResponseEntity.noContent().build();
    }
}
