package com.mozido.channels.util;

import com.mangofactory.swagger.authorization.AuthorizationContext;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import com.wordnik.swagger.model.Authorization;
import com.wordnik.swagger.model.AuthorizationScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@EnableSwagger
@Configuration
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;


    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin swaggerSpringMvcPlugin() {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .apiInfo(apiInfo())
                .build();
    }

    /**
     * API Info as it appears on the swagger-ui page
     */
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Channel API",
                "Sample api based on the swagger 1.2 spec",
                "http://en.wikipedia.org/wiki/Terms_of_service",
                "alex.manusovich@mozido.com",
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
        return apiInfo;
    }

    @Bean
    public AuthorizationContext authorizationContext() {
        List<Authorization> authorizations = newArrayList();

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        authorizations.add(new Authorization("oauth2", authorizationScopes));
        return new AuthorizationContext.AuthorizationContextBuilder(authorizations).build();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(500000);
        return multipartResolver;
    }
}