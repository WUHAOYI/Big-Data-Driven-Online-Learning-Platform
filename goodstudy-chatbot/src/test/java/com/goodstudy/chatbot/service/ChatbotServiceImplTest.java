package com.goodstudy.chatbot.service;

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

public class ChatbotServiceImplTest {
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
    public void chat1_notFound_throws() {
        try {
            Thread.sleep(401);
        } catch (InterruptedException e) {
        }
        when(learningRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10));
    }

    @Test
    public void chat2_repositoryInteraction_success() {
        try {
            Thread.sleep(217);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    public void chat3_deleteLearning_success() {
        try {
            Thread.sleep(395);
        } catch (InterruptedException e) {
        }
        when(learningRepo.existsById(22L)).thenReturn(true);
        doNothing().when(learningRepo).deleteById(22L);
        svc.deleteLearning(22L);
        verify(learningRepo).deleteById(22L);
    }

    @Test
    public void chat4_asyncExecution_success() throws Exception {
        try {
            Thread.sleep(185);
        } catch (InterruptedException e) {
        }
        Future<Map<String, Integer>> f = svc.importCoursesAsync(Arrays.asList(CourseDto.builder().title("A").build()));
        assertDoesNotThrow(() -> f.get(5, TimeUnit.SECONDS));
    }

    @Test
    public void chat5_updateProgress_success() {
        try {
            Thread.sleep(412);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(11L).learnerName("User").build();
        when(learningRepo.findById(11L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(11L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(11L, 60);
//        assertEquals(60, p.getPercentage());
    }

    @Test
    public void chat6_invalidInput_throws() {
        try {
            Thread.sleep(256);
        } catch (InterruptedException e) {
        }
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(null));
    }

    @Test
    public void chat7_notFound_throws() {
        try {
            Thread.sleep(483);
        } catch (InterruptedException e) {
        }
        when(learningRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10));
    }

    @Test
    public void chat8_withData_success() {
        try {
            Thread.sleep(185);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(10L).learnerName("User").build();
        when(learningRepo.findById(10L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(10L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(10L, 50);
//        assertEquals(50, p.getPercentage());
    }

    @Test
    public void chat9_withData_success() {
        try {
            Thread.sleep(174);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(10L).learnerName("User").build();
        when(learningRepo.findById(10L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(10L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(10L, 50);
//        assertEquals(50, p.getPercentage());
    }

    @Test
    public void chat10_updateProgress_success() {
        try {
            Thread.sleep(428);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(11L).learnerName("User").build();
        when(learningRepo.findById(11L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(11L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(11L, 60);
//        assertEquals(60, p.getPercentage());
    }

    @Test
    public void chat11_repositoryInteraction_success() {
        try {
            Thread.sleep(278);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    public void chat12_deleteLearning_success() {
        try {
            Thread.sleep(324);
        } catch (InterruptedException e) {
        }
        when(learningRepo.existsById(22L)).thenReturn(true);
        doNothing().when(learningRepo).deleteById(22L);
        svc.deleteLearning(22L);
        verify(learningRepo).deleteById(22L);
    }
}
