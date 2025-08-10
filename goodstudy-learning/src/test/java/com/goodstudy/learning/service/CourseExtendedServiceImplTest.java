package com.goodstudy.learning.service;

import com.goodstudy.learning.service.impl.CourseExtendedServiceImpl;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.dto.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseExtendedServiceImplTest {

    @Mock private CourseRepository courseRepo;
    private CourseExtendedServiceImpl svc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        svc = new CourseExtendedServiceImpl(courseRepo);
    }

    @Test
    public void search_empty() {
        when(courseRepo.findAll()).thenReturn(List.of());
        var res = svc.searchCourses(new CourseSearchRequest());
        assertEquals(0, res.getItems().size());
    }

    @Test
    public void stats_nonEmpty() {
        CourseEntity c1 = CourseEntity.builder().id(1L).published(true).build();
        CourseEntity c2 = CourseEntity.builder().id(2L).published(false).build();
        when(courseRepo.findAll()).thenReturn(List.of(c1, c2));
        var map = svc.courseStats();
        assertEquals(2L, map.get("total"));
        assertEquals(1L, map.get("published"));
    }

    @Test
    public void bulk_async() throws Exception {
        when(courseRepo.save(any())).thenAnswer(i -> { CourseEntity e = i.getArgument(0); e.setId(100L); return e; });
        List<CourseDto> dtos = new ArrayList<>();
        dtos.add(CourseDto.builder().title("A").build());
        dtos.add(CourseDto.builder().title("B").build());
        Future<Map<String,Integer>> f = svc.bulkCreateCoursesAsync(dtos);
        var out = f.get();
        assertEquals(2, out.get("success"));
    }
}
