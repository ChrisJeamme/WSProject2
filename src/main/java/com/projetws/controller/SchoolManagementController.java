package com.projetws.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.Child;
import com.projetws.model.ChildRepository;
import com.projetws.model.School;
import com.projetws.model.SchoolClass;
import com.projetws.model.SchoolClassRepository;
import com.projetws.model.SchoolRepository;
import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.model.UserRole;

import io.swagger.annotations.Api;

@Controller
@Api(value = "school")
public class SchoolManagementController
{
	private static final Logger logger = LogManager.getLogger(SchoolManagementController.class);
	
	@Autowired
	ChildRepository childRepository;
	@Autowired
	SchoolRepository schoolRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SchoolClassRepository schoolClassRepository;

	@RequestMapping("/newSchool")
	public String newSchool(Principal principal, Model m, @RequestParam("schoolName") String schoolName)
	{
		logger.info("Request new school");
		
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
		
		logger.info("New school created = "+schoolName);
		return "redirect:/schoolManagement";
	}
	
	@RequestMapping("/newSchoolClass")
	public String newSchoolClass(Principal principal,
							Model m,
							@RequestParam("schoolClassName") String schoolClassName,
							@RequestParam("year") int year,
							@RequestParam("schoolId") String schoolId)
	{
		logger.info("Request of new school class");

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

		School school = schoolRepository.findBySchoolId(Long.parseLong(schoolId));
		
		if(school==null)
		{
			m.addAttribute("error","School not found");		
			return "redirect:/schoolManagement";
		}

		schoolClassRepository.save(new SchoolClass(schoolClassName, year, school));

		logger.info("New school class created = "+schoolClassName);
		return "redirect:/schoolManagement";
	}
	
	@RequestMapping("/newChild")
	public String newChild(Principal principal,
							Model m,
							@RequestParam("firstName") String firstName,
							@RequestParam("lastName") String lastName,
							@RequestParam("schoolClassId") String schoolClassId,
							@RequestParam("emailParent") String emailParent,
							@RequestParam("phoneNumberParent") int phoneNumberParent)
	{
		logger.info("Request of new child");

		if(principal==null)
		{
			logger.error("Not connected");
			return "redirect:/login";
		}
		
		List<String> roles = new ArrayList<>();
        roles.add("ROLE_DEFAULT");
        roles.add("ROLE_PARENT");
		
        User parent = new User(emailParent, "M./MS.", lastName, ""+phoneNumberParent, "parent", emailParent, roles);
		
		logger.info("New parents created");
        
		SchoolClass schoolClass = schoolClassRepository.findBySchoolClassId(Long.parseLong(schoolClassId));
		
		childRepository.save(new Child(firstName, lastName, parent, schoolClass));

		logger.info("New child created = "+firstName+" "+lastName);
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
	public String getSchoolClassManagement(Principal principal, Model m, @RequestParam("id") int id)
	{
		if(principal==null)
		{
			logger.error("Not connected");
			return "redirect:/error";
		}

		String userName = principal.getName();
		logger.info(userName);
		//TODO Vérifier qu'il a le droit d'afficher ça
		
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
		SchoolClass schoolClass = schoolClassRepository.findBySchoolClassId(id);
		
		m.addAttribute("school", school);
		m.addAttribute("schoolClass", schoolClass);

		return "schoolClassManagement";
	}
}
