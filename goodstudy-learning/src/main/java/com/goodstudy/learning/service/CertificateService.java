package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import java.util.*;

public interface CertificateService {
    CertificateDto issueCertificate(Long learningId, String title);
    Optional<CertificateDto> findCertificate(Long id);
    List<CertificateDto> listCertificatesForLearning(Long learningId);
}
