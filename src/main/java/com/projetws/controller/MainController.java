package com.projetws.controller;

import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.projetws.model.Child;
import com.projetws.model.ChildRepository;
import com.projetws.model.School;
import com.projetws.model.SchoolClass;
import com.projetws.model.SchoolClassRepository;
import com.projetws.model.SchoolRepository;
import com.projetws.model.User;
import com.projetws.model.UserRepository;
import com.projetws.model.UserRole;

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
	@Autowired
	ChildRepository childRepository;
	
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
    	
    	School s1;
    	
    	if(schoolRepository.existsBySchoolName("Ecole 1"))
    	{
    		logger.error("Test schools already exists");
    		s1 = schoolRepository.findBySchoolName("Ecole 1");
    	}
    	else
    	{    		
    		s1 = new School();
    		s1.setSchoolName("Ecole 1");
    		s1.setSchoolId(1);
    		s1.setManager(u1);
    		schoolRepository.save(s1);
    		logger.info("Test schools created");
    	}
    	
    	SchoolClass sc1, sc2;
    
    	
    	if(schoolClassRepository.existsBySchoolClassName("3eme")&&schoolClassRepository.existsBySchoolClassName("4eme"))
    	{
    		logger.error("Test school classses already exists");
    	}
    	else
    	{    		
    		sc1 = new SchoolClass();
    		sc1.setSchoolClassName("3eme");
    		sc1.setYear(2018);
    		sc1.setSchool(s1);
    		schoolClassRepository.save(sc1);
    		
    		sc2 = new SchoolClass();
    		sc2.setSchoolClassName("4eme");
    		sc2.setYear(2018);
    		sc2.setSchool(s1);    		
    		schoolClassRepository.save(sc2);

    		logger.info("Test school classses created"); 
    	}
    		
    	Child c1;
    	
    	if(childRepository.existsByChildFirstName("FirstName1"))
    	{
    		logger.error("Test child already exists");
    		c1 = childRepository.findByChildFirstName("FirstName1");
    	}
    	else
    	{    		
    		c1 = new Child();
    		c1.setChildFirstName("FirstName1");
    		c1.setChildLastName("LastName1");
    		c1.setSchoolClass(schoolClassRepository.findBySchoolClassId(1));
    		childRepository.save(c1);
    		logger.info("Test Children created");
    	}
    	
    	Child c2;
    	
    	if(childRepository.existsByChildFirstName("FirstName2"))
    	{
    		logger.error("Test child2 already exists");
    	}
    	else
    	{    		
    		c2 = new Child();
    		c2.setChildFirstName("FirstName2");
    		c2.setChildLastName("LastName2");
    		c2.setSchoolClass(schoolClassRepository.findBySchoolClassId(1));
    		childRepository.save(c2);
    		logger.info("Test Children created");
    	}
    	
		return "redirect:/"; 
	}
    
	@RequestMapping("/testUpload")
    private String upload(Model model)
    {
    	
    	model.addAttribute("schoolClasss", schoolClassRepository.findAll());
    	model.addAttribute("childs", childRepository.findAll());
    	return "upload";
    }
	
	@RequestMapping("/order")
    private String order(/*Model model*/)
    {
    	return "order";
    }
	
}
