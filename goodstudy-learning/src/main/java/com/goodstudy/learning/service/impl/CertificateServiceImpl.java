package com.goodstudy.learning.service.impl;

import com.goodstudy.learning.service.CertificateService;
import com.goodstudy.learning.dto.*;
import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import com.goodstudy.learning.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;
import java.util.stream.*;
import java.util.UUID;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certRepo;
    private final LearningRepository learningRepo;
    private final ProgressRepository progressRepo;

    public CertificateServiceImpl(CertificateRepository certRepo, LearningRepository learningRepo, ProgressRepository progressRepo) {
        this.certRepo = certRepo;
        this.learningRepo = learningRepo;
        this.progressRepo = progressRepo;
    }

    @Override
    @Transactional
    public CertificateDto issueCertificate(Long learningId, String title) {
        LearningEntity l = learningRepo.findById(learningId).orElseThrow(() -> new NotFoundException("Learning not found: " + learningId));
        // require progress 100 to issue certificate
        int progress = progressRepo.findByLearningId(learningId).stream().findFirst().map(ProgressEntity::getPercentage).orElse(0);
        if (progress < 100) throw new IllegalArgumentException("Cannot issue certificate: progress < 100");
        CertificateEntity c = CertificateEntity.builder()
                .code(generateCode(learningId))
                .recipient(l.getLearnerName())
                .issuedAt(LocalDateTime.now())
                .title(title)
                .learning(l)
                .build();
        CertificateEntity saved = certRepo.save(c);
        return toDto(saved);
    }

    private String generateCode(Long learningId) {
        return "CERT-" + learningId + "-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    @Override
    public Optional<CertificateDto> findCertificate(Long id) {
        return certRepo.findById(id).map(this::toDto);
    }

    @Override
    public List<CertificateDto> listCertificatesForLearning(Long learningId) {
        return certRepo.findByLearningId(learningId).stream().map(this::toDto).collect(Collectors.toList());
    }

    private CertificateDto toDto(CertificateEntity e) {
        return CertificateDto.builder()
                .id(e.getId())
                .code(e.getCode())
                .recipient(e.getRecipient())
                .issuedAt(e.getIssuedAt())
                .title(e.getTitle())
                .learningId(e.getLearning() == null ? null : e.getLearning().getId())
                .build();
    }
}
