package me.daniel.taskapi.global.auth;

import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.user.exception.FailureAuthenticationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class AuthConfiguration implements WebMvcConfigurer {

    private final TokenDecodeService tokenService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(tokenService));
    }

    @RequiredArgsConstructor
	static class AuthenticationInterceptor extends HandlerInterceptorAdapter {
        private static final String HEADER_NAME = "authorization";
        private static final String TOKEN_TYPE = "BEARER";

        private final TokenDecodeService tokenService;

        private String[] validateToken(String authorization) {
            if (StringUtils.isEmpty(authorization)) throw new EmptyTokenException();
            String[] body = authorization.split("\\s+");
            if (!body[0].toUpperCase().contentEquals(TOKEN_TYPE)) throw new InvalidTokenTypeException();
            if (StringUtils.isEmpty(body[1])) throw new EmptyTokenException();
            return body;
        }

        /**
         * @param request - HTTP Servlet Reqeust Object
         * @param response - HTTP Servlet Response Object
         * @param handler - Controller HandlerMethod Object
         * @return boolean
         * @throws EmptyTokenException - 토큰 본문 혹은 토큰 payload 부분에 대한 예외
         * @throws InvalidTokenTypeException - 토큰 타입(`Bearer`)에 대한 예외
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (!(handler instanceof HandlerMethod)) return true;
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            if (!(handlerMethod).hasMethodAnnotation(Authorization.class)) return true;
            String authorization = request.getHeader(HEADER_NAME);
            String[] body = validateToken(authorization);
            request.setAttribute("payload", tokenService.decode(body[1]));
            return true;
        }



    }
}
