package com.projetws.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.model.UserService;
import com.projetws.tools.InputDataVerification;

import io.swagger.annotations.Api;

@Controller
public class SignupController
{
	private static final Logger logger = LogManager.getLogger(SignupController.class);
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String signup(
    		HttpServletRequest request,
            @RequestParam("userName") String userName,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("mail") String mail,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            @RequestParam("type") String type,
            Model m
    )
    {
    	logger.info("Signup asked");
    	if (password.compareTo(password2) != 0) //The 2 passwords are different
        {
        	logger.warn("Passwords are differents");
            m.addAttribute("error", "Passwords are differents");
            return "redirect:"+request.getHeader("Referer");
        }
        
        if (!InputDataVerification.emailAddressOk(mail))
        {
        	logger.warn("The email adress is not valid");
            m.addAttribute("error", "The email adress is not valid");
            return "redirect:"+request.getHeader("Referer");
        }
    	
        List<String> roles = new ArrayList<>();
    	if(type == "schoolAdmin")
    	{
            roles.add("ROLE_DEFAULT");
            roles.add("ROLE_SCHOOLADMIN");
    	}
    	else if(type == "photographer")
    	{
            roles.add("ROLE_DEFAULT");
            roles.add("ROLE_PHOTOGRAPHER");
		}
    	else
    	{
    		logger.error("signupType="+type);
        	logger.warn("Unknown type of signup");
            m.addAttribute("error", "Error in the form");
    		return "redirect:"+request.getHeader("Referer");
    	}
    	
        int add = userService.addUser(new User(mail, firstName, lastName, phoneNumber, password, userName, roles)); //Try to add the user

        if (add == 1)
        {
        	logger.warn("Username already used");
            m.addAttribute("error", "Username already used");
            return "redirect:"+request.getHeader("Referer");
        }
        if (add == 2)
        {
        	logger.warn("Mail already used");
            m.addAttribute("mailExists", "Mail already used");
            return "redirect:"+request.getHeader("Referer");
        }
        logger.info("Sign up success : username="+userName);
        m.addAttribute("app_username", userName);
        m.addAttribute("app_password", password);
        return "redirect:/login"; //The user has been correctly added in the database
    }

    @RequestMapping("/signupSchoolAdministrator")
    public String signupSchoolAdministrator()
    {
        return "/signupSchoolAdministrator";
    }
    @RequestMapping("/signupPhotographer")
    public String signupPhotographer()
    {
        return "/signupPhotographer";
    }
}
