package com.shivansh.code.hospitalManagement.security;

import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("incoming request: {}", request.getRequestURI());
            String path = request.getRequestURI();
            if (
                    path.startsWith("/auth/refresh") ||
                            path.startsWith("/auth/login") ||
                            path.startsWith("/auth/signup") ||
                            path.startsWith("/swagger-ui") ||
                            path.startsWith("/v3/api-docs") ||
                            path.startsWith("/oauth2") ||
                            path.startsWith("/login/oauth2")
            ) {
                filterChain.doFilter(request, response);
                return;
            }
            log.info("JWT filter Called");
            final String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestTokenHeader.substring(7);
            String userName = authUtil.getUsernameFromToken(token);
            if (userName != null) {
                User user = userRepository.findByUsername(userName).orElseThrow();
                System.out.println("Authorities from jwtfilter "+user.getAuthorities());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println(SecurityContextHolder.getContext()
                        .getAuthentication());
            }
            filterChain.doFilter(request, response);
        }catch (Exception ex){
             log.info("Exception in JWT filter: {}", ex.getMessage());
             handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
