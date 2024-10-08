package com.sesasis.donusum.yok.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket getDocketInstanceEx1() {
        final Set<String> produces = new HashSet<String>();
        produces.add(MediaType.APPLICATION_JSON_VALUE);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("Emeklilik API")
                        .description("Emeklilik Web and Mobile integration")
                        .version("0.0.1")
                        .license("MIT")
                        .licenseUrl("https://opensource.org/licenses/MIT")
                        .build())
                .produces(produces).consumes(produces)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }

}