package com.goodstudy.learning.service.impl;

import com.goodstudy.learning.service.LearningAdvancedService;
import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.*;

@Service
public class LearningAdvancedServiceImpl implements LearningAdvancedService {

    private final LearningRepository learningRepo;
    private final CourseRepository courseRepo;
    private final ProgressRepository progressRepo;

    public LearningAdvancedServiceImpl(LearningRepository learningRepo, CourseRepository courseRepo, ProgressRepository progressRepo) {
        this.learningRepo = learningRepo;
        this.courseRepo = courseRepo;
        this.progressRepo = progressRepo;
    }

    @Override
    @Transactional
    public LearningDto transferEnrollment(Long learningId, Long toCourseId) {
        LearningEntity l = learningRepo.findById(learningId).orElseThrow(() -> new NotFoundException("Learning not found: " + learningId));
        CourseEntity to = courseRepo.findById(toCourseId).orElseThrow(() -> new NotFoundException("Course not found: " + toCourseId));
        l.setCourse(to);
        LearningEntity saved = learningRepo.save(l);
        // reset progress
        progressRepo.findByLearningId(learningId).forEach(p -> { p.setPercentage(0); progressRepo.save(p); });
        return LearningDto.builder().id(saved.getId()).learnerName(saved.getLearnerName()).course(null).enrolledAt(saved.getEnrolledAt()).build();
    }

    @Override
    @Transactional
    public void withdraw(Long learningId) {
        if (!learningRepo.existsById(learningId)) throw new NotFoundException("Learning not found: " + learningId);
        progressRepo.findByLearningId(learningId).forEach(p -> progressRepo.deleteById(p.getId()));
        learningRepo.deleteById(learningId);
    }

    @Override
    public List<LearningDto> findByLearner(String learnerName) {
        return learningRepo.findByLearnerName(learnerName).stream().map(e -> LearningDto.builder().id(e.getId()).learnerName(e.getLearnerName()).course(null).enrolledAt(e.getEnrolledAt()).build()).collect(Collectors.toList());
    }
}
