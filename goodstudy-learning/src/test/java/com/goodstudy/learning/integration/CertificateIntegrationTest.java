package com.goodstudy.learning.integration;

import com.goodstudy.learning.LearningApplication;
import com.goodstudy.learning.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LearningApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CertificateIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void cannotIssueCertificate_beforeComplete() {
        CourseDto course = CourseDto.builder().title("C2").description("d").level("L").published(true).build();
        CourseDto c = rest.postForEntity("/api/learning/course", course, CourseDto.class).getBody();
        Long courseId = c.getId();
        LearningDto l = rest.postForEntity(String.format("/api/learning?learnerName=%s&courseId=%d","Stu2", courseId), null, LearningDto.class).getBody();
        Long learningId = l.getId();
        // try to issue certificate -> should return 400
        ResponseEntity<String> certResp = rest.postForEntity(String.format("/api/certificates?learningId=%d&title=%s", learningId, "T"), null, String.class);
        assertEquals(400, certResp.getStatusCodeValue());
        // update progress to 100
        rest.exchange(String.format("/api/learning/%d/progress?percentage=100", learningId), HttpMethod.PUT, new HttpEntity<>(null), String.class);
        ResponseEntity<CertificateDto> certResp2 = rest.postForEntity(String.format("/api/certificates?learningId=%d&title=%s", learningId, "T"), null, CertificateDto.class);
        assertEquals(201, certResp2.getStatusCodeValue());
    }
}
