package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.service.impl.CertificateServiceImpl;
import com.goodstudy.learning.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CertificateServiceImplTest {
    @Mock private CertificateRepository certRepo;
    @Mock private LearningRepository learningRepo;
    @Mock private ProgressRepository progressRepo;
    private CertificateServiceImpl svc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        svc = new CertificateServiceImpl(certRepo, learningRepo,progressRepo);
    }

    @Test
    public void issueCertificate_success() {
        LearningEntity l = LearningEntity.builder()
                .id(12L)
                .learnerName("CertUser")
                .course(CourseEntity.builder()
                        .id(99L).title("Java").description("desc").level("beginner").build())
                .build();

        ProgressEntity p = ProgressEntity.builder()
                .id(77L)
                .learning(l)
                .percentage(100) // 满进度
                .build();

        when(learningRepo.findById(12L)).thenReturn(Optional.of(l));
        when(progressRepo.findByLearningId(12L)).thenReturn(List.of(p));
        when(certRepo.save(any())).thenAnswer(i -> {
            CertificateEntity c = i.getArgument(0);
            c.setId(555L);
            return c;
        });

        CertificateDto dto = svc.issueCertificate(12L, "Completed Java");
        assertEquals(555L, dto.getId());
        assertTrue(dto.getCode().startsWith("CERT-12-"));
        assertEquals("CertUser", dto.getRecipient());
    }

    @Test
    public void issueCertificate_learningNotFound() {
        when(learningRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> svc.issueCertificate(99L, "T"));
    }
}
