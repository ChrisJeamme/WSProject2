package com.projetws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.Country;
import com.projetws.model.CountryRepository;
import com.projetws.model.Region;
import com.projetws.model.RegionRepository;
import com.projetws.tools.SecurityTools;

import io.swagger.annotations.Api;

@Controller
@Api(value = "countries")
@RequestMapping("/country")
public class CountryController
{

	@Autowired
	CountryRepository countryRepository;
	@Autowired
	RegionRepository regionRepository;

	@RequestMapping("/all")
	public String getAllCountries(Model m)
	{
		List<Country> countries = countryRepository.findAll();
		List<Region> regions = regionRepository.findAll();
		
		m.addAttribute("countries", countries);
		m.addAttribute("regions", regions);
		return "countries";
	}
	
	@RequestMapping(value="/updateCountry", method=RequestMethod.POST)
	public String updateCountry(@RequestParam("countryId") String id, @RequestParam("countryName") String name, @RequestParam("regionId") Long regionId)
	{
		if(!SecurityTools.hasRole("ROLE_ALL"))
			return "redirect:/country/all";
		
		Country country = countryRepository.findByCountryId(id);
		if(country != null)
		{
			Region region = regionRepository.findByRegionId(regionId);
			if(region!= null)
			{
				country.setCountryName(name);
				country.setRegion(region);
				countryRepository.save(country);
			}
		}
		return "redirect:/country/all";
	}
	
}
