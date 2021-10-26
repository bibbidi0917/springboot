package com.koscom.springboot.config.auth.login;

import com.koscom.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { //기준
        //메소드의 파라미터 어노테이션이 @LoginUser 인지
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        //클래서의 타입이 SessionUser인지
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        //둘다 true인 경우 아래 액션을 하려고 함.
        return isLoginUserAnnotation && isUserClass;
    }

    @Override //액션
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
