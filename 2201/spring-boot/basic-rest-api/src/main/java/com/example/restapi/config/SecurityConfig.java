package com.example.restapi.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager(
                getPreDefinedUserDetailsList()
        );
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf( csrf -> csrf.disable())
                .authorizeRequests( auth -> {
                    auth.antMatchers("/").permitAll();
                    auth.antMatchers("/users/**").hasAnyRole(ROLE_USER, ROLE_ADMIN);
                    auth.antMatchers("/admin").hasRole(ROLE_ADMIN);
                })
                .formLogin(Customizer.withDefaults())
                .build();
    }

    private List<UserDetails> getPreDefinedUserDetailsList() {
        return List.of(
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles(ROLE_USER)
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles(ROLE_ADMIN)
                        .build()
        );
    }
}
