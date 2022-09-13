package com.example.restapi.config;

import com.example.restapi.filter.JWTRequestFilter;
import com.example.restapi.service.auth.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppUserDetailsService userDetailsService;

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    @Bean
    public JWTRequestFilter jwtRequestFilter() {
        return new JWTRequestFilter();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        return http
//                .csrf( csrf -> csrf.disable())
//                .authorizeRequests( auth -> {
//                    auth.antMatchers("/").permitAll();
//                    auth.antMatchers("/users/**").hasAnyRole(ROLE_USER, ROLE_ADMIN);
//                    auth.antMatchers("/admin").hasRole(ROLE_ADMIN);
//                })
//                .formLogin(Customizer.withDefaults())
//                .build();

        return http.csrf().disable()
                .authorizeRequests( auth -> {
                    auth.antMatchers("/access/token").permitAll();
//                    auth.antMatchers("/admin/**").hasRole(ROLE_ADMIN);
                    auth.anyRequest().authenticated();
                })
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
//                .authenticationEntryPoint(authFailureHandler)
//                .accessDeniedHandler(accessDeniedHandler)
                .and().addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
