package com.product.restful.security;

import com.product.restful.service.CustomUserDetailsService;
import com.product.restful.service.impl.CustomUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsServiceImpl customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsServiceImpl customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            // ambil token dari setiap request user
            String jwt = getJwtFromRequest(request);

            // validasi jwt dari request apakah sama dengan jwt yang diterbitkan oleh aplikasi
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {

                // ambil user id
                String userId = jwtTokenProvider.getUserIdFromJWT(jwt);

                // ambil user detail by id
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);

                // autentikasi token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception ex) {
            LOGGER.error("Could not set user authentication in security context", ex);
        }

        // lalukan filter
        filterChain.doFilter(request, response);
    }

    // ambil token dari request user
    private String getJwtFromRequest(HttpServletRequest request) {
        // cek apakah request mengandung header Authorization
        String bearerToken = request.getHeader("Authorization");

        // jika memiliki header Authorization dan isinya diawali dengan karakter Bearer, maka ambil tokennya
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        // request tidak mengandung header Authorization
        return null;
    }

}
