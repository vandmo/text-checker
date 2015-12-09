/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vandmo.textchecker.maven.rules;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import se.vandmo.textchecker.maven.Fixer;


@Retention(RUNTIME)
@Target(TYPE)
public @interface FixWith {
  Class<? extends Fixer> value();
}
