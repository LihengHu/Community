package com.ancientmoon.newcommunity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//用在方法上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
public @interface LoginRequired {
}
