package net.canadensys.dataportal.vascan.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.config.VascanConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for simple pages with no dynamic content.
 * 
 * @author cgendreau
 *
 */
@Controller
public class PagesController {
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value={"/about"}, method={RequestMethod.GET})
	public ModelAndView handleAboutPage(HttpServletRequest request){
		Map<String,Object> model = new HashMap<String,Object>();
		// date is used to build dynamic citation
		model.put("currentDate", SDF.format(Calendar.getInstance().getTime()));
		ControllerHelper.addOtherLanguageUri(request, model);
		return new ModelAndView("about", VascanConfig.PAGE_ROOT_MODEL_KEY, model);
	}

	/**
	 * Response to HEAD request with a HTTP_OK and no content, as defined by the standard.
	 * @param response
	 */
    @RequestMapping(value="/api", method=RequestMethod.HEAD)
    public void healthCheckHead(HttpServletResponse response) {
        response.setContentLength(0);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
	@RequestMapping(value={"/api"}, method={RequestMethod.GET})
	public ModelAndView handleApiPage(HttpServletRequest request){
		Map<String,Object> model = new HashMap<String,Object>();
		ControllerHelper.addOtherLanguageUri(request, model);
		return new ModelAndView("api", VascanConfig.PAGE_ROOT_MODEL_KEY, model);
	}
}
