package net.canadensys.dataportal.vascan.controller;

import net.canadensys.dataportal.vascan.APIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	
	@Autowired
	private APIService apiService;
	
	@RequestMapping(value="/api/{version}/search",method={RequestMethod.GET})
	public @ResponseBody Object handleSearch(@RequestParam String q, @PathVariable String version){
		return apiService.search(q);
	}

}
