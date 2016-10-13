package me.itache.validation.rule;

/**
 * Rule that have restrictive value to check
 */
public interface RestrictiveRule<V> extends Rule {
    V getRestrictiveValue();
}
