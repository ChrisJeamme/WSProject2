package com.projetws.controller;

import java.security.Principal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.School;
import com.projetws.model.SchoolClass;
import com.projetws.model.SchoolClassRepository;
import com.projetws.model.SchoolRepository;
import com.projetws.model.User;
import com.projetws.model.UserRepository;

import io.swagger.annotations.Api;

@Controller
@Api(value = "school")
public class SchoolManagementController
{
	private static final Logger logger = LogManager.getLogger(SchoolManagementController.class);
	
	@Autowired
	SchoolRepository schoolRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SchoolClassRepository schoolClassRepository;

	@RequestMapping("/newSchool")
	public String newSchool(@RequestParam("schoolName") String schoolName)
	{
		logger.info("new school = "+schoolName);
		return "redirect:/schoolManagement";
	}
	
	@RequestMapping("/schoolManagement")
	public String getSchoolManagement(Principal principal, Model m)
	{
		if(principal==null)
		{
			logger.error("Not connected");
			return "redirect:/error";
		}

		String userName = principal.getName();
		logger.info(userName);
		
		if(!userRepository.existsByUserName(userName))
		{
			logger.error("User not found");
			return "redirect:/error";
		}
		
		User manager = userRepository.findByUserName(userName);
		if(!schoolRepository.existsByManager(manager))
		{
			logger.info("School not found");
			return "schoolCreation";
		}
		School school = schoolRepository.findByManager(manager);
		List<SchoolClass> schoolClasses = schoolClassRepository.findAllBySchool(school);
		
		logger.info(schoolClasses.size());
		
		m.addAttribute("school", school);
		m.addAttribute("schoolClasses", schoolClasses);
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
