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
	public String newSchool(Principal principal, Model m, @RequestParam("schoolName") String schoolName)
	{
		logger.info("new school = "+schoolName);
		
		if(principal==null)
		{
			logger.error("Not connected");
			return "redirect:/login";
		}
		String username = principal.getName();
		if(!userRepository.existsByUserName(username))
		{
			logger.error("User doesn't exist");
			return "redirect:/login";
		}
		User manager = userRepository.findByUserName(username);
		
		if(schoolRepository.existsBySchoolName(schoolName))
		{
			m.addAttribute("error","School name already taken");
		}
		else
		{
			schoolRepository.save(new School(schoolName, manager));
		}
		
		return "redirect:/schoolManagement";
	}
	
	@RequestMapping("/newSchoolClass")
	public String newSchoolClass(Principal principal,
							Model m,
							@RequestParam("schoolClassName") String schoolClassName,
							@RequestParam("year") int year,
							@RequestParam("schoolId") String schoolId)
	{
		logger.info("new school class = "+schoolClassName);

		if(principal==null)
		{
			logger.error("Not connected");
			return "redirect:/login";
		}

		if(schoolClassRepository.existsBySchoolClassName(schoolClassName))
		{
			if(schoolClassRepository.existsBySchoolClassNameAndYear(schoolClassName, year))
			{
				m.addAttribute("error","School class name already taken with this year");		
				return "redirect:/schoolManagement";
			}
		}

		logger.info("schoolId="+schoolId);
		/*CA BUG ICI*/
		logger.info("existBySchoolId="+schoolRepository.existsBySchoolId(schoolId));
		School school = schoolRepository.findBySchoolId(schoolId);
		
		if(school==null)
		{
			m.addAttribute("error","School not found");		
			return "redirect:/schoolManagement";
		}

		logger.info("4");
		schoolClassRepository.save(new SchoolClass(schoolClassName, year, school));
	
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
	
	@RequestMapping("/schoolClassManagement")
	public String getSchoolClassManagement(Principal principal, Model m)
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
		return "schoolClassManagement";
	}
}
