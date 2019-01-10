package com.projetws.tools;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.projetws.model.EmployeeRole;

public class SecurityTools
{
	/**
	 * 
	 * @param consult ("ROLE_CONSULT","ROLE_EDITOR","ROLE_ADMIN")
	 * @return true/false
	 */
	public static boolean hasRole(String role)
	{
		for(GrantedAuthority userRole : SecurityContextHolder.getContext().getAuthentication().getAuthorities())
		{
			if(userRole.toString().equals(role))
				return true;
		}
		return false;
	}
	
	/**
     * 
     * @return true/false
     */
    public boolean isConnected()
    {
        return (SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
    }
}
