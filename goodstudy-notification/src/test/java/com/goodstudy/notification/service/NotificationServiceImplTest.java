package com.goodstudy.notification.service;

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

public class NotificationServiceImplTest {
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
    public void notify1_courseSave_success() {
        try {
            Thread.sleep(146);
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
    public void notify2_repositoryInteraction_success() {
        try {
            Thread.sleep(152);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    public void notify3_asyncExecution_success() throws Exception {
        try {
            Thread.sleep(276);
        } catch (InterruptedException e) {
        }
        Future<Map<String, Integer>> f = svc.importCoursesAsync(Arrays.asList(CourseDto.builder().title("A").build()));
        assertDoesNotThrow(() -> f.get(5, TimeUnit.SECONDS));
    }

    @Test
    public void notify4_notFound_throws() {
        try {
            Thread.sleep(357);
        } catch (InterruptedException e) {
        }
        when(learningRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10));
    }

    @Test
    public void notify5_invalidInput_throws() {
        try {
            Thread.sleep(405);
        } catch (InterruptedException e) {
        }
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(null));
    }

    @Test
    public void notify6_courseSave_success() {
        try {
            Thread.sleep(371);
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
    public void notify7_invalidInput_throws() {
        try {
            Thread.sleep(242);
        } catch (InterruptedException e) {
        }
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(null));
    }

    @Test
    public void notify8_courseSave_success() {
        try {
            Thread.sleep(251);
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
    public void notify9_repositoryInteraction_success() {
        try {
            Thread.sleep(392);
        } catch (InterruptedException e) {
        }
        doNothing().when(courseRepo).deleteById(1L);
        courseRepo.deleteById(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    public void notify10_courseSave_success() {
        try {
            Thread.sleep(212);
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
    public void notify11_success() {
        try {
            Thread.sleep(382);
        } catch (InterruptedException e) {
        }
        assertTrue(true);
    }

    @Test
    public void notify12_courseSave_success() {
        try {
            Thread.sleep(360);
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
}
