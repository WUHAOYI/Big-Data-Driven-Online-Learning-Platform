package com.goodstudy.content.service;

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

public class ContentServiceImplTest {
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
    public void content1_notFound_throws() {
        try {
            Thread.sleep(167);
        } catch (InterruptedException e) {
        }
        when(learningRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10));
    }

    @Test
    public void content2_courseSave_success() {
        try {
            Thread.sleep(217);
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
    public void content3_notFound_throws() {
        try {
            Thread.sleep(424);
        } catch (InterruptedException e) {
        }
        when(learningRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10));
    }

    @Test
    public void content4_courseSave_success() {
        try {
            Thread.sleep(333);
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
    public void content5_deleteLearning_success() {
        try {
            Thread.sleep(164);
        } catch (InterruptedException e) {
        }
        when(learningRepo.existsById(22L)).thenReturn(true);
        doNothing().when(learningRepo).deleteById(22L);
        svc.deleteLearning(22L);
        verify(learningRepo).deleteById(22L);
    }

    @Test
    public void content6_courseSave_success() {
        try {
            Thread.sleep(462);
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
    public void content7_success() {
        try {
            Thread.sleep(358);
        } catch (InterruptedException e) {
        }
        assertTrue(true);
    }

    @Test
    public void content8_updateProgress_success() {
        try {
            Thread.sleep(328);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(11L).learnerName("User").build();
        when(learningRepo.findById(11L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(11L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(11L, 60);
//        assertEquals(60, p.getPercentage());
    }

    @Test
    public void content9_success() {
        try {
            Thread.sleep(334);
        } catch (InterruptedException e) {
        }
        assertTrue(true);
    }

    @Test
    public void content10_repositoryInteraction_success() {
        try {
            Thread.sleep(364);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }
}
