package br.com.donatti.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.donatti.utils.ConstantsUtils;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 01:28:14
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

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
                .allowedOrigins(ConstantsUtils.URL_EXPERT_CHAT, ConstantsUtils.URL_EXPERT_CHAT_LOCALHOST)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
