package net.canadensys.web;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ServletInjectWrappingController extends AbstractController implements BeanNameAware, InitializingBean, DisposableBean {
	
	private String servletName;
	private Properties initParameters = new Properties();
	
	private Servlet servletInstance;
	private String beanName;

	public Servlet getServletInstance() {
		return servletInstance;
	}

	public void setServletInstance(Servlet servletInstance) {
		this.servletInstance = servletInstance;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.servletInstance.service(request, response);
		return null;
	}

	@Override
	public void destroy() throws Exception {
		this.servletInstance.destroy();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.servletInstance == null) {
			throw new IllegalArgumentException("servletInstance is required");
		}
		this.servletInstance.init(new DelegatingServletConfig());
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}
	
	/**
	 * Set the name of the servlet to wrap.
	 * Default is the bean name of this controller.
	 */
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	/**
	 * Specify init parameters for the servlet to wrap,
	 * as name-value pairs.
	 */
	public void setInitParameters(Properties initParameters) {
		this.initParameters = initParameters;
	}
	
	/**
	 * Internal implementation of the ServletConfig interface, to be passed
	 * to the wrapped servlet. Delegates to ServletWrappingController fields
	 * and methods to provide init parameters and other environment info.
	 */
	private class DelegatingServletConfig implements ServletConfig {

		public String getServletName() {
			return servletName;
		}

		public ServletContext getServletContext() {
			return ServletInjectWrappingController.this.getServletContext();
		}

		public String getInitParameter(String paramName) {
			return initParameters.getProperty(paramName);
		}

		public Enumeration getInitParameterNames() {
			return initParameters.keys();
		}
	}
}
