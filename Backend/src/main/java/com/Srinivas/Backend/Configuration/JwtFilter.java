package com.Srinivas.Backend.Configuration;

import com.Srinivas.Backend.Service.interfac.CustomUserDetailsService;
import com.Srinivas.Backend.Utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userEmail ;
        String token;
        String Authheader=request.getHeader("Authorization");

        if (Authheader == null || Authheader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
            token = Authheader.substring(7);
            userEmail = jwtUtils.extractUsername(token);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                if (jwtUtils.isValidToken(token, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken jwttoken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    jwttoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(jwttoken);
                    SecurityContextHolder.setContext(securityContext);
                }


//        filterChain.doFilter(request, response);
    }
        filterChain.doFilter(request, response);
    }
}
