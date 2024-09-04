package com.greensphere.userservice.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greensphere.userservice.dto.response.DefaultResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.repository.UserRepository;
import com.greensphere.userservice.service.AuthUserDetailsService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
@Order(3)
public class JWTRequestFilter extends OncePerRequestFilter {
    private static final String MDC_UID_KEY = "uid";
    private final AuthUserDetailsService userService;
    private final UserRepository appUserRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            boolean refreshToken = skipRefreshToken(request);

            if (refreshToken) {
                filterChain.doFilter(request, response);
                return;
            }
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = getAuthentication(header, request);
            if (Objects.isNull(authentication)) {
                log.info("JWTRequestFilter:[doFilterInternal] -> Not authenticated. Public request.");
            } else {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (AlgorithmMismatchException e) {
            log.error("JWTRequestFilter:[doFilterInternal] -> Exception: JWT algorithm mismatched");
            DefaultResponse defaultResponse = DefaultResponse.builder().code(ResponseCodeUtil.JWT_TOKEN_VALIDATE_ERROR_CODE).title(ResponseUtil.FAILED).message(ResponseUtil.INVALID_CREDENTIAL).build();
            generateErrorResponse(response, defaultResponse);
        } catch (SignatureVerificationException e) {
            log.error("JWTRequestFilter:[doFilterInternal] -> Exception: JWT signature verification failed");
            DefaultResponse defaultResponse = DefaultResponse.builder().code(ResponseCodeUtil.JWT_TOKEN_VALIDATE_ERROR_CODE).title(ResponseUtil.FAILED).message(ResponseUtil.INVALID_CREDENTIAL).build();
            generateErrorResponse(response, defaultResponse);
        } catch (TokenExpiredException e) {
            log.error("JWTRequestFilter:[doFilterInternal] -> Exception: JWT expired");
            DefaultResponse defaultResponse = DefaultResponse.builder().code(ResponseCodeUtil.JWT_TOKEN_EXPIRED_ERROR_CODE).title(ResponseUtil.FAILED).message(ResponseUtil.INVALID_CREDENTIAL).build();
            generateErrorResponse(response, defaultResponse);
        } catch (InvalidClaimException e) {
            log.error("JWTRequestFilter:[doFilterInternal] -> Exception: JWT claim not valid");
            DefaultResponse defaultResponse = DefaultResponse.builder().code(ResponseCodeUtil.JWT_TOKEN_VALIDATE_ERROR_CODE).title(ResponseUtil.FAILED).message(ResponseUtil.INVALID_CREDENTIAL).build();
            generateErrorResponse(response, defaultResponse);
        } catch (JWTVerificationException e) {
            log.error("JWTRequestFilter:[doFilterInternal] -> Exception: JWT verification failed");
            DefaultResponse defaultResponse = DefaultResponse.builder().code(ResponseCodeUtil.JWT_TOKEN_VALIDATE_ERROR_CODE).title(ResponseUtil.FAILED).message(ResponseUtil.INVALID_CREDENTIAL).build();
            generateErrorResponse(response, defaultResponse);
        } catch (Exception e) {
            log.error("JWTRequestFilter:[doFilterInternal] -> Exception: " + e.getMessage(), e);
            DefaultResponse defaultResponse = DefaultResponse.builder().code(ResponseCodeUtil.JWT_TOKEN_VALIDATE_ERROR_CODE).title(ResponseUtil.FAILED).message(ResponseUtil.INVALID_CREDENTIAL).build();
            generateErrorResponse(response, defaultResponse);
        } finally {
            MDC.remove(MDC_UID_KEY);
            request.removeAttribute("JWTRequestFilter.FILTERED");
        }
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    public UsernamePasswordAuthenticationToken getAuthentication(String token, HttpServletRequest request) {
        if (token != null) {
            // parse the token.
            String username = JWT.require(Algorithm.HMAC512(secretKey.getBytes())).build().verify(token.replace("Bearer ", "")).getSubject();

            if (username != null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                AppUser appUser = appUserRepository.findAppUserByUsername(username);
                if (appUser != null) {

                    request.setAttribute("user", appUser);
                    MDC.put(MDC_UID_KEY, appUser.getUsername());
                }
                return usernamePasswordAuthenticationToken;
            }
            return null;
        }
        return null;
    }

    private boolean skipRefreshToken(HttpServletRequest httpServletRequest) {
        String[] regs = {"/user/refresh-token"};
        Matcher matcher;
        for (String pathExpr : regs) {
            matcher = Pattern.compile(pathExpr).matcher(httpServletRequest.getServletPath());
            if (matcher.find()) {
                log.info("Request: PATH: " + httpServletRequest.getServletPath());
                return true;
            }
        }
        return false;
    }

    public void generateErrorResponse(HttpServletResponse response, DefaultResponse defaultResponse) throws IOException {
        generateErrorResponse(response, defaultResponse, HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void generateErrorResponse(HttpServletResponse response, DefaultResponse defaultResponse, int httpStatus) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatus);
        writer.print(new ObjectMapper().writeValueAsString(defaultResponse));
    }

}
