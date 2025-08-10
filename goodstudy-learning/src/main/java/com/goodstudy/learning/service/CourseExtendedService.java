package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import java.util.*;
import java.util.concurrent.Future;

public interface CourseExtendedService {
    PagedResult<CourseDto> searchCourses(CourseSearchRequest req);
    Map<String, Long> courseStats();
    Future<Map<String,Integer>> bulkCreateCoursesAsync(List<CourseDto> dtos);
    void archiveCourse(Long id);
}
