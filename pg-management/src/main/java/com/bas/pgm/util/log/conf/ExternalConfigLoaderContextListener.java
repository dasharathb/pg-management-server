package com.bas.pgm.util.log.conf;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@WebListener
@Component
public class ExternalConfigLoaderContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ExternalConfigLoaderContextListener.class);
	
	
	private static final String LOGBACKCONFIGDIR = "conf";
	private static final String LOGBACKFILENAME = "logback.xml";
	private static final String FILE_SEPERATOR =  java.io.File.separator;
	private static final String CONFIGDIRPARAM = "external.config.dir.base";
	
	
	@Value("${external.config.dir.base}")
	String externalConfig;
	
	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String configLocation = sce.getServletContext().getInitParameter(CONFIGDIRPARAM);	
		logger.info("first time config location is from servletContext*******" +configLocation);
		if(configLocation == null){		
			
			configLocation = System.getenv(CONFIGDIRPARAM);
			logger.info("second  time config location is from system property*******"+configLocation);
			if(configLocation == null){
				//configLocation = System.getenv(CONFIGDIRPARAM);
				
				configLocation = externalConfig;
				logger.info("third time config location is attaching externalConfig is:*******:"+externalConfig);
			}
				 
		} 
		
		logger.info("logback configLocation :"+configLocation);
		System.out.println("logback configLocation :"+configLocation);
		try{
			
			final String logbackFile = configLocation+FILE_SEPERATOR+LOGBACKCONFIGDIR+FILE_SEPERATOR+LOGBACKFILENAME;
			logger.info("logback file location is :"+logbackFile);
			System.out.println("logback file location is :"+logbackFile);	
			new LogBackConfigLoader(logbackFile);
			
		}catch(Exception e){
			logger.error("Unable to read config file", e);
		}
	}
}
