package com.clinica.agendamento.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Redirecionar com base nos papéis do usuário
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/painel-admin");
        } else if (roles.contains("ROLE_FUNCIONARIO")) {
            response.sendRedirect("/painel-funcionario");
        } else if (roles.contains("ROLE_PACIENTE")) {
            response.sendRedirect("/painel-paciente");
        } else {
            // Caso o usuário não tenha nenhum dos papéis esperados, redireciona para a página principal (/)
            response.sendRedirect("/");
        }
    }
}
