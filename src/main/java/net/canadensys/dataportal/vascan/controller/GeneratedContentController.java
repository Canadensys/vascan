package net.canadensys.dataportal.vascan.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.DownloadService;
import net.canadensys.dataportal.vascan.ImageService;
import net.canadensys.dataportal.vascan.generatedcontent.DistributionImageGenerator;
import net.canadensys.exception.web.ResourceNotFoundException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Controller responsible for controlling dynamic images access and dynamic files creation.
 * @author canadensys
 *
 */
@Controller
@SessionAttributes("filename")
public class GeneratedContentController implements MessageSourceAware {
	
	//get log4j handler
	private static final Logger LOGGER = Logger.getLogger(GeneratedContentController.class);
	
	private static final String PNG_MIME_TYPE = "image/png";
	private static final String SVG_MIME_TYPE = "image/svg+xml";
		
	private MessageSource messageSource;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private DownloadService downloadService;
	
	@RequestMapping(value="/images/distribution/{taxonid}.{ext}",method={RequestMethod.GET})
	public void handleImage(@PathVariable Integer taxonid, @PathVariable String ext, HttpServletResponse response){		
		String imgPath = imageService.getImage(taxonid, ext);
		if(StringUtils.isBlank(imgPath)){
			throw new ResourceNotFoundException();
		}
		
		try {
			FileInputStream istrm = new FileInputStream(imgPath);
			int byteCopied = IOUtils.copy(istrm, response.getOutputStream());
			response.setContentLength(byteCopied);
			
			if(DistributionImageGenerator.PNG_FILE_EXT.endsWith(ext.toLowerCase())){
				response.setContentType (PNG_MIME_TYPE);
			}
			else if(DistributionImageGenerator.SVG_FILE_EXT.endsWith(ext.toLowerCase())){
				response.setContentType (SVG_MIME_TYPE);
			}
			else{
				throw new ResourceNotFoundException();
			}
		} catch (FileNotFoundException e) {
			LOGGER.fatal("Looking for :" + imgPath, e);
			throw new ResourceNotFoundException();
		} catch (IOException e) {
			LOGGER.fatal("Looking for :" + imgPath, e);
			throw new ResourceNotFoundException();
		}
	}
	
	@RequestMapping(value="/download",method={RequestMethod.GET})
	public ModelAndView handleAskForDownload(@RequestParam String format){

	    String filename = downloadService.generateFileName(format);
	    String downloadURL = downloadService.getFileDownloadURL(filename);
	    
	    ModelAndView modelAndView = new ModelAndView();
	    //this will store the filename in the session (@SessionAttributes("filename")) 
        modelAndView.addObject("filename", filename);
        modelAndView.addObject("format", format);
        modelAndView.addObject("downloadURL", downloadURL);
	    
        modelAndView.setViewName("download");
	    return modelAndView;
	}
	
	@RequestMapping(value="/generate",method={RequestMethod.GET})
	public void handleGenerateFile(@ModelAttribute("filename") String filename, @RequestParam String format, HttpServletRequest request, HttpServletResponse response){	    
		downloadService.generateTextFile(request.getParameterMap(), filename, new MessageSourceResourceBundle(messageSource,RequestContextUtils.getLocale(request)));
		//maybe we should send a JSON response
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
    @Override
    public void setMessageSource(MessageSource messageSource) {
         this.messageSource = messageSource;
    }

}
