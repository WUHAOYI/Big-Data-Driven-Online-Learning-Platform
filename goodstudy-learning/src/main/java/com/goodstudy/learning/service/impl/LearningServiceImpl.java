package com.goodstudy.learning.service.impl;

import com.goodstudy.learning.model.*;
import com.goodstudy.learning.model.dto.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.service.LearningService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LearningServiceImpl implements LearningService {

    private final LearningEntityRepository learningRepo;
    private final CourseRepository courseRepo;
    private final ProgressRepository progressRepo;

    public LearningServiceImpl(LearningEntityRepository learningRepo, CourseRepository courseRepo, ProgressRepository progressRepo) {
        this.learningRepo = learningRepo;
        this.courseRepo = courseRepo;
        this.progressRepo = progressRepo;
    }

    @Override
    public LearningDto createLearning(String learnerName, Long courseId) {
        CourseEntity course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        LearningEntity learning = LearningEntity.builder()
                .learnerName(learnerName)
                .course(course)
                .build();

        return toDto(learningRepo.save(learning));
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
        learningRepo.deleteById(id);
    }

    @Override
    public ProgressDto updateProgress(Long learningId, int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        LearningEntity learning = learningRepo.findById(learningId)
                .orElseThrow(() -> new IllegalArgumentException("Learning not found"));

        ProgressEntity progress = progressRepo.findAll().stream()
                .filter(p -> p.getLearning().getId().equals(learningId))
                .findFirst()
                .orElse(ProgressEntity.builder().learning(learning).build());

        progress.setPercentage(percentage);
        return toDto(progressRepo.save(progress));
    }

    private LearningDto toDto(LearningEntity e) {
        CourseDto courseDto = CourseDto.builder()
                .id(e.getCourse().getId())
                .title(e.getCourse().getTitle())
                .description(e.getCourse().getDescription())
                .level(e.getCourse().getLevel())
                .build();

        return LearningDto.builder()
                .id(e.getId())
                .learnerName(e.getLearnerName())
                .course(courseDto)
                .build();
    }

    private ProgressDto toDto(ProgressEntity e) {
        return ProgressDto.builder()
                .id(e.getId())
                .learning(toDto(e.getLearning()))
                .percentage(e.getPercentage())
                .build();
    }
}
