package com.example.restapi.filter;

import com.example.restapi.props.AppProperties;
import com.example.restapi.service.auth.AuthService;
import com.example.restapi.utils.JWTUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Slf4j
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    private AuthService authService;
    @Autowired
    private AppProperties props;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader(props.getAuthHeaderName());
        if(JWTUtils.isTokenFormatValid(authToken)) {
            UserDetails userDetails = authService.validateToken(authToken);
            // adding authenticated user into the spring security context
            SecurityContextHolder.getContext().setAuthentication(
                    this.getUsernamePasswordAuthenticationToken(request, userDetails)
            );
        }
        log.info("{} Triggered for URI {}", this.getClass().getName(), request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken (
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        return usernamePasswordAuthenticationToken;
    }
}
