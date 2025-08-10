package com.goodstudy.learning.service;

import com.goodstudy.learning.dto.*;
import java.util.*;

public interface CourseService {
    CourseDto createCourse(CourseDto dto);
    CourseDto updateCourse(Long id, CourseDto dto);
    Optional<CourseDto> findCourse(Long id);
    List<CourseDto> listCourses(boolean onlyPublished);
}
