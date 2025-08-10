package com.goodstudy.learning.service;

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

public class LearningServiceImplTest {

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

    // ---------- 现有测试补充 ----------

    @Test
    public void createCourse_nullDto_throws() {
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(null));
    }

    @Test
    public void createCourse_emptyTitle_throws() {
        CourseDto dto = CourseDto.builder().title("").build();
        assertThrows(IllegalArgumentException.class, () -> svc.createCourse(dto));
    }

    @Test
    public void updateCourse_notFound_throws() {
        when(courseRepo.findById(anyLong())).thenReturn(Optional.empty());
        CourseDto dto = CourseDto.builder().title("title").build();
        assertThrows(NotFoundException.class, () -> svc.updateCourse(999L, dto));
    }

    @Test
    public void deleteLearning_withProgress_success() {
        Long learningId = 1L;
        when(learningRepo.existsById(learningId)).thenReturn(true);

        ProgressEntity p = ProgressEntity.builder().id(100L).build();
        when(progressRepo.findByLearningId(learningId)).thenReturn(Collections.singletonList(p));
        doNothing().when(progressRepo).deleteById(p.getId());
        doNothing().when(learningRepo).deleteById(learningId);

        svc.deleteLearning(learningId);

        verify(progressRepo).deleteById(p.getId());
        verify(learningRepo).deleteById(learningId);
    }

    @Test
    public void deleteLearning_noProgress_success() {
        Long learningId = 2L;
        when(learningRepo.existsById(learningId)).thenReturn(true);
        when(progressRepo.findByLearningId(learningId)).thenReturn(Collections.emptyList());
        doNothing().when(learningRepo).deleteById(learningId);

        svc.deleteLearning(learningId);

        verify(progressRepo, never()).deleteById(anyLong());
        verify(learningRepo).deleteById(learningId);
    }

    @Test
    public void deleteLearning_notFound_throws() {
        when(learningRepo.existsById(123L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> svc.deleteLearning(123L));
    }

    @Test
    public void importCoursesAsync_successAndFailure() throws Exception {
        CourseDto good = CourseDto.builder().title("Good").build();
        CourseDto bad = CourseDto.builder().title(null).build();

        when(courseRepo.save(any())).thenAnswer(i -> {
            CourseEntity e = i.getArgument(0);
            e.setId(1L);
            return e;
        });

        List<CourseDto> dtos = Arrays.asList(good, bad);

        Future<Map<String, Integer>> future = svc.importCoursesAsync(dtos);

        Map<String, Integer> result = future.get(5, TimeUnit.SECONDS);

        assertEquals(1, result.get("success"));
        assertEquals(1, result.get("failed"));
    }


    @Test
    public void updateProgress_existingProgress() {
        CourseEntity course = CourseEntity.builder().id(99L).title("A").description("B").level("E").build();
        LearningEntity learning = LearningEntity.builder().id(50L).learnerName("X").course(course).build();

        ProgressEntity progress = ProgressEntity.builder().id(10L).learning(learning).percentage(20).build();

        when(learningRepo.findById(50L)).thenReturn(Optional.of(learning));
        when(progressRepo.findByLearningId(50L)).thenReturn(Collections.singletonList(progress));
        when(progressRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        ProgressDto updated = svc.updateProgress(50L, 40);
        assertEquals(40, updated.getPercentage());
        assertEquals(10L, updated.getId());
    }

    @Test
    public void updateProgress_newProgressCreated() {
        CourseEntity course = CourseEntity.builder().id(99L).title("A").description("B").level("E").build();
        LearningEntity learning = LearningEntity.builder().id(50L).learnerName("X").course(course).build();

        when(learningRepo.findById(50L)).thenReturn(Optional.of(learning));
        when(progressRepo.findByLearningId(50L)).thenReturn(Collections.emptyList());
        when(progressRepo.save(any())).thenAnswer(i -> {
            ProgressEntity p = i.getArgument(0);
            p.setId(77L);
            return p;
        });

        ProgressDto p = svc.updateProgress(50L, 30);
        assertEquals(30, p.getPercentage());
        assertEquals(77L, p.getId());
    }

    @Test
    public void updateProgress_invalidPercentage_throws() {
        assertThrows(IllegalArgumentException.class, () -> svc.updateProgress(1L, -1));
        assertThrows(IllegalArgumentException.class, () -> svc.updateProgress(1L, 101));
    }

    @Test
    public void updateProgress_learningNotFound_throws() {
        when(learningRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.updateProgress(123L, 50));
    }

    @Test
    public void listCourses_onlyPublished() {
        CourseEntity publishedCourse = CourseEntity.builder().id(1L).title("P").published(true).build();
        when(courseRepo.findByPublishedTrue()).thenReturn(Collections.singletonList(publishedCourse));

        List<CourseDto> dtos = svc.listCourses(true);
        assertEquals(1, dtos.size());
        assertTrue(dtos.get(0).isPublished());
    }

    @Test
    public void listCourses_all() {
        CourseEntity c1 = CourseEntity.builder().id(1L).title("C1").published(true).build();
        CourseEntity c2 = CourseEntity.builder().id(2L).title("C2").published(false).build();

        when(courseRepo.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<CourseDto> dtos = svc.listCourses(false);
        assertEquals(2, dtos.size());
    }
}
