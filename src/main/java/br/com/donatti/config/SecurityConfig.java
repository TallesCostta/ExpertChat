package br.com.donatti.config;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import br.com.donatti.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 08:14:06
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig
{
    @Value("${jwt.public.key}")
    private String appKey;

    @Value("${jwt.private.key}")
    private String appPub;

    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/expertchat/login")
                        .permitAll().requestMatchers(HttpMethod.POST, "/expertchat/usuario/cadastro").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable()).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder()
    {
        try
        {
            return NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
            
            return null;
        }
    }

    @Bean
    JwtEncoder jwtEncoder()
    {
        try
        {
            return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(new RSAKey.Builder(getPublicKey()).privateKey(getPrivateKey()).build())));
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
            
            return null;
        }
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 13/12/2024 - 11:26:34
     * @return
     * @throws Exception
     */
    protected RSAPublicKey getPublicKey() throws Exception
    {
        if (publicKey == null)
        {
            String publicKeyPEM = appKey
                    .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            
            publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyPEM)));
        }

        return publicKey;
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 13/12/2024 - 11:26:40
     * @return
     * @throws Exception
     */
    protected RSAPrivateKey getPrivateKey() throws Exception
    {
        if (privateKey == null)
        {
            String privateKeyPEM = appPub
                    .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            
            privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPEM)));
        }

        return privateKey;
    }
}
