package br.com.donatti.firebase.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 17:01:56
 */
@Slf4j
@Configuration
class FirebaseConfiguration
{
    @Value("${firebase.service.account.key}")
    private String serviceAccountKey;
    
    @Bean
    FirebaseDatabase firebaseDatabase() throws IOException
    {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(Base64.getDecoder().decode(serviceAccountKey))))
                .setDatabaseUrl("https://expert-chat-3b94e-default-rtdb.firebaseio.com")
                .build();


        if (FirebaseApp.getApps().isEmpty())
        {
            FirebaseApp.initializeApp(options);
            
            log.info("🔗 Connected to database [Firebase Realtime Database]");
        }

        return FirebaseDatabase.getInstance();
    }

}
