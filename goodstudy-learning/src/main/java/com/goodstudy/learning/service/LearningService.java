package com.goodstudy.learning.service;

import com.goodstudy.learning.model.dto.LearningDto;
import com.goodstudy.learning.model.dto.ProgressDto;

import java.util.List;
import java.util.Optional;

public interface LearningService {
    LearningDto createLearning(String learnerName, Long courseId);
    Optional<LearningDto> findLearningById(Long id);
    List<LearningDto> findAllLearnings();
    void deleteLearning(Long id);

    ProgressDto updateProgress(Long learningId, int percentage);
}
