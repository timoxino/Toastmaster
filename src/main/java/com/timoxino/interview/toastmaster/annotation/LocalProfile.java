package com.timoxino.interview.toastmaster.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "local", matchIfMissing = true)
public @interface LocalProfile {
}
