package com.projetws.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumerate the different roles a employee may have
 */
public enum UserRole implements GrantedAuthority
{
	/**
     * School administrator
     */
    ROLE_SCHOOLADMIN,
    /**
     * Photographer
     */
    ROLE_PHOTOGRAPHER,
    /**
     * Parent
     */ 
    ROLE_PARENT,
    /**
     * Default role
     */
    ROLE_DEFAULT;

    /**
     * @return the user authority
     */
    @Override
    public String getAuthority()
    {
        return this.name();
    }
}
