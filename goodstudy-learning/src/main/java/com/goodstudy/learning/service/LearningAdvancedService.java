package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import java.util.*;

public interface LearningAdvancedService {
    LearningDto transferEnrollment(Long learningId, Long toCourseId);
    void withdraw(Long learningId);
    List<LearningDto> findByLearner(String learnerName);
}
