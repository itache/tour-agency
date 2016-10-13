package me.itache.web.listener;

import me.itache.dao.DAOFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import me.itache.service.TourService;

import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Context listener.
 * 
 * @author karpachev vitaliy
 *
 */
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(ContextListener.class);
	/**
	 * Servlet context destruction
	 */
	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction");
	}

	/**
	 * Servlet context initialization
	 */
	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.naming.java.javaURLContextFactory");
		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();
		initDAOFactory();
        initServices(servletContext);

	
		log("Servlet context initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(
				servletContext.getRealPath("WEB-INF/log4j.properties"));
			log("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			log(ex.getMessage());
		}		
		log("Log4J initialization finished");
	}

    /**
     * Initializes services.
     *
     * @param context
     */
    private void initServices(ServletContext context) {
        String path = context.getInitParameter("upload.location");
        TourService.init(path);
    }
	
	/**
	 * Initializes CommandRegistry.
	 */
	private void initCommandContainer() {
		try {
			Class.forName("ua.nure.karpachev.summaryTask4.web.command.CommandRegistry");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}
	
	/**
	 * Initializes DAOFactory name.
	 */
	private void initDAOFactory() {
		LOG.debug("DAOFactory setting started");
		try {
			DAOFactory.init(DAOFactory.Factory.JDBC);
			LOG.debug("DAOFactory has been setted");
		} catch (Exception ex) {
			LOG.error("Cannot set DAOFactory");
			log(ex.getMessage());
		}		
		LOG.debug("DAOFactory setting finished");
	}
	
	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}