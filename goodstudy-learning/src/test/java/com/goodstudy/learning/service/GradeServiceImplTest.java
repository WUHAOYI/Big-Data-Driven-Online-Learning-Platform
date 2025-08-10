package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.service.impl.GradeServiceImpl;
import com.goodstudy.learning.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GradeServiceImplTest {
    @Mock private GradeRepository gradeRepo;
    @Mock private LearningRepository learningRepo;
    @Mock private ExamRepository examRepo;
    private GradeServiceImpl svc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        svc = new GradeServiceImpl(gradeRepo, learningRepo, examRepo);
    }

    @Test
    public void awardGrade_success() {
        LearningEntity l = LearningEntity.builder().id(2L).learnerName("Stu").build();
        ExamEntity ex = ExamEntity.builder().id(3L).title("Ex").build();
        when(learningRepo.findById(2L)).thenReturn(Optional.of(l));
        when(examRepo.findById(3L)).thenReturn(Optional.of(ex));
        when(gradeRepo.save(any())).thenAnswer(i -> { GradeEntity g = i.getArgument(0); g.setId(99L); return g; });
        GradeDto out = svc.awardGrade(2L, 3L, 85.5);
        assertEquals("B", out.getGradeLetter());
        assertEquals(99L, out.getId());
    }

    @Test
    public void awardGrade_learningNotFound() {
        when(learningRepo.findById(4L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.awardGrade(4L, 1L, 50));
    }

    @Test
    public void computeLetter_edges() {
        when(learningRepo.findById(1L)).thenReturn(Optional.of(LearningEntity.builder().id(1L).learnerName("a").build()));
        when(examRepo.findById(1L)).thenReturn(Optional.of(ExamEntity.builder().id(1L).build()));
        when(gradeRepo.save(any())).thenAnswer(i -> { GradeEntity g = i.getArgument(0); g.setId(1L); return g; });
        GradeDto a = svc.awardGrade(1L,1L,90);
        assertEquals("A", a.getGradeLetter());
        GradeDto f = svc.awardGrade(1L,1L,59.9);
        assertEquals("F", f.getGradeLetter());
    }
}
