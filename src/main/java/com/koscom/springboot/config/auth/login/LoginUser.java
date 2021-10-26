package com.koscom.springboot.config.auth.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // (1) 어느테이션이 선언될 수 있는 위치.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // (2)
}

