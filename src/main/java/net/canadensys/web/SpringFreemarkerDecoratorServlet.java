package net.canadensys.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;

import net.canadensys.dataportal.vascan.config.VascanConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

/**
 * Light FreemarkerDecoratorServlet override to add our custom URLHelper.
 * At some point we should try to wrap it using Spring ServletWrappingController.
 * 
 * <bean id="freemarkerWrapperServletController" class="org.springframework.web.servlet.mvc.ServletWrappingController">
 * 	<property name="servletClass" value="net.canadensys.web.SpringFreemarkerDecoratorServlet" />
 * 	<property name="servletName" value="sitemesh-freemarker" />
 * 	<property name="initParameters">
 * 		<props>
 * 			<prop key="TemplatePath">/</prop><!--this is ignored by our custom implementation but keep it here-->
 * 			<prop key="default_encoding">ISO-8859-1</prop>
 * 		</props>
 * 	</property>
 * </bean>
 * So we could use:
 *  Autowired
 *  Configuration freemarkerConfiguration;
 *  
 *  TemplateLoader templateLoader = freemarkerConfiguration.getTemplateLoader();
 *  getConfiguration().setTemplateLoader(templateLoader);
 * @author canadensys
 *
 */
public class SpringFreemarkerDecoratorServlet extends FreemarkerServlet {

	private static final long serialVersionUID = 1942463095708194219L;
	
	@Autowired
	private VascanConfig vascanConfig;
	
	//URL prefix starts from after the servlet context
	private String decoratorUrlPrefix = "decorators";
	

	@Override
	public void init() throws ServletException {
		super.init();
		
		// ensure decoratorUrlPrefix starts and ends with a slash
		this.decoratorUrlPrefix = StringUtils.prependIfMissing(decoratorUrlPrefix, "/");
		this.decoratorUrlPrefix = StringUtils.appendIfMissing(decoratorUrlPrefix, "/");
	}
	
	/**
	 * 
	 */
	@Override
	protected String requestUrlToTemplatePath(javax.servlet.http.HttpServletRequest request) {
		String templatePath = super.requestUrlToTemplatePath(request);
		templatePath = StringUtils.removeStart(templatePath, decoratorUrlPrefix);
		return templatePath;
	}
	
    /**
     * This method is called from {@link #init()} to create the
     * FreeMarker configuration object that this servlet will use
     * for template loading. This is a hook that allows you
     * to custom-configure the configuration object in a subclass.
     * The default implementation returns a new {@link Configuration}
     * instance.
     */
    protected Configuration createConfiguration() {
    	Configuration cfg =  new Configuration(Configuration.VERSION_2_3_21);
    	try {
    		BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
    		cfg.setSharedVariable("URLHelper",
					beansWrapper.getStaticModels().get("net.canadensys.web.freemarker.FreemarkerURLHelper"));
			
			//Since we are running in a different Servlet context we need to load the config ourself.
			Properties prop = new Properties();
			InputStream in = getServletContext().getResourceAsStream("/WEB-INF/vascan-config.properties");
			if(in != null){
				prop.load(in);
				in.close();
			}
			
			//register the key in message bundle to include Vascan last publiction date in the footer
			cfg.setSharedVariable("footerAdditionalInfoKey", "footer_last_publication_date");
			// variable to be added that will include the value of the last publication date.
			cfg.setSharedVariable("footerAdditionalInfoParamKey", "lastPublicationDate");

			cfg.setSharedVariable("lastPublicationDate", vascanConfig.getLastPublicationDate());

			cfg.setSharedVariable("gaSiteVerification", StringUtils.defaultString(prop.getProperty("googleanalytics.siteVerification")));
			cfg.setSharedVariable("gaAccount", StringUtils.defaultString(prop.getProperty("googleanalytics.account")));
			if(prop.getProperty("feedback.url") != null){
				cfg.setSharedVariable("feedbackURL", StringUtils.defaultString(prop.getProperty("feedback.url")));
			}
		}
		catch (TemplateModelException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    	return cfg;
    }

	@Override
    protected TemplateLoader createTemplateLoader(String templatePath) throws IOException
    {
		if(!StringUtils.contains(templatePath, ",")){
			return new WebappTemplateLoader(this.getServletContext(), templatePath);
		}
		String[] templatePaths = templatePath.split(",");
		List<TemplateLoader> templateLoaderList = new ArrayList<TemplateLoader>();
		for(String currPath : templatePaths){
			templateLoaderList.add(new WebappTemplateLoader(this.getServletContext(), currPath));
		}
		return new MultiTemplateLoader(templateLoaderList.toArray(new TemplateLoader[0]));

    }


}
