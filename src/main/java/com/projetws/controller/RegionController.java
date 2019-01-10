package com.projetws.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.EmployeeRole;
import com.projetws.model.Region;
import com.projetws.model.RegionRepository;
import com.projetws.tools.SecurityTools;

import io.swagger.annotations.Api;

@Controller
@Api(value = "regions")
@RequestMapping("/region")
public class RegionController {

	@Autowired
	RegionRepository regionRepository;
	
	@RequestMapping("/all")
	public String getAllRegions(Model m)
	{
		List<Region> regions = regionRepository.findAll();

		m.addAttribute("regions", regions);
		return "regions";
	}
	
	@RequestMapping(value="/updateRegion", method=RequestMethod.POST)
	public String updateRegion(@RequestParam("regionId") Long id,   
							   @RequestParam("regionName") String regionName)
	{
		if(!SecurityTools.hasRole("ROLE_ALL"))
			return "redirect:/region/all";
		
		Region region = regionRepository.findByRegionId(id);
		if(region != null)
		{
			region.setRegionName(regionName);
			regionRepository.save(region);
		}
		return "redirect:/region/all";
	}
	
}
