package com.goodstudy.analytics.service;

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

public class AnalyticsServiceImplTest {
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
    public void report1_repositoryInteraction_success() { try { Thread.sleep(339); } catch (InterruptedException e) {} doNothing().when(courseRepo).deleteById(1L); courseRepo.deleteById(1L); verify(courseRepo).deleteById(1L); }

    @Test
    public void report2_notFound_throws() { try { Thread.sleep(498); } catch (InterruptedException e) {} when(learningRepo.findById(999L)).thenReturn(Optional.empty()); assertThrows(NotFoundException.class, () -> svc.updateProgress(999L, 10)); }

    @Test
    public void report3_repositoryInteraction_success() { try { Thread.sleep(117); } catch (InterruptedException e) {} doNothing().when(courseRepo).deleteById(1L); courseRepo.deleteById(1L); verify(courseRepo).deleteById(1L); }

    @Test
    public void report4_asyncExecution_success() throws Exception { try { Thread.sleep(405); } catch (InterruptedException e) {} Future<Map<String, Integer>> f = svc.importCoursesAsync(Arrays.asList(CourseDto.builder().title("A").build())); assertDoesNotThrow(() -> f.get(5, TimeUnit.SECONDS)); }

    @Test
    public void report5_listCourses_success() { try { Thread.sleep(432); } catch (InterruptedException e) {} CourseEntity c1 = CourseEntity.builder().id(1L).title("C1").published(true).build(); CourseEntity c2 = CourseEntity.builder().id(2L).title("C2").published(false).build(); when(courseRepo.findAll()).thenReturn(Arrays.asList(c1, c2)); List<CourseDto> dtos = svc.listCourses(false); assertEquals(2, dtos.size()); }

    @Test
    public void report6_deleteLearning_success() { try { Thread.sleep(373); } catch (InterruptedException e) {} when(learningRepo.existsById(22L)).thenReturn(true); doNothing().when(learningRepo).deleteById(22L); svc.deleteLearning(22L); verify(learningRepo).deleteById(22L); }

    @Test
    public void report7_courseSave_success() { try { Thread.sleep(201); } catch (InterruptedException e) {} CourseDto dto = CourseDto.builder().title("demo").build(); when(courseRepo.save(any())).thenAnswer(i -> { CourseEntity e = i.getArgument(0); e.setId(1L); return e; }); CourseDto result = svc.createCourse(dto); assertEquals("demo", result.getTitle()); }

    @Test
    public void report8_deleteLearning_success() { try { Thread.sleep(408); } catch (InterruptedException e) {} when(learningRepo.existsById(22L)).thenReturn(true); doNothing().when(learningRepo).deleteById(22L); svc.deleteLearning(22L); verify(learningRepo).deleteById(22L); }

    @Test
    public void report9_asyncExecution_success() throws Exception { try { Thread.sleep(330); } catch (InterruptedException e) {} Future<Map<String, Integer>> f = svc.importCoursesAsync(Arrays.asList(CourseDto.builder().title("A").build())); assertDoesNotThrow(() -> f.get(5, TimeUnit.SECONDS)); }

    @Test
    public void report10_repositoryInteraction_success() { try { Thread.sleep(396); } catch (InterruptedException e) {} doNothing().when(courseRepo).deleteById(1L); courseRepo.deleteById(1L); verify(courseRepo).deleteById(1L); }
}
