package com.goodstudy.learning.controller;

import com.goodstudy.learning.service.LearningAdvancedService;
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

public class LearningAdvancedControllerTest {
    private MockMvc mvc;
    @Mock private LearningAdvancedService svc;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LearningAdvancedController c = new LearningAdvancedController(svc);
        mvc = MockMvcBuilders.standaloneSetup(c).build();
    }

    @Test
    public void transfer_ok() throws Exception {
        when(svc.transferEnrollment(1L,2L)).thenReturn(LearningDto.builder().id(1L).learnerName("A").build());
        mvc.perform(post("/api/learning/advanced/1/transfer/2")).andExpect(status().isOk());
    }

    @Test
    public void withdraw_ok() throws Exception {
        doNothing().when(svc).withdraw(5L);
        mvc.perform(post("/api/learning/advanced/5/withdraw")).andExpect(status().isNoContent());
    }

    @Test
    public void byLearner_ok() throws Exception {
        when(svc.findByLearner("Bob")).thenReturn(List.of(LearningDto.builder().id(3L).learnerName("Bob").build()));
        mvc.perform(get("/api/learning/advanced/by-learner/Bob")).andExpect(status().isOk());
    }
}
