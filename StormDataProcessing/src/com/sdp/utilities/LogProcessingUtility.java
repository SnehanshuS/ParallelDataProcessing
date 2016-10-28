package com.sdp.utilities;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sdp.logger.CustomFileLogger;

public class LogProcessingUtility {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			LogProcessingUtility.class.getName());

	public static Matcher getLogDetails(String logline, String logType) {

		String logPattern = Constants.LOG_PATTERN.get(logType);
		if (logPattern == null) {
			logger.error("Log pattern not found for key " + logType);
			return null;
		}
		Pattern p = Pattern.compile(logPattern);
		Matcher matcher = p.matcher(logline);
		if (!matcher.matches()) {
			logger.error("Bad log entry");
			logger.error(logline);
			return null;
		}

		return matcher;
	}

	public static HashMap<String, Integer> getLogLevelCounter(Matcher match,
			Integer logLevelIndex) {

		if (match == null) {
			return null;
		}

		HashMap<String, Integer> counters = new HashMap<String, Integer>();
		if (counters.containsKey(match.group(logLevelIndex))) {
			counters.put(match.group(logLevelIndex),
					((Integer) counters.get(match.group(logLevelIndex))) + 1);
		} else {
			counters.put(match.group(logLevelIndex), 1);
		}

		return counters;
	}
}
