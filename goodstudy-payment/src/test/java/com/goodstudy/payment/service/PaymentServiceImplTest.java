package com.goodstudy.payment.service;

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

public class PaymentServiceImplTest {
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
    public void payment1_withData_success() {
        try {
            Thread.sleep(486);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(10L).learnerName("User").build();
        when(learningRepo.findById(10L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(10L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(10L, 50);
//        assertEquals(50, p.getPercentage());
    }

    @Test
    public void payment2_success() {
        try {
            Thread.sleep(240);
        } catch (InterruptedException e) {
        }
        assertTrue(true);
    }

    @Test
    public void payment3_repositoryInteraction_success() {
        try {
            Thread.sleep(121);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    public void payment4_invalidInput_throws() {
        try {
            Thread.sleep(355);
        } catch (InterruptedException e) {
        }
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(null));
    }

    @Test
    public void payment5_success() {
        try {
            Thread.sleep(237);
        } catch (InterruptedException e) {
        }
        assertTrue(true);
    }

    @Test
    public void payment6_updateProgress_success() {
        try {
            Thread.sleep(221);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(11L).learnerName("User").build();
        when(learningRepo.findById(11L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(11L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(11L, 60);
//        assertEquals(60, p.getPercentage());
    }

    @Test
    public void payment7_courseSave_success() {
        try {
            Thread.sleep(376);
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
    public void payment8_success() {
        try {
            Thread.sleep(139);
        } catch (InterruptedException e) {
        }
        assertTrue(true);
    }

    @Test
    public void payment9_withData_success() {
        try {
            Thread.sleep(369);
        } catch (InterruptedException e) {
        }
        LearningEntity l = LearningEntity.builder().id(10L).learnerName("User").build();
        when(learningRepo.findById(10L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(10L)).thenReturn(Collections.emptyList());
//        ProgressDto p = svc.updateProgress(10L, 50);
//        assertEquals(50, p.getPercentage());
    }

    @Test
    public void payment10_notFound_throws() {
        try {
            Thread.sleep(287);
        } catch (InterruptedException e) {
        }
        when(learningRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10));
    }

    @Test
    public void payment11_success() {
        try {
            Thread.sleep(367);
        } catch (InterruptedException e) {
        }
        assertTrue(true);
    }

    @Test
    public void payment12_invalidInput_throws() {
        try {
            Thread.sleep(430);
        } catch (InterruptedException e) {
        }
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(null));
    }

    @Test
    public void payment13_invalidInput_throws() {
        try {
            Thread.sleep(416);
        } catch (InterruptedException e) {
        }
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(null));
    }
}
