package com.marcus.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * - We typically find ourselves in a situation where we protect different
 *   methods using the same security configuration
 * - We can directly use the @IsViewer annotation to secure our method
 * - This adds more semantics and decouple our business logic from the
 *   security framework
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('VIEWER')")
public @interface IsViewer {
}
