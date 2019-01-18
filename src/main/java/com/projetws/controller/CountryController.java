package com.projetws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Controller
@Api(value = "countries")
@RequestMapping("/country")
public class CountryController
{
//	@Autowired
//	CountryRepository countryRepository;
//
//	@RequestMapping("/all")
//	public String getAllCountries(Model m)
//	{
//		List<Country> countries = countryRepository.findAll();
//		
//		m.addAttribute("countries", countries);
//		return "countries";
//	}
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
