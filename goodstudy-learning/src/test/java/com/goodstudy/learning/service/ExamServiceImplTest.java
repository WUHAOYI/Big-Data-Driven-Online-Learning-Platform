package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.service.impl.ExamServiceImpl;
import com.goodstudy.learning.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExamServiceImplTest {
    @Mock private ExamRepository examRepo;
    @Mock private CourseRepository courseRepo;
    private ExamServiceImpl svc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        svc = new ExamServiceImpl(examRepo, courseRepo);
    }

    @Test
    public void createExam_success() {
        CourseEntity c = CourseEntity.builder().id(1L).title("X").build();
        when(courseRepo.findById(1L)).thenReturn(Optional.of(c));
        when(examRepo.save(any())).thenAnswer(i -> { ExamEntity e = i.getArgument(0); e.setId(11L); return e; });
        ExamDto dto = ExamDto.builder().courseId(1L).title("Exam 1").durationMinutes(60).build();
        ExamDto r = svc.createExam(dto);
        assertEquals(11L, r.getId());
        assertEquals("Exam 1", r.getTitle());
    }

    @Test
    public void createExam_courseNotFound() {
        when(courseRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.createExam(ExamDto.builder().courseId(2L).title("x").build()));
    }

    @Test
    public void listByCourse_returnsList() {
        ExamEntity e = ExamEntity.builder().id(5L).title("E").build();
        when(examRepo.findByCourseId(3L)).thenReturn(Collections.singletonList(e));
        List<ExamDto> out = svc.listByCourse(3L);
        assertEquals(1, out.size());
        assertEquals(5L, out.get(0).getId());
    }
}
