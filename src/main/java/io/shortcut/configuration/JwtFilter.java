package io.shortcut.configuration;

import io.shortcut.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            String username = jwtService.getUsernameFromToken(jwtToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, List.of()); //todo credentials is null because we use token to authenticate
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
