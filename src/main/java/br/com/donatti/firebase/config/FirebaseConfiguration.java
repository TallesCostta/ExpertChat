package br.com.donatti.firebase.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 17:01:56
 */
@Configuration
class FirebaseConfiguration
{
    @Bean
    FirebaseDatabase firebaseDatabase() throws IOException
    {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://expert-chat-3b94e-default-rtdb.firebaseio.com")
                .build();

        if (FirebaseApp.getApps().isEmpty())
        {
            FirebaseApp.initializeApp(options);
        }

        return FirebaseDatabase.getInstance();
    }

}
