package com.product.restful.configuration;

import com.product.restful.security.RestAccessDeniedHandler;
import com.product.restful.security.JwtAuthenticationEntryPoint;
import com.product.restful.security.JwtAuthenticationFilter;
import com.product.restful.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static com.product.restful.entity.enumerator.RoleName.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final RestAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y,SecureRandom.getInstance("SHA1PRNG"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() throws NoSuchAlgorithmException {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/roles/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers("/api/admin/**").hasRole(ADMIN.name())
                        .requestMatchers("/api/users/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers("/api/products/**").hasAnyRole(ADMIN.name(), MANAGER.name(), USER.name())
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

}
