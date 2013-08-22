package net.canadensys.dataportal.vascan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.config.VascanConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller to check the status of the web application. This controller will answer to HEAD and GET queries.
 * This is often used by external service like UptimeRobot.
 * @author canadensys
 *
 */
@Controller
public class HealthController {
	
	@Autowired
	private VascanConfig vascanConfig;
	
	/**
	 * Response to HEAD request with a HTTP_OK and no content, as defined by the standard.
	 * @param response
	 */
    @RequestMapping(value="/health", method=RequestMethod.HEAD)
    public void healthCheckHead(HttpServletResponse response) {
        response.setContentLength(0);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    /**
     * Response to GET request with a HTTP_OK and the string OK.
     * @param response
     */
    @RequestMapping(value="/health", method=RequestMethod.GET)
    public void healthCheckGet(HttpServletResponse response) {
        try {
			response.getWriter().println("OK");
			response.getWriter().println("Version : " + vascanConfig.getCurrentVersion());
			response.getWriter().close();
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
