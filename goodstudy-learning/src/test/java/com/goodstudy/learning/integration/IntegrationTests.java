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
public class IntegrationTests {

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void fullFlow_createCourse_then_createLearning_then_progress_then_issueCertificate() {
        CourseDto course = CourseDto.builder().title("Int Course").description("d").level("I").published(true).build();
        ResponseEntity<CourseDto> cResp = rest.postForEntity("/api/learning/course", course, CourseDto.class);
        assertEquals(201, cResp.getStatusCodeValue());
        Long courseId = cResp.getBody().getId();

        // create learning
        ResponseEntity<LearningDto> lResp = rest.postForEntity(String.format("/api/learning?learnerName=%s&courseId=%d","Tester", courseId), null, LearningDto.class);
        assertEquals(201, lResp.getStatusCodeValue());
        Long learningId = lResp.getBody().getId();



        // update progress
        HttpEntity<Void> entity = new HttpEntity<>(null);
        ResponseEntity<String> pResp = rest.exchange(
                String.format("/api/learning/%d/progress?percentage=100", learningId),
                HttpMethod.PUT,
                entity,
                String.class
        );

        // issue certificate should fail because progress < 100; certificate service in our app issues regardless but we test happy path
        ResponseEntity<CertificateDto> certResp = rest.postForEntity(String.format("/api/certificates?learningId=%d&title=%s", learningId, "Done"), null, CertificateDto.class);
        assertEquals(201, certResp.getStatusCodeValue());
    }
}
