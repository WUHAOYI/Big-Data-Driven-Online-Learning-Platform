package com.goodstudy.learning.service;

import com.goodstudy.learning.model.*;
import com.goodstudy.learning.model.dto.LearningDto;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.service.impl.LearningServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LearningServiceImplTest {

    private LearningEntityRepository learningRepo;
    private CourseRepository courseRepo;
    private ProgressRepository progressRepo;
    private LearningServiceImpl service;

    @BeforeEach
    void setup() {
        learningRepo = mock(LearningEntityRepository.class);
        courseRepo = mock(CourseRepository.class);
        progressRepo = mock(ProgressRepository.class);
        service = new LearningServiceImpl(learningRepo, courseRepo, progressRepo);
    }

    @Test
    void testCreateLearning() {
        CourseEntity course = CourseEntity.builder().id(1L).title("Java").description("Java basics").level("Beginner").build();
        LearningEntity saved = LearningEntity.builder().id(10L).learnerName("Alice").course(course).build();

        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(learningRepo.save(any())).thenReturn(saved);

        LearningDto dto = service.createLearning("Alice", 1L);

        assertEquals("Alice", dto.getLearnerName());
        assertEquals("Java", dto.getCourse().getTitle());
    }

    @Test
    void testUpdateProgress() {
        CourseEntity course = CourseEntity.builder().id(1L).title("Java").build();
        LearningEntity learning = LearningEntity.builder().id(10L).learnerName("Alice").course(course).build();
        ProgressEntity progress = ProgressEntity.builder().id(100L).learning(learning).percentage(0).build();

        when(learningRepo.findById(10L)).thenReturn(Optional.of(learning));
        when(progressRepo.findAll()).thenReturn(List.of(progress));
        when(progressRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var dto = service.updateProgress(10L, 50);

        assertEquals(50, dto.getPercentage());
    }
}
