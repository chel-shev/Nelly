package dev.chel_shev.nelly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableJpaRepositories
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TelegramBotQuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotQuickstartApplication.class, args);
    }
}
