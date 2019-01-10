package com.projetws.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumerate the different roles a employee may have
 */
public enum EmployeeRole implements GrantedAuthority
{

    /**
     * Consult data from locations, regions and country
     */
    ROLE_CONSULT,
    /**
     * Edit access rights for employees and jobs and can consult the rest
     */ 
    ROLE_EDITOR,
    /**
     * All rights (CEO)
     */
    ROLE_ALL,
    /**
     * For others
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
