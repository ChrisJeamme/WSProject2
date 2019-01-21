package com.projetws.tools;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityTools
{
	/**
	 * Verification if the connected user has the desired role
	 * @param role ( ROLE_SCHOOLADMIN, ROLE_PHOTOGRAPHER, ROLE_PARENT, ROLE_DEFAULT )
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
	 * Generate roles list
	 * @param rolesToAdd dynamic parameter of roles
	 * @return null if incorrect, the list if correct
	 */
	public static List<String> generateRolesWith(String ... rolesToAdd)
	{
		List<String> roles = new ArrayList<>();
		if(rolesToAdd==null)
			return null;
		for(String role : rolesToAdd)
		{
			if(role==null)
				return null;
			roles.add(role);
		}
		return roles;
	}
	
	/**
     * Detection if the user is connected
     * @return true/false
     */
    public boolean isConnected()
    {
        return (SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
    }
}
