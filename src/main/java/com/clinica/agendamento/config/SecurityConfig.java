package com.clinica.agendamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/login", "/css/**", "/js/**").permitAll() // Permite acesso a recursos estáticos
                .requestMatchers("/admin/**").hasRole("ADMIN") // Restringe o acesso ao dashboard para ADMIN
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login") // Página de login personalizada
                .permitAll()
                .defaultSuccessUrl("/admin/dashboard") // Redireciona para o dashboard após o login
                .failureUrl("/login?error=true") // Redireciona em caso de falha no login
            )
            .logout((logout) -> logout
                .permitAll()
                .logoutSuccessUrl("/login?logout=true") // Redireciona após o logout
            )
            .exceptionHandling((exception) -> exception
                .accessDeniedPage("/403") // Página de acesso negado
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
            User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}