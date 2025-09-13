package com.greensphere.userservice.filters;

import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.AuditLog;
import com.greensphere.userservice.repository.AuditLogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuditLoggingFilter extends OncePerRequestFilter {

    private final AuditLogRepository auditLogRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Object userAttr = request.getAttribute("user");
            String actorId = null;
            String actorType = null;
            if (userAttr instanceof AppUser) {
                AppUser u = (AppUser) userAttr;
                actorId = u.getUsername();
                actorType = u.getRoleType();
            }

            AuditLog log = new AuditLog();
            log.setActorId(actorId);
            log.setActorType(actorType);
            log.setAction(request.getMethod() + " " + request.getRequestURI());
            log.setResourceId(null);
            log.setResourceType(null);
            log.setDescription(null);
            log.setOccurredAt(LocalDateTime.now());
            log.setIp(request.getRemoteAddr());

            auditLogRepository.save(log);
        } catch (Exception ignored) {
        }

        filterChain.doFilter(request, response);
    }
}


