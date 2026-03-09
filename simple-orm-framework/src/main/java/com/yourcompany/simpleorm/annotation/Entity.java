package com.yourcompany.simpleorm.annotation; // Define the package for annotations

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) // Ensures the annotation is available at runtime for reflection
@Target(ElementType.TYPE)          // Specifies that this annotation can only be applied to types (classes, interfaces, enums)
public @interface Entity {
    
}