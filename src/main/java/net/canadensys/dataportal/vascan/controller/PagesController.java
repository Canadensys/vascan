package net.canadensys.dataportal.vascan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.canadensys.dataportal.vascan.config.VascanConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagesController {
	//private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value={"/about"}, method={RequestMethod.GET})
	public ModelAndView handleAboutPage(HttpServletRequest request){
		Map<String,Object> model = new HashMap<String,Object>();
		//model.put("currentDate", SDF.format(Calendar.getInstance().getTime()));
		ControllerHelper.addOtherLanguageUri(request, model);
		return new ModelAndView("about", VascanConfig.PAGE_ROOT_MODEL_KEY, model);
	}

	@RequestMapping(value={"/api"}, method={RequestMethod.GET})
	public ModelAndView handleApiPage(HttpServletRequest request){
		Map<String,Object> model = new HashMap<String,Object>();
		//model.put("currentDate", SDF.format(Calendar.getInstance().getTime()));
		
		ControllerHelper.addOtherLanguageUri(request, model);
		return new ModelAndView("api", VascanConfig.PAGE_ROOT_MODEL_KEY, model);
	}
}
