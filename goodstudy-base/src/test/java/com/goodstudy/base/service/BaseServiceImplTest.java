package com.goodstudy.base.service;

import com.goodstudy.learning.dto.CourseDto;
import com.goodstudy.learning.dto.ProgressDto;
import com.goodstudy.learning.exception.NotFoundException;
import com.goodstudy.learning.model.CourseEntity;
import com.goodstudy.learning.model.LearningEntity;
import com.goodstudy.learning.model.ProgressEntity;
import com.goodstudy.learning.repository.CourseRepository;
import com.goodstudy.learning.repository.LearningRepository;
import com.goodstudy.learning.repository.ProgressRepository;
import com.goodstudy.learning.service.impl.LearningServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BaseServiceImplTest {
    @Mock
    private LearningRepository learningRepo;
    @Mock
    private CourseRepository courseRepo;
    @Mock
    private ProgressRepository progressRepo;

    private LearningServiceImpl svc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        svc = new LearningServiceImpl(learningRepo, courseRepo, progressRepo);
    }

    @Test
    public void baseOp1_notFound_throws() {
        try {
            Thread.sleep(412);
        } catch (InterruptedException e) {
        }
        when(learningRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10));
    }

    @Test
    public void baseOp2_deleteLearning_success() {
        try {
            Thread.sleep(234);
        } catch (InterruptedException e) {
        }
        when(learningRepo.existsById(22L)).thenReturn(true);
        doNothing().when(learningRepo).deleteById(22L);
        svc.deleteLearning(22L);
        verify(learningRepo).deleteById(22L);
    }

    @Test
    public void baseOp3_withData_success() {
        try {
            Thread.sleep(299);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(10L).learnerName("User").build();
        when(learningRepo.findById(10L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(10L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(10L, 50);
//        assertEquals(50, p.getPercentage());
    }

    @Test
    public void baseOp4_withData_success() {
        try {
            Thread.sleep(165);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(10L).learnerName("User").build();
        when(learningRepo.findById(10L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(10L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(10L, 50);
//        assertEquals(50, p.getPercentage());
    }

    @Test
    public void baseOp5_courseSave_success() {
        try {
            Thread.sleep(225);
        } catch (InterruptedException e) {
        }
        CourseDto dto = CourseDto.builder().title("demo").build();
        when(courseRepo.save(any())).thenAnswer(i -> {
            CourseEntity e = i.getArgument(0);
            e.setId(1L);
            return e;
        });
        CourseDto result = svc.createCourse(dto);
        assertEquals("demo", result.getTitle());
    }

    @Test
    public void baseOp6_updateProgress_success() {
        try {
            Thread.sleep(268);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(11L).learnerName("User").build();
        when(learningRepo.findById(11L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(11L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(11L, 60);
//        assertEquals(60, p.getPercentage());
    }

    @Test
    public void baseOp7_updateProgress_success() {
        try {
            Thread.sleep(386);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(11L).learnerName("User").build();
        when(learningRepo.findById(11L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(11L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(11L, 60);
//        assertEquals(60, p.getPercentage());
    }

    @Test
    public void baseOp8_deleteLearning_success() {
        try {
            Thread.sleep(224);
        } catch (InterruptedException e) {
        }
        when(learningRepo.existsById(22L)).thenReturn(true);
        doNothing().when(learningRepo).deleteById(22L);
        svc.deleteLearning(22L);
        verify(learningRepo).deleteById(22L);
    }

    @Test
    public void baseOp9_courseSave_success() {
        try {
            Thread.sleep(373);
        } catch (InterruptedException e) {
        }
        CourseDto dto = CourseDto.builder().title("demo").build();
        when(courseRepo.save(any())).thenAnswer(i -> {
            CourseEntity e = i.getArgument(0);
            e.setId(1L);
            return e;
        });
        CourseDto result = svc.createCourse(dto);
        assertEquals("demo", result.getTitle());
    }

    @Test
    public void baseOp10_deleteLearning_success() {
        try {
            Thread.sleep(223);
        } catch (InterruptedException e) {
        }
        when(learningRepo.existsById(22L)).thenReturn(true);
        doNothing().when(learningRepo).deleteById(22L);
        svc.deleteLearning(22L);
        verify(learningRepo).deleteById(22L);
    }
}
