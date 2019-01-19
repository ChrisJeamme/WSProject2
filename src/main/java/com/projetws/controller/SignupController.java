package com.projetws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.model.UserService;
import com.projetws.tools.InputDataVerification;

import io.swagger.annotations.Api;

@Controller
public class SignupController
{
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String inscription(
            @RequestParam("username") String userName,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("mail") String mail,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            Model m
    )
    {
	if (password.compareTo(password2) != 0) //The 2 passwords are different
        {
            m.addAttribute("error", "Les mots de passes sont différents");
            return "forward:/signup";
        }
        
        if (!InputDataVerification.emailAddressOk(mail))
        {
            m.addAttribute("error", "L'adresse e-mail n'est pas valide");
            return "forward:/signup";
        }

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_DEFAULT");
        roles.add("ROLE_SCHOOLADMIN");
        
        int add = userService.addUser(new User(mail, firstName, lastName, phoneNumber, password, userName, roles)); //Try to add the user

        if (add == 1)	//The pseudo is already used
        {
            m.addAttribute("error", "Ce pseudo est déjà utilisé");
            return "forward:/signup";
        }
        if (add == 2)	//The mail is already used
        {
            m.addAttribute("mailExists", "Il y a déjà un compte avec cet email");
            return "forward:/signup";
        }
        m.addAttribute("user", userName);
        m.addAttribute("mdp", password);
        return "/login"; //The user has been correctly added in the database
    }

    /**
     * Request signup
     * @return The template "signup"
     */
    @RequestMapping("/signup")
    public String page()
    {
        return "/signup";
    }
}
