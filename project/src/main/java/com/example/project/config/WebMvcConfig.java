package com.example.project.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //images 폴더의 하위 경로
                /*
                /images
                /images/test
                /images/test/hello 등등
                */

        //localhost/images 로 시작하는 모든 경로의 파일들은 uploadPath 에서 찾아오겠다
        //uploadPath에 이미지가 있어야한다.
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }

    @Bean
    MappingJackson2JsonView jsonView() { //json 객체로 리턴해준다.
        return new MappingJackson2JsonView();
    }


}
