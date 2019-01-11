package com.projetws.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.School;
import com.projetws.model.SchoolRepository;
import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.model.UserRole;
import com.projetws.tools.SecurityTools;

import io.swagger.annotations.Api;

@Controller
@Api(value = "school")
@RequestMapping("/schoolManagement")
public class SchoolManagement
{
	@Autowired
	SchoolRepository schoolRepository;
	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	public String getSchoolManagement(Principal principal, Model m)
	{
		
		if(principal==null)
			return "redirect:/error";

		String userName;
		userName = principal.getName();
		
		User user = userRepository.findByUserName(userName);
		if(user==null)
			return "redirect:/error";
		
		School school = schoolRepository.findByManager(user.getUserId());
		if(school==null)
			return "redirect:/error";
		
		m.addAttribute("school", school);
		return "schoolManagement";
	}
//	
//	@RequestMapping(value="/updateCountry", method=RequestMethod.POST)
//	public String updateCountry(@RequestParam("countryId") String id, @RequestParam("countryName") String name, @RequestParam("regionId") Long regionId)
//	{
//		if(!SecurityTools.hasRole("ROLE_ALL"))
//			return "redirect:/country/all";
//		
//		Country country = countryRepository.findByCountryId(id);
//		if(country != null)
//		{
//			Region region = regionRepository.findByRegionId(regionId);
//			if(region!= null)
//			{
//				country.setCountryName(name);
//				country.setRegion(region);
//				countryRepository.save(country);
//			}
//		}
//		return "redirect:/country/all";
//	}
	
}
