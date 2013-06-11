package net.canadensys.dataportal.vascan.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.canadensys.dataportal.vascan.ImageService;
import net.canadensys.dataportal.vascan.image.DistributionImageGenerator;
import net.canadensys.exception.web.ResourceNotFoundException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Image controller responsible for controlling dynamic images access.
 * @author canadensys
 *
 */
@Controller
public class ImageController {
	
	private static final String PNG_MIME_TYPE = "image/png";
	private static final String SVG_MIME_TYPE = "image/svg+xml";
	
	@Autowired
	private ImageService imageService;
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
