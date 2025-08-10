package com.goodstudy.learning.service;

import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.service.impl.LearningServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LearningServiceEdgeCasesTest {
    @Mock LearningRepository learningRepo;
    @Mock CourseRepository courseRepo;
    @Mock ProgressRepository progressRepo;
    private LearningServiceImpl svc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        svc = new LearningServiceImpl(learningRepo, courseRepo, progressRepo);
    }

    @Test
    public void deleteExisting() {
        when(learningRepo.existsById(1L)).thenReturn(true);
        doNothing().when(learningRepo).deleteById(1L);
        svc.deleteLearning(1L);
        verify(learningRepo, times(1)).deleteById(1L);
    }

    @Test
    public void updateProgress_existingProgress() {
        CourseEntity c = CourseEntity.builder().id(99L).title("T").description("D").level("L").build();
        LearningEntity l = LearningEntity.builder().id(10L).learnerName("Z").course(c).build();
        ProgressEntity p = ProgressEntity.builder().id(2L).learning(l).percentage(10).build();
        when(learningRepo.findById(10L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(10L)).thenReturn(Collections.singletonList(p));
        when(progressRepo.save(any())).thenAnswer(i -> i.getArgument(0));
        var out = svc.updateProgress(10L, 55);
        assertEquals(55, out.getPercentage());
    }
}
