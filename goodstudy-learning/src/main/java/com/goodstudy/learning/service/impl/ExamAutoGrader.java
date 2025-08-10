package com.goodstudy.learning.service.impl;

import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import org.springframework.stereotype.Component;
import java.util.*;
import java.time.*;

@Component
public class ExamAutoGrader {

    private final GradeRepository gradeRepo;
    public ExamAutoGrader(GradeRepository gradeRepo) {
        this.gradeRepo = gradeRepo;
    }

    // naive automatic grading: accept map of question->score, compute percentage and award grade entity
    public GradeEntity autoGrade(LearningEntity learning, ExamEntity exam, Map<String,Double> answers) {
        double total = answers.values().stream().mapToDouble(Double::doubleValue).sum();
        double max = answers.size() * 1.0;
        double percent = (max == 0) ? 0 : (total / max) * 100.0;
        String letter = percent >= 90 ? "A" : percent >=80 ? "B" : percent>=70?"C":percent>=60?"D":"F";
        GradeEntity g = GradeEntity.builder().learning(learning).exam(exam).score(percent).gradeLetter(letter).awardedAt(LocalDateTime.now()).build();
        return gradeRepo.save(g);
    }
}
