package org.twt.ts.filter;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.twt.ts.utils.JwtUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {


    @Value("${jwt.name}")
    private String name;

    @Value("${jwt.prefix}")
    private String prefix;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationString = request.getHeader(name);
        if (!StringUtils.hasText(authorizationString)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationString.replace(prefix + " ", "");
        Claims userInfo = jwtUtil.getTokenInfo(token);
        if (Objects.isNull(userInfo)) {
            filterChain.doFilter(request, response);
            return;
        }

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userInfo.get("permission").toString());
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(simpleGrantedAuthority);
        
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        userInfo, null, authorityList
                )
        );

        filterChain.doFilter(request, response);
    }
}
