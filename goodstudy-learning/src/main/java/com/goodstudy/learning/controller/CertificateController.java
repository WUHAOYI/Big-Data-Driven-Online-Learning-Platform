package com.goodstudy.learning.controller;

import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.service.CertificateService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService svc;
    public CertificateController(CertificateService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<CertificateDto> issue(@RequestParam Long learningId, @RequestParam String title) {
        CertificateDto c = svc.issueCertificate(learningId, title);
        return ResponseEntity.created(URI.create("/api/certificates/" + c.getId())).body(c);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> get(@PathVariable Long id) {
        return svc.findCertificate(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/learning/{learningId}")
    public List<CertificateDto> listForLearning(@PathVariable Long learningId) {
        return svc.listCertificatesForLearning(learningId);
    }
}
