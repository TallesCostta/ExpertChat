package br.com.donatti.cors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 01:28:14
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    /**
     * 
     * (non-Javadoc)
     *
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 01:28:52
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
