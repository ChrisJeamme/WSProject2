package com.projetws.tools;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.projetws.controller.SchoolManagementController;
import com.projetws.model.UserRepository;

public class SecurityTools
{
	private static final Logger logger = LogManager.getLogger(SecurityTools.class);
	
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

    /**
     * Verify if the email is not already used
     * @param userRepository JPA Repository of users
     * @param emailParent email of the parents
     * @return true if not used, false if already used
     */
	public static boolean emailNotUsedVerification(UserRepository userRepository, String emailParent)
	{
		if(userRepository==null || emailParent == null)
		{
			logger.error("(emailNotUsedVerification) userRepository or emailParent null");
			return false;
		}
		return !userRepository.existsByEmail(emailParent);
	}

	/**
	 * Give the relative path to the previous page (which call this request)
	 * @param request Request call by the page we want to have the path
	 * @return the absolute path
	 */
	public static String samePage(HttpServletRequest request)
	{
		String path[] = request.getHeader("referer").split(request.getHeader("host"));
		return path[1];
	}

	/**
	 * Display an error in the logger, in the model (to display it in the page) and redirect to redirectPage
	 * @param m Model
	 * @param errorMessage Error message
	 * @param redirectPage Page to redirect after display the error
	 * @return redirectPage
	 */
	public static String displayErrorAndRedirect(Model m, String errorMessage, String redirectPage)
	{
		logger.error(errorMessage);
		m.addAttribute("error",errorMessage);
		return "redirect:"+redirectPage;
	}
}
