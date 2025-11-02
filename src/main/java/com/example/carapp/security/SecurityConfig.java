package com.example.carapp.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AppUserDetailsService uds;
    public SecurityConfig(AppUserDetailsService uds) { this.uds = uds; }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/css/**").permitAll()
                        // REST под Basic Auth
                        .requestMatchers("/api/**").authenticated()
                        // HTML-часть как было
                        .requestMatchers(HttpMethod.GET, "/cars", "/cars/search", "/cars/{id}/edit").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cars", "/cars/{id}", "/cars/{id}/delete").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/cars", true)
                        .failureUrl("/login?error")
                )
                .httpBasic(withDefaults()) // <-- включили Basic Auth
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout").permitAll()
                )
                .userDetailsService(uds);
        return http.build();
    }

}
