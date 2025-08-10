package com.goodstudy.learning.service.impl;

import com.goodstudy.learning.service.LearningService;
import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.exception.NotFoundException;
import com.goodstudy.learning.validator.ProgressValidator;
import com.goodstudy.learning.util.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

@Service
public class LearningServiceImpl implements LearningService {

    private final LearningRepository learningRepo;
    private final CourseRepository courseRepo;
    private final ProgressRepository progressRepo;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public LearningServiceImpl(LearningRepository learningRepo, CourseRepository courseRepo, ProgressRepository progressRepo) {
        this.learningRepo = learningRepo;
        this.courseRepo = courseRepo;
        this.progressRepo = progressRepo;
    }

    @Override
    @Transactional
    public LearningDto createLearning(String learnerName, Long courseId) {
        if (StringUtil.isBlank(learnerName)) throw new IllegalArgumentException("learnerName required");
        CourseEntity course = courseRepo.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found: " + courseId));

        LearningEntity learning = LearningEntity.builder()
                .learnerName(learnerName)
                .course(course)
                .build();

        LearningEntity saved = learningRepo.save(learning);
        // create initial progress record
        ProgressEntity p = ProgressEntity.builder().learning(saved).percentage(0).build();
        progressRepo.save(p);
        return toDto(saved);
    }

    @Override
    public Optional<LearningDto> findLearningById(Long id) {
        return learningRepo.findById(id).map(this::toDto);
    }

    @Override
    public List<LearningDto> findAllLearnings() {
        return learningRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteLearning(Long id) {
        if (!learningRepo.existsById(id)) throw new NotFoundException("Learning not found: " + id);
        // delete progress first
        progressRepo.findByLearningId(id).forEach(p -> progressRepo.deleteById(p.getId()));
        learningRepo.deleteById(id);
    }

    @Override
    @Transactional
    public ProgressDto updateProgress(Long learningId, int percentage) {
        ProgressValidator.validatePercentage(percentage);
        LearningEntity learning = learningRepo.findById(learningId)
                .orElseThrow(() -> new NotFoundException("Learning not found: " + learningId));

        // simulate concurrent-safe update by retrying optimistic update
        synchronized (("" + learningId).intern()) {
            ProgressEntity progress = progressRepo.findByLearningId(learningId).stream()
                    .findFirst()
                    .orElse(ProgressEntity.builder().learning(learning).build());

            progress.setPercentage(percentage);
            progress = progressRepo.save(progress);
            return toDto(progress);
        }
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseDto dto) {
        if (dto == null || StringUtil.isBlank(dto.getTitle())) throw new IllegalArgumentException("course title required");
        CourseEntity entity = CourseEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .level(dto.getLevel())
                .published(dto.isPublished())
                .build();
        CourseEntity saved = courseRepo.save(entity);
        return toDto(saved);
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long id, CourseDto dto) {
        CourseEntity entity = courseRepo.findById(id).orElseThrow(() -> new NotFoundException("Course not found: " + id));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setLevel(dto.getLevel());
        entity.setPublished(dto.isPublished());
        return toDto(courseRepo.save(entity));
    }

    @Override
    public Optional<CourseDto> findCourse(Long id) {
        return courseRepo.findById(id).map(this::toDto);
    }

    @Override
    public List<CourseDto> listCourses(boolean onlyPublished) {
        if (onlyPublished) {
            return courseRepo.findByPublishedTrue().stream().map(this::toDto).collect(Collectors.toList());
        } else {
            return courseRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
        }
    }

    // Bulk asynchronous import that returns future summary
    public Future<Map<String,Integer>> importCoursesAsync(List<CourseDto> dtos) {
        return executor.submit(() -> {
            int success = 0;
            int failed = 0;
            for (CourseDto d : dtos) {
                try {
                    createCourse(d);
                    success++;
                } catch (Exception ex) {
                    failed++;
                }
            }
            Map<String,Integer> r = new HashMap<>();
            r.put("success", success);
            r.put("failed", failed);
            return r;
        });
    }

    // utility functions
    private CourseDto toDto(CourseEntity e) {
        return CourseDto.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .level(e.getLevel())
                .published(e.isPublished())
                .build();
    }

    private LearningDto toDto(LearningEntity e) {
        CourseDto c = toDto(e.getCourse());
        return LearningDto.builder()
                .id(e.getId())
                .learnerName(e.getLearnerName())
                .course(c)
                .enrolledAt(e.getEnrolledAt())
                .build();
    }

    private ProgressDto toDto(ProgressEntity e) {
        return ProgressDto.builder()
                .id(e.getId())
                .learning(toDto(e.getLearning()))
                .percentage(e.getPercentage())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}
