package com.goodstudy.learning.controller;

import com.goodstudy.learning.service.CourseExtendedService;
import com.goodstudy.learning.dto.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourseAdminControllerTest {

    private MockMvc mvc;
    @Mock private CourseExtendedService svc;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        CourseAdminController c = new CourseAdminController(svc);
        mvc = MockMvcBuilders.standaloneSetup(c).build();
    }

    @Test
    public void search_returnsPaged() throws Exception {
        CourseDto d = CourseDto.builder().id(1L).title("T").build();
        when(svc.searchCourses(any())).thenReturn(PagedResult.<CourseDto>builder().items(List.of(d)).total(1).page(0).size(10).build());
        mvc.perform(post("/api/admin/courses/search").contentType("application/json").content(mapper.writeValueAsString(new CourseSearchRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void stats_ok() throws Exception {
        when(svc.courseStats()).thenReturn(Map.of("total", 2L, "published",1L, "unpublished",1L));
        mvc.perform(get("/api/admin/courses/stats")).andExpect(status().isOk());
    }

    @Test
    public void bulk_ok() throws Exception {
        when(svc.bulkCreateCoursesAsync(any())).thenReturn(java.util.concurrent.CompletableFuture.completedFuture(Map.of("success",2,"failed",0)));
        mvc.perform(post("/api/admin/courses/bulk").contentType("application/json").content(mapper.writeValueAsString(List.of(new CourseDto(), new CourseDto()))))
                .andExpect(status().isOk());
    }

    @Test
    public void archive_ok() throws Exception {
        doNothing().when(svc).archiveCourse(5L);
        mvc.perform(post("/api/admin/courses/5/archive")).andExpect(status().isNoContent());
    }
}
