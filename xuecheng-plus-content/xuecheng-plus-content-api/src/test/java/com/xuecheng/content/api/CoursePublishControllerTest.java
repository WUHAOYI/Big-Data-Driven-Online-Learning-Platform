package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.service.CoursePublishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CoursePublishControllerTest.SecurityFreeConfig.class)  // 加载禁用 security 配置
class CoursePublishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoursePublishService coursePublishService;

    @BeforeEach
    void setUp() {
        // 模拟 service 返回值
        CourseBaseInfoDto courseBase = new CourseBaseInfoDto();
        courseBase.setId(123L);
        courseBase.setName("测试课程");

        CoursePreviewDto previewDto = new CoursePreviewDto();
        previewDto.setCourseBase(courseBase);

        Mockito.when(coursePublishService.getCoursePreviewInfo(123L))
                .thenReturn(previewDto);
    }

//    @Test
//    void testGetCoursePreviewInfo() throws Exception {
//        mockMvc.perform(get("/course/whole/2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.courseBase.id").value(123))
//                .andExpect(jsonPath("$.courseBase.name").value("测试课程"));
//    }

    /**
     * 禁用 Spring Security 的配置类
     */
    @Configuration
    @EnableWebSecurity
    static class SecurityFreeConfig extends org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter {
        @Override
        protected void configure(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests().anyRequest().permitAll();  // 所有请求都放行
        }
    }
}
