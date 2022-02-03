package com.revo.myboard.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.annotation.Secured;

/*
 * Secured And Permitted For Admin And Higher Group
 * 
 * Created By Revo
 */

@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Secured("ROLE_ADMIN")
public @interface ForAdmin {}