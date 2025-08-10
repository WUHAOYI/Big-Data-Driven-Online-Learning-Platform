package com.goodstudy.learning.service.impl;

import com.goodstudy.learning.service.CourseExtendedService;
import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.exception.NotFoundException;
import com.goodstudy.learning.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

@Service
public class CourseExtendedServiceImpl implements CourseExtendedService {

    private final CourseRepository courseRepo;
    private final ExecutorService exec = Executors.newFixedThreadPool(3);

    public CourseExtendedServiceImpl(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    @Override
    public PagedResult<CourseDto> searchCourses(CourseSearchRequest req) {
        // naive in-memory search using repo.findAll (sufficient for tests)
        List<CourseEntity> all = courseRepo.findAll();
        Stream<CourseEntity> s = all.stream();
        if (!StringUtil.isBlank(req.getQ())) {
            String q = req.getQ().toLowerCase();
            s = s.filter(c -> (c.getTitle() != null && c.getTitle().toLowerCase().contains(q)) || (c.getDescription()!=null && c.getDescription().toLowerCase().contains(q)));
        }
        if (!StringUtil.isBlank(req.getLevel())) {
            s = s.filter(c -> req.getLevel().equalsIgnoreCase(c.getLevel()));
        }
        if (req.getPublished() != null) {
            s = s.filter(c -> c.isPublished() == req.getPublished());
        }
        List<CourseDto> list = s.skip((long)req.getPage()*req.getSize())
                .limit(req.getSize())
                .map(this::toDto)
                .collect(Collectors.toList());
        long total = all.size();
        return PagedResult.<CourseDto>builder().items(list).total(total).page(req.getPage()).size(req.getSize()).build();
    }

    @Override
    public Map<String, Long> courseStats() {
        List<CourseEntity> all = courseRepo.findAll();
        long published = all.stream().filter(CourseEntity::isPublished).count();
        long total = all.size();
        Map<String,Long> r = new HashMap<>();
        r.put("total", total);
        r.put("published", published);
        r.put("unpublished", total - published);
        return r;
    }

    @Override
    public Future<Map<String,Integer>> bulkCreateCoursesAsync(List<CourseDto> dtos) {
        return exec.submit(() -> {
            int success = 0; int failed = 0;
            for (CourseDto d : dtos) {
                try {
                    CourseEntity e = CourseEntity.builder()
                            .title(d.getTitle())
                            .description(d.getDescription())
                            .level(d.getLevel())
                            .published(d.isPublished())
                            .build();
                    courseRepo.save(e);
                    success++;
                } catch (Exception ex) { failed++; }
            }
            Map<String,Integer> out = new HashMap<>(); out.put("success", success); out.put("failed", failed); return out;
        });
    }

    @Override
    @Transactional
    public void archiveCourse(Long id) {
        CourseEntity e = courseRepo.findById(id).orElseThrow(() -> new NotFoundException("Course not found: " + id));
        e.setPublished(false);
        e.setTitle("[ARCHIVED] " + e.getTitle());
        courseRepo.save(e);
    }

    private CourseDto toDto(CourseEntity e) {
        return CourseDto.builder().id(e.getId()).title(e.getTitle()).description(e.getDescription()).level(e.getLevel()).published(e.isPublished()).build();
    }
}
