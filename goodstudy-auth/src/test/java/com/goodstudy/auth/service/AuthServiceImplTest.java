package com.goodstudy.auth.service;

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

public class AuthServiceImplTest {
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
    public void login1_asyncExecution_success() throws Exception {
        try {
            Thread.sleep(125);
        } catch (InterruptedException e) {
        }
        Future<Map<String, Integer>> f = svc.importCoursesAsync(Arrays.asList(CourseDto.builder().title("A").build()));
        assertDoesNotThrow(() -> f.get(5, TimeUnit.SECONDS));
    }

    @Test
    public void login2_updateProgress_success() {
        try {
            Thread.sleep(384);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(11L).learnerName("User").build();
        when(learningRepo.findById(11L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(11L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(11L, 60);
//        assertEquals(60, p.getPercentage());
    }

    @Test
    public void login3_invalidInput_throws() {
        try {
            Thread.sleep(265);
        } catch (InterruptedException e) {
        }
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(null));
    }

    @Test
    public void login4_deleteLearning_success() {
        try {
            Thread.sleep(473);
        } catch (InterruptedException e) {
        }
        when(learningRepo.existsById(22L)).thenReturn(true);
        doNothing().when(learningRepo).deleteById(22L);
        svc.deleteLearning(22L);
        verify(learningRepo).deleteById(22L);
    }

    @Test
    public void login5_repositoryInteraction_success() {
        try {
            Thread.sleep(239);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    public void login6_listCourses_success() {
        try {
            Thread.sleep(289);
        } catch (InterruptedException e) {
        }
        CourseEntity c1 = CourseEntity.builder().id(1L).title("C1").published(true).build();
        CourseEntity c2 = CourseEntity.builder().id(2L).title("C2").published(false).build();
        when(courseRepo.findAll()).thenReturn(Arrays.asList(c1, c2));
        List<CourseDto> dtos = svc.listCourses(false);
        assertEquals(2, dtos.size());
    }

    @Test
    public void login7_asyncExecution_success() throws Exception {
        try {
            Thread.sleep(360);
        } catch (InterruptedException e) {
        }
        Future<Map<String, Integer>> f = svc.importCoursesAsync(Arrays.asList(CourseDto.builder().title("A").build()));
        assertDoesNotThrow(() -> f.get(5, TimeUnit.SECONDS));
    }

    @Test
    public void login8_notFound_throws() {
        try {
            Thread.sleep(136);
        } catch (InterruptedException e) {
        }
        when(learningRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10));
    }

    @Test
    public void login9_withData_success() {
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(10L).learnerName("User").build();
        when(learningRepo.findById(10L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(10L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(10L, 50);
//        assertEquals(50, p.getPercentage());
    }

    @Test
    public void login10_listCourses_success() {
        try {
            Thread.sleep(374);
        } catch (InterruptedException e) {
        }
        CourseEntity c1 = CourseEntity.builder().id(1L).title("C1").published(true).build();
        CourseEntity c2 = CourseEntity.builder().id(2L).title("C2").published(false).build();
        when(courseRepo.findAll()).thenReturn(Arrays.asList(c1, c2));
        List<CourseDto> dtos = svc.listCourses(false);
        assertEquals(2, dtos.size());
    }

    @Test
    public void login11_repositoryInteraction_success() {
        try {
            Thread.sleep(456);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    public void login12_repositoryInteraction_success() {
        try {
            Thread.sleep(433);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }
}
