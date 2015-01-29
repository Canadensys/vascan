package net.canadensys.dataportal.vascan.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.canadensys.dataportal.vascan.config.VascanConfig;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Collection of static methods to help controllers.
 * @author cgendreau
 *
 */
public class ControllerHelper {
	
	/**
	 * Add to the provided model a map including the URI to the provided resource in the other
	 * available language(s).
	 * @param request
	 * @param model
	 */
	public static void addOtherLanguageUri(HttpServletRequest request, Map<String,Object> model){		
		Locale locale = RequestContextUtils.getLocale(request);
		String currLanguage = locale.getLanguage();
		
		Map<String,String> languagePathMap = new HashMap<String,String>();
		for(String currSupportedLang : VascanConfig.SUPPORTED_LANGUAGES){
			if(!currSupportedLang.equalsIgnoreCase(currLanguage)){
				languagePathMap.put(currSupportedLang, buildLanguageUri(request, currSupportedLang));
			}
		}
		model.put("otherLanguage", languagePathMap);
	}
	
	public static String buildLanguageUri(HttpServletRequest request, String lang){
		UriComponentsBuilder bldr = ServletUriComponentsBuilder.fromRequest(request);
		bldr.replaceQueryParam(VascanConfig.LANG_PARAM, lang);
		return bldr.build().toUriString();
	}

}
