package com.goodstudy.learning.service.impl;

import com.goodstudy.learning.service.ExamService;
import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.*;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepo;
    private final CourseRepository courseRepo;

    public ExamServiceImpl(ExamRepository examRepo, CourseRepository courseRepo) {
        this.examRepo = examRepo;
        this.courseRepo = courseRepo;
    }

    @Override
    @Transactional
    public ExamDto createExam(ExamDto dto) {
        CourseEntity course = courseRepo.findById(dto.getCourseId()).orElseThrow(() -> new NotFoundException("Course not found: " + dto.getCourseId()));
        ExamEntity e = ExamEntity.builder()
                .title(dto.getTitle())
                .course(course)
                .scheduledAt(dto.getScheduledAt())
                .durationMinutes(dto.getDurationMinutes())
                .instructions(dto.getInstructions())
                .published(dto.isPublished())
                .build();
        ExamEntity saved = examRepo.save(e);
        return toDto(saved);
    }

    @Override
    public Optional<ExamDto> findExam(Long id) {
        return examRepo.findById(id).map(this::toDto);
    }

    @Override
    public List<ExamDto> listByCourse(Long courseId) {
        return examRepo.findByCourseId(courseId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteExam(Long id) {
        examRepo.deleteById(id);
    }

    private ExamDto toDto(ExamEntity e) {
        return ExamDto.builder()
                .id(e.getId())
                .title(e.getTitle())
                .courseId(e.getCourse() == null ? null : e.getCourse().getId())
                .scheduledAt(e.getScheduledAt())
                .durationMinutes(e.getDurationMinutes())
                .instructions(e.getInstructions())
                .published(e.isPublished())
                .build();
    }
}
