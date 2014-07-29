package com.mozido.channels.nextweb.access;

import com.mozido.channels.nextweb.model.ui.FGroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Alexander Manusovich
 */
@Target({ElementType.TYPE})
public @interface FunctionalGroup {
    FGroup[] value();

    /**
     * Default value Object - means that we shouldn't do anything special. If there will be a class value -
     * user will be resirected for this page if he is trying open component which he is not allowed
     */
    Class ifIsNotAllowedUseInstead() default Object.class;
}
