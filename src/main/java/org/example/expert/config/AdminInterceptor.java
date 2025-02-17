package org.example.expert.config;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final static Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String tokenValue = request.getHeader("Authorization");

        if (tokenValue == null || tokenValue.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT 토큰이 필요합니다.");
            return false;
        }

        try {
            UserRole userRole = UserRole.valueOf(jwtUtil.extractUserRole(tokenValue));

            if (!UserRole.ADMIN.equals(userRole)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 없습니다.");
                return false;
            }

            logger.info("API 요청 URL: {}, API 요청 시각: {}", request.getRequestURL(),
                LocalDateTime.now());

        } catch (SecurityException | MalformedJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
            return false;
        }
        return true;
    }
}
