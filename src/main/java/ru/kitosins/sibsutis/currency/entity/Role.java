package ru.kitosins.sibsutis.currency.entity;

import org.springframework.security.core.GrantedAuthority;


/**
 * Role enum
 * @author kitosina
 * @version 0.1
 * @see GrantedAuthority
 */
public enum Role implements GrantedAuthority {
    USER,
    SPECIALIST,
    ADMIN;

    /**
     * Automatically generated method for authorities and role system in Spring Security
     * @see Override
     * @see org.springframework.security.authentication.jaas.AuthorityGranter
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
