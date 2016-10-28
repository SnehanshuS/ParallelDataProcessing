package com.sdp.logger;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import com.sdp.utilities.Constants;

public class CustomFileLogger {
	private Logger logger;
	private FileAppender appender;
	private static CustomFileLogger instance;

	private CustomFileLogger() {

	}

	public static synchronized CustomFileLogger getInstance() {
		if (instance == null) {
			instance = new CustomFileLogger();
		}
		return instance;
	}

	public Logger getLogger(String className) {
		if (className == null) {
			className = "";
		}
		logger = Logger.getLogger(className);
		if (appender == null) {
			appender = getAppender();
		}
		logger.addAppender(appender);
		logger.setLevel(Level.DEBUG);
		return logger;
	}

	private FileAppender getAppender() {
		PatternLayout layout = new PatternLayout(
				"%d{yyyy-MM-dd HH:mm:ss} %p %C:%L %m%n");
		String logLocation = Constants.LOG_LOCATION;
		try {
			File logPathFile = new File(logLocation);
			if (!logPathFile.exists()) {
				logPathFile.mkdirs();
			}
			String filename = logLocation + "/" + Constants.LOG_FILENAME;
			RollingFileAppender appender = new RollingFileAppender(layout,
					filename, true);
			appender.setName("stormdp");
			appender.setBufferedIO(false);
			appender.activateOptions();
			return appender;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
