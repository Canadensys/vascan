package net.canadensys.dataportal.vascan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HttpErrorController {
		
	@RequestMapping(value="/errors/404.html")
    public ModelAndView handle404(HttpServletRequest request) {
        return new ModelAndView("error/404");
    }
}

