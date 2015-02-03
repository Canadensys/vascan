package net.canadensys.dataportal.vascan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.canadensys.dataportal.vascan.config.VascanConfig;
import net.canadensys.exception.web.ResourceNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagesController {
	
	/**
	 * Handle SiteMesh calls for decorators.
	 * 
	 * @param request
	 * @param decoratorName
	 * @return
	 */
	@RequestMapping("/decorators/{decoratorName}")
	public ModelAndView handleDecorator(HttpServletRequest request, @PathVariable String decoratorName){

		// Do not respond to direct request, only internal forward
		if(request.getAttribute("javax.servlet.forward.request_uri") == null){
			throw new ResourceNotFoundException();
		}
		return new ModelAndView(decoratorName);
	}

	@RequestMapping(value={"/about"}, method={RequestMethod.GET})
	public ModelAndView handleAboutPage(HttpServletRequest request){
		Map<String,Object> model = new HashMap<String,Object>();
		ControllerHelper.addOtherLanguageUri(request, model);
		return new ModelAndView("about", VascanConfig.PAGE_ROOT_MODEL_KEY, model);
	}

	@RequestMapping(value={"/api"}, method={RequestMethod.GET})
	public ModelAndView handleApiPage(HttpServletRequest request){
		Map<String,Object> model = new HashMap<String,Object>();
		
		ControllerHelper.addOtherLanguageUri(request, model);
		return new ModelAndView("api", VascanConfig.PAGE_ROOT_MODEL_KEY, model);
	}
}
