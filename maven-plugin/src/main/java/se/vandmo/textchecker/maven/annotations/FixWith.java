package se.vandmo.textchecker.maven.annotations;

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
