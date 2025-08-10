package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import java.util.*;

public interface ExamService {
    ExamDto createExam(ExamDto dto);
    Optional<ExamDto> findExam(Long id);
    List<ExamDto> listByCourse(Long courseId);
    void deleteExam(Long id);
}
