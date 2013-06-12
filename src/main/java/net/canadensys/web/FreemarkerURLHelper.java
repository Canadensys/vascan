package net.canadensys.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import freemarker.ext.servlet.HttpRequestHashModel;

/**
 * Candidate for future canadensys-web-core library
 * Helper class to manage URL from Freemarker HttpRequestHashModel in Servlet environment.
 * Wrap the class with freemarker.ext.beans.BeansWrapper
 * In your template use something like : ${URLHelper.getURL(Request,"lang","en")}
 * @author canadensys
 *
 */
public class FreemarkerURLHelper {
	
	public static final String LANG_PARAM = "lang";
	
	/**
	 * Get absolute URL from HttpRequestHashModel and add or change a query string parameter.
	 * @param hr HttpRequestHashModel from Freemarker template
	 * @param name query string parameter to add or change
	 * @param value Value of the query string parameter
	 * @return absolute URL as String
	 */
	public static String getURL(HttpRequestHashModel hr, String name, String value){
		UriComponentsBuilder bldr = ServletUriComponentsBuilder.fromRequest(hr.getRequest());
		bldr.replaceQueryParam(name,value);
		return bldr.build().toUriString();
	}
	
	/**
	 * Get an URI with a provided language as query parameter.
	 * Then parameter will be added or replaced.
	 * e.g. 
	 * -getUriWithLanguage("/search?q=cal","fr") will produce /search?q=cal&lang=fr
	 * -getUriWithLanguage("/search?q=cal&lang=en","fr") will produce /search?q=cal&lang=fr
	 * 
	 * @param uri
	 * @param lang
	 * @return
	 */
	public static String getUriWithLanguage(String uri, String lang){
		UriComponentsBuilder bldr = UriComponentsBuilder.fromUriString(uri);
		bldr.replaceQueryParam("lang",lang);
		return bldr.build().toUriString();
	}
}
