package com.goodstudy.learning.controller;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final LearningService learningService;
    private final CourseServicePlaceholder placeholder = new CourseServicePlaceholder();

    public AdminController(LearningService learningService) {
        this.learningService = learningService;
    }

    // Bulk CSV import of courses: simple parser that expects title,description,level,published
    @PostMapping(value = "/courses/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String,Object>> importCourses(@RequestParam("file") MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    CourseDto dto = CourseDto.builder()
                            .title(parts[0])
                            .description(parts[1])
                            .level(parts[2])
                            .published(Boolean.parseBoolean(parts[3]))
                            .build();
                    learningService.createCourse(dto);
                    count++;
                }
            }
            Map<String,Object> r = new HashMap<>();
            r.put("imported", count);
            return ResponseEntity.ok(r);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to read file");
        }
    }

    // simple CSV export of courses
    @GetMapping(value = "/courses/export", produces = "text/csv")
    public ResponseEntity<String> exportCourses() {
        var courses = learningService.listCourses(false);
        StringBuilder sb = new StringBuilder();
        sb.append("title,description,level,published\n");
        for (CourseDto c : courses) {
            sb.append(escape(c.getTitle())).append(",").append(escape(c.getDescription())).append(",").append(escape(c.getLevel())).append(",").append(c.isPublished()).append("\n");
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= courses.csv").body(sb.toString());
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\n"," ").replace(",",";");
    }

    // A placeholder class to show additional admin utilities (not a service)
    static class CourseServicePlaceholder {
        public String ping() { return "pong"; }
    }
}
