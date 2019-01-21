package com.projetws.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.UserRepository;
import com.projetws.model.UserService;

@Controller
public class ProfilController
{
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	
	@RequestMapping("/profil")
	public String profil(Principal principal, Model m)
	{
		m.addAttribute("user",userRepository.findByUserName(principal.getName()));
		return "profil";
	}

	/**
     * Request a password modification
     * @param newPassword New password
     * @param repeatNewPassword Repeated new password
     * @param m Model
     * @return Forward to "/profil"
     */
    @RequestMapping(value = "/passwordChanging", method = RequestMethod.POST)
    public String passwordChanging(
    		Principal principal,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("repeatNewPassword") String repeatNewPassword,
            Model m)
    {
        if (!newPassword.equals("undefined") && !repeatNewPassword.equals("undefined"))
        {
            if (newPassword.equals(repeatNewPassword))
            {
                String name = principal.getName();
                if (userService.changeUserPassword(name, newPassword) == 1)
                    m.addAttribute("success", "Password modified with success");
                else
                    m.addAttribute("error", "Password not modified");
            }
            else
                m.addAttribute("error", "Passwords are differents");
        }
        return "forward:/profil";
    }
}
