package com.revo.myboard.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.annotation.Secured;

/*
 * Secured And Permitted For User And Higher Annotation
 * 
 * Created By Revo
 */

@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Secured({"ROLE_USER","ROLE_MODERATOR","ROLE_ADMIN"})
public @interface ForUser {}