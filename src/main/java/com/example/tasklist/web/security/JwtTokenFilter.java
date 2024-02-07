    package com.example.tasklist.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.net.http.HttpRequest;

@AllArgsConstructor
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        log.info(bearerToken);
         if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
             bearerToken = bearerToken.substring(7);
             log.info(bearerToken);
         }

         if (bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
             try {
                 Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                 log.info(authentication.toString());
                 if(authentication != null) {
                     SecurityContextHolder.getContext().setAuthentication(authentication);
                     log.info(SecurityContextHolder.getContext().toString());
                 }
             } catch (ResourceNotFoundException ignored) {}
         }
        log.info("This is a doFilter method");
         filterChain.doFilter(servletRequest, servletResponse);
    }
}
