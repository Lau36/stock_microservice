package com.example.stock_microservice.infrastructure.configuration.security;

import com.example.stock_microservice.infrastructure.configuration.Roles;
import com.example.stock_microservice.infrastructure.configuration.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html").permitAll()
                        //Categories
                        .requestMatchers(HttpMethod.POST,"/categories").hasAuthority(Roles.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET,"/categories/").hasAnyAuthority(Roles.ROLE_ADMIN, Roles.ROLE_AUX_BODEGA)
                        //Brands
                        .requestMatchers(HttpMethod.POST,"/brands").hasAuthority(Roles.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET,"/brands/").hasAnyAuthority(Roles.ROLE_ADMIN, Roles.ROLE_AUX_BODEGA)
                        //Items
                        .requestMatchers(HttpMethod.POST,"/Item").hasAuthority(Roles.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PATCH,"/Item").hasAuthority(Roles.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET,"/Item").hasAnyAuthority(Roles.ROLE_ADMIN, Roles.ROLE_AUX_BODEGA)
                        .requestMatchers(HttpMethod.GET,"/Item/InStock ").hasAnyAuthority(Roles.ROLE_CUSTOMER)
                        .requestMatchers(HttpMethod.GET,"/Item/Categories").hasAnyAuthority(Roles.ROLE_CUSTOMER)
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
