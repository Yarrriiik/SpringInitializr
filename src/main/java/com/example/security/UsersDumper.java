package com.example.security;

import com.example.domain.AppUser;
import com.example.repository.AppUserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UsersDumper implements ApplicationRunner {
    private final AppUserRepository users;

    public UsersDumper(AppUserRepository users) { this.users = users; }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("USERS DUMP START");
        for (AppUser u : users.findAll()) {
            System.out.println("id=" + u.getId() +
                    " username=" + u.getUsername() +
                    " role=" + u.getRole() +
                    " enabled=" + u.isEnabled() +
                    " pass='" + u.getPassword() + "'");
        }
        System.out.println("USERS DUMP END");
    }
}
