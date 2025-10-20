package com.example.carapp.security;

import com.example.carapp.domain.AppUser;
import com.example.carapp.repository.AppUserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class AdminSeeder implements ApplicationRunner {
    private final AppUserRepository users; private final PasswordEncoder encoder;
    public AdminSeeder(AppUserRepository users, PasswordEncoder encoder) {
        this.users = users; this.encoder = encoder;
    }
    @Override
    public void run(ApplicationArguments args) {
        users.findByUsername("admin").orElseGet(() -> {
            AppUser a = new AppUser("admin", encoder.encode("admin123"), "ROLE_ADMIN");
            return users.save(a);
        });
    }
}
