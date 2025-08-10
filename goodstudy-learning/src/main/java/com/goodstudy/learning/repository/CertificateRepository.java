package com.goodstudy.learning.repository;

import com.goodstudy.learning.model.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {
    List<CertificateEntity> findByLearningId(Long learningId);
}
