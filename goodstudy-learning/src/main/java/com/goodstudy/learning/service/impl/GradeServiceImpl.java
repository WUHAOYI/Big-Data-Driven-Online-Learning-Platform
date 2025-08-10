package com.goodstudy.learning.service.impl;

import com.goodstudy.learning.service.GradeService;
import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepo;
    private final LearningRepository learningRepo;
    private final ExamRepository examRepo;

    public GradeServiceImpl(GradeRepository gradeRepo, LearningRepository learningRepo, ExamRepository examRepo) {
        this.gradeRepo = gradeRepo;
        this.learningRepo = learningRepo;
        this.examRepo = examRepo;
    }

    @Override
    @Transactional
    public GradeDto awardGrade(Long learningId, Long examId, double score) {
        LearningEntity l = learningRepo.findById(learningId).orElseThrow(() -> new NotFoundException("Learning not found: " + learningId));
        ExamEntity ex = examRepo.findById(examId).orElseThrow(() -> new NotFoundException("Exam not found: " + examId));
        String letter = computeLetter(score);
        GradeEntity ge = GradeEntity.builder().learning(l).exam(ex).score(score).gradeLetter(letter).awardedAt(LocalDateTime.now()).build();
        GradeEntity saved = gradeRepo.save(ge);
        return toDto(saved);
    }

    private String computeLetter(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    @Override
    public List<GradeDto> findGradesByLearning(Long learningId) {
        return gradeRepo.findByLearningId(learningId).stream().map(this::toDto).collect(Collectors.toList());
    }

    private GradeDto toDto(GradeEntity e) {
        return GradeDto.builder()
                .id(e.getId())
                .examId(e.getExam() == null ? null : e.getExam().getId())
                .learningId(e.getLearning() == null ? null : e.getLearning().getId())
                .score(e.getScore())
                .gradeLetter(e.getGradeLetter())
                .awardedAt(e.getAwardedAt())
                .build();
    }
}
