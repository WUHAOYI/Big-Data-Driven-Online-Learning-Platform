package com.goodstudy.learning.config;

import com.goodstudy.learning.model.*;
import com.goodstudy.learning.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CourseRepository courseRepo;
    private final LearningRepository learningRepo;

    public DataLoader(CourseRepository courseRepo, LearningRepository learningRepo) {
        this.courseRepo = courseRepo;
        this.learningRepo = learningRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // create 50 courses and 200 learnings for test data
        if (courseRepo.count() > 0) return;
        for (int i = 1; i <= 50; i++) {
            CourseEntity c = CourseEntity.builder()
                    .title("Course " + i)
                    .description("Description for course " + i)
                    .level(i % 3 == 0 ? "Advanced" : i % 3 == 1 ? "Beginner" : "Intermediate")
                    .published(i % 2 == 0)
                    .build();
            courseRepo.save(c);
            for (int j = 0; j < 4; j++) {
                LearningEntity l = LearningEntity.builder()
                        .learnerName("Learner-" + i + "-" + j)
                        .course(c)
                        .build();
                learningRepo.save(l);
            }
        }
    }
}
