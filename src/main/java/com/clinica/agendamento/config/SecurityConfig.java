package com.clinica.agendamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", 
                                 "/index", 
                                 "/login-admin", 
                                 "/login-funcionario", 
                                 "/login-paciente",
                                 "/css/**", 
                                 "/img/**", 
                                 "/js/**", 
                                 "/webjars/**").permitAll() // Permite o acesso público às páginas de login e estáticas
                .requestMatchers("/painel-admin").hasRole("ADMIN")
                .requestMatchers("/painel-funcionario").hasRole("FUNCIONARIO")
                .requestMatchers("/painel-paciente").hasRole("PACIENTE")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login-admin").permitAll()  // Página de login principal
                .loginProcessingUrl("/login")           // URL para processar login
                .successHandler(customAuthenticationSuccessHandler())  // Custom handler de sucesso
                .failureUrl("/login-admin?error=true")  // URL de falha genérica
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login-admin?logout=true")  // Página de sucesso de logout
                .permitAll()
            )
            .exceptionHandling()
                .accessDeniedPage("/access-denied");  // Página de acesso negado

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var encoder = passwordEncoder();

        var admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        var funcionario = User.withUsername("func")
                .password(encoder.encode("func123"))
                .roles("FUNCIONARIO")
                .build();

        var paciente = User.withUsername("pac")
                .password(encoder.encode("pac123"))
                .roles("PACIENTE")
                .build();

        return new InMemoryUserDetailsManager(admin, funcionario, paciente);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}
