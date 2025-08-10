package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.service.impl.LearningServiceImpl;
import com.goodstudy.learning.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
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

    @Test
    public void createLearning_success() {
        CourseEntity course = CourseEntity.builder().id(1L).title("Java 101").build();
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(learningRepo.save(any())).thenAnswer(i -> {
            LearningEntity e = i.getArgument(0);
            e.setId(10L);
            return e;
        });

        LearningDto dto = svc.createLearning("Alice", 1L);
        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals("Alice", dto.getLearnerName());
        verify(learningRepo, times(1)).save(any());
    }

    @Test
    public void createLearning_courseNotFound() {
        when(courseRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.createLearning("Bob", 2L));
    }

    @Test
    public void findLearningById_found() {
        LearningEntity e = LearningEntity.builder().id(5L).learnerName("L").course(CourseEntity.builder().id(3L).title("C").build()).build();
        when(learningRepo.findById(5L)).thenReturn(Optional.of(e));
        Optional<LearningDto> dto = svc.findLearningById(5L);
        assertTrue(dto.isPresent());
        assertEquals(5L, dto.get().getId());
    }

    @Test
    public void findLearningById_notFound() {
        when(learningRepo.findById(99L)).thenReturn(Optional.empty());
        assertFalse(svc.findLearningById(99L).isPresent());
    }

    @Test
    public void findAllLearnings_nonEmpty() {
        LearningEntity e1 = LearningEntity.builder().id(1L).learnerName("A").course(CourseEntity.builder().id(1L).title("T").build()).build();
        LearningEntity e2 = LearningEntity.builder().id(2L).learnerName("B").course(CourseEntity.builder().id(2L).title("T2").build()).build();
        when(learningRepo.findAll()).thenReturn(Arrays.asList(e1, e2));
        List<LearningDto> all = svc.findAllLearnings();
        assertEquals(2, all.size());
    }

    @Test
    public void deleteLearning_notFound() {
        when(learningRepo.existsById(123L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> svc.deleteLearning(123L));
    }

    @Test
    public void updateProgress_newProgress() {
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
    public void updateProgress_invalidPercentage() {
        assertThrows(IllegalArgumentException.class, () -> svc.updateProgress(1L, -1));
        assertThrows(IllegalArgumentException.class, () -> svc.updateProgress(1L, 101));
    }

    @Test
    public void createCourse_and_updateCourse_and_list() {
        CourseDto dto = CourseDto.builder().title("T").description("D").level("B").published(true).build();
        when(courseRepo.save(any())).thenAnswer(i -> {
            CourseEntity e = i.getArgument(0);
            e.setId(200L);
            return e;
        });
        CourseDto created = svc.createCourse(dto);
        assertEquals(200L, created.getId());

        CourseEntity existing = CourseEntity.builder().id(200L).title("Old").build();
        when(courseRepo.findById(200L)).thenReturn(Optional.of(existing));
        when(courseRepo.save(existing)).thenReturn(existing);
        CourseDto u = svc.updateCourse(200L, dto);
        assertEquals("T", u.getTitle());

        when(courseRepo.findByPublishedTrue()).thenReturn(Collections.singletonList(existing));
        List<CourseDto> published = svc.listCourses(true);
        assertEquals(1, published.size());
    }

    @Test
    public void findCourse_absent() {
        when(courseRepo.findById(999L)).thenReturn(Optional.empty());
        assertFalse(svc.findCourse(999L).isPresent());
    }
}
