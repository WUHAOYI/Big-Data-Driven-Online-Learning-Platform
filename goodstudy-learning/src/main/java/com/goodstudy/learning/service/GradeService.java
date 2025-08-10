package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import java.util.*;

public interface GradeService {
    GradeDto awardGrade(Long learningId, Long examId, double score);
    List<GradeDto> findGradesByLearning(Long learningId);
}
