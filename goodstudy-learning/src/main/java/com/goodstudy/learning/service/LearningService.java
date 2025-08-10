package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import java.util.*;

public interface LearningService {
    LearningDto createLearning(String learnerName, Long courseId);
    Optional<LearningDto> findLearningById(Long id);
    List<LearningDto> findAllLearnings();
    void deleteLearning(Long id);
    ProgressDto updateProgress(Long learningId, int percentage);
    CourseDto createCourse(CourseDto dto);
    CourseDto updateCourse(Long id, CourseDto dto);
    Optional<CourseDto> findCourse(Long id);
    List<CourseDto> listCourses(boolean onlyPublished);
}
