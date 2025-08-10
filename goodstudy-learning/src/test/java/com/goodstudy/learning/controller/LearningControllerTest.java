package com.goodstudy.learning.controller;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.service.LearningService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LearningControllerTest {

    @Mock
    private LearningService svc;

    private LearningController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new LearningController(svc);
    }

    @Test
    public void create_returnsCreatedResponse() {
        LearningDto dto = LearningDto.builder().id(10L).learnerName("A").build();
        when(svc.createLearning("A", 1L)).thenReturn(dto);
        ResponseEntity<LearningDto> resp = controller.create("A", 1L);
        assertEquals(201, resp.getStatusCodeValue());
        assertTrue(resp.getHeaders().getLocation().toString().contains("/api/learning/10"));
    }

    @Test
    public void get_whenFound() {
        LearningDto dto = LearningDto.builder().id(5L).learnerName("Z").build();
        when(svc.findLearningById(5L)).thenReturn(Optional.of(dto));
        ResponseEntity<LearningDto> resp = controller.get(5L);
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("Z", resp.getBody().getLearnerName());
    }

    @Test
    public void get_whenNotFound() {
        when(svc.findLearningById(7L)).thenReturn(Optional.empty());
        ResponseEntity<LearningDto> resp = controller.get(7L);
        assertEquals(404, resp.getStatusCodeValue());
    }

    @Test
    public void list_returnsAll() {
        when(svc.findAllLearnings()).thenReturn(Collections.emptyList());
        assertEquals(0, controller.list(null).size());
    }

    @Test
    public void updateProgress_callsService() {
        ProgressDto p = ProgressDto.builder().id(1L).percentage(10).build();
        when(svc.updateProgress(1L, 10)).thenReturn(p);
        ResponseEntity<ProgressDto> resp = controller.updateProgress(1L, 10);
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(10, resp.getBody().getPercentage());
    }

    @Test
    public void delete_callsService() {
        doNothing().when(svc).deleteLearning(3L);
        ResponseEntity<Void> r = controller.delete(3L);
        assertEquals(204, r.getStatusCodeValue());
        verify(svc, times(1)).deleteLearning(3L);
    }
}
