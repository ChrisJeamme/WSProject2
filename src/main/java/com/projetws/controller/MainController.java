package com.projetws.controller;

import java.util.HashSet;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.projetws.model.School;
import com.projetws.model.SchoolClassRepository;
import com.projetws.model.SchoolRepository;
import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.model.UserRole;
import com.projetws.tools.SecurityTools;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Controller
public class MainController
{
	private static final Logger logger = LogManager.getLogger(MainController.class);
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	SchoolRepository schoolRepository;
	@Autowired
	SchoolClassRepository schoolClassRepository;
	
	@RequestMapping("/")
	public String hub()
	{
		return "hubPage";
	}
	
//	@RequestMapping("/error")
//	public String customError(@RequestParam("errorMessage") String errorMessage, Model model)
//	{
//		if(errorMessage!=null)
//			model.addAttribute("errorMessage", errorMessage);
//		return "error";
//	}

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model)
    {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        logger.error(errorMessage);
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
    
    @RequestMapping("/test")
	public String test()
	{
    	logger.info("Adding test entities...");
    	
    	User u1;
    	
    	if(userRepository.existsByEmail("jean.jacques@bourdin.fr"))
    	{
    		logger.error("Test users already exists");
    		u1 = userRepository.findByEmail("jean.jacques@bourdin.fr");
    	}
    	else
    	{
    		u1 = new User();
        	u1.setEmail("jean.jacques@bourdin.fr");
        	u1.setFirstName("Jean-Jacques");
        	u1.setLastName("Bourdin");
        	u1.setUserName("bourdin");
        	u1.setPassword(new BCryptPasswordEncoder().encode("bourdin"));
        	HashSet<UserRole> roles = new HashSet<UserRole>();
        	roles.add(UserRole.ROLE_SCHOOLADMIN);
        	u1.setRoles(roles);
        	u1.setPhoneNumber("0600000000");
        	u1.setUserId(1);
        	userRepository.save(u1);
        	logger.info("Test users created");
    	}
    	
    	
    	if(schoolRepository.existsBySchoolName("Ecole 1"))
    	{
    		logger.error("Test schools already exists");
    		return "redirect:/";
    	}
    	School s1 = new School();
    	s1.setSchoolName("Ecole 1");
    	s1.setSchoolId(1);
    	s1.setManager(u1);
    	schoolRepository.save(s1);
    	logger.info("Test schools created");
    	
		return "redirect:/";
	}
}
