package com.projetws.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.Child;
import com.projetws.model.ChildRepository;
import com.projetws.model.Order;
import com.projetws.model.OrderRepository;
import com.projetws.model.School;
import com.projetws.model.SchoolClass;
import com.projetws.model.SchoolClassRepository;
import com.projetws.model.SchoolRepository;
import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.model.UserRole;
import com.projetws.model.UserService;
import com.projetws.tools.SecurityTools;

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
	UserService userService;
	@Autowired
	SchoolClassRepository schoolClassRepository;
	@Autowired
	OrderRepository orderRepository;
	
	private User getActualConnectedUser(Principal principal, Model m)
	{
		if(principal==null)
		{
			logger.error("Problem with logged user");
			m.addAttribute("error","Problem with logged user");
			return null;
		}
		
		String username = principal.getName();
		if(!userRepository.existsByUserName(username))
		{
			logger.error("User doesn't exist");
			m.addAttribute("error","User doesn't exist");
			return null;
		}
		
		return userRepository.findByUserName(username);
	}
	/**
	 *
	 * @param principal
	 * @param m
	 * @param schoolName
	 * @return
	 */
	@RequestMapping(value="/newSchool",method=RequestMethod.POST)
	public String newSchool(Principal principal, Model m, @RequestParam(value="schoolName",required=true) String schoolName)
	{
		logger.info("Request new school");
		
		User manager = getActualConnectedUser(principal, m);
		if(manager==null)
			return "redirect:/login";
		
		if(schoolRepository.existsBySchoolName(schoolName))
		{
			logger.error("School name already taken");
			m.addAttribute("error","School name already taken");

			return "redirect:/login";
		}
		else
		{
			schoolRepository.save(new School(schoolName, manager));
		}
		
		logger.info("New school created = "+schoolName);
		return "redirect:/schoolManagement";
	}
	
	@RequestMapping(value="/newSchoolClass",method=RequestMethod.POST)
	public String newSchoolClass(Principal principal,
							Model m,
							@RequestParam("schoolClassName") String schoolClassName,
							@RequestParam("year") int year,
							@RequestParam("schoolId") String schoolId)
	{
		logger.info("Request of new school class");

		if(principal==null)
		{
			logger.error("Problem with logged user");
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
	
	/**
	 *  Create a new child and a new associated parent
	 * @param request The request which call this function
	 * @param principal The connected user's principal
	 * @param m Model
	 * @param firstName firstName of the child
	 * @param lastName lastName of the child
	 * @param schoolClassId School class id of the child
	 * @param emailParent Email of the parent
	 * @param phoneNumberParent Phone number of the parent
	 * @return A redirection to schoolManagement page or login page
	 */
	@RequestMapping(value="/newChild",method=RequestMethod.POST)
	public String newChild(
							HttpServletRequest request,
							Principal principal,
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
			logger.error("Problem with logged user");
			return "redirect:/login";
		}
		
		// Generation of role set
		List<String> roles = SecurityTools.generateRolesWith("ROLE_DEFAULT","ROLE_PARENT");
		
		// Verification of the email
		if(!SecurityTools.emailNotUsedVerification(userRepository, emailParent))
			return SecurityTools.displayErrorAndRedirect(m, "Email already used", SecurityTools.samePage(request));
		
		// Add the parent
        int parentAdd = userService.addUser(new User(emailParent, "M./MS.", lastName, ""+phoneNumberParent, ""+phoneNumberParent, emailParent, roles));
        if(parentAdd == 1)
        {
        	m.addAttribute("error","Parent already exists");
			return "redirect:/schoolManagement";
        }
        logger.info("New parents created");
		
        // Add the child
        User parent = userRepository.findByEmail(emailParent);
		SchoolClass schoolClass = schoolClassRepository.findBySchoolClassId(Long.parseLong(schoolClassId));
		Child child = childRepository.save(new Child(firstName, lastName, parent, schoolClass));
		logger.info("New child created = "+firstName+" "+lastName+" (ID="+child.getChildId()+")");

		// Add the child to his school class
		schoolClass.getChildren().add(child);
		schoolClassRepository.save(schoolClass);
		logger.info("New child add to the school class : "+schoolClass.getSchoolClassName()+" "+schoolClass.getYear());

		return "redirect:/schoolManagement";
	}
	
	/**
	 * Controller for the school management page
	 * If the user don't have a school, it will redirect him to the school creation page
	 * It will provide school and the associated schoolclasses in the model
	 * @param principal The connected user's principal
	 * @param m Model 
	 * @return schoolCreation, schoolManagement, or a redirection to login/error
	 */
	@RequestMapping("/schoolManagement")
	public String getSchoolManagement(Principal principal, Model m)
	{
		if(principal==null)
		{
			logger.error("Problem with logged user");
			return "redirect:/login";
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
		
		// TODO : Need to take into account the School
		List<Order> orders = orderRepository.findAll();
		m.addAttribute("school", school);
		m.addAttribute("schoolClasses", schoolClasses);
		m.addAttribute("orders", orders);
		return "schoolManagement";
	}
	
	/**
	 * Controller for the school class management page
	 * It will provide school and the associated schoolclasses in the model
	 * @param principal The connected user's principal
	 * @param m Model 
	 * @param id Id of the connected user (the manager)
	 * @return schoolCreation, schoolClassManagement, or a redirection to login/error
	 */
	@RequestMapping("/schoolClassManagement")
	public String getSchoolClassManagement(Principal principal, Model m, @RequestParam(value="id",required=true) int id)
	{
		
		if(principal==null)
		{
			logger.error("Problem with logged user");
			return "redirect:/login";
		}	
		
		String userName = principal.getName();
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
	
	@RequestMapping("/testtt")
	public String testtt(HttpServletRequest request)
	{
		SecurityTools.samePage(request);
		return "redirect:/";
	}
	
}
