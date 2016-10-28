package com.sdp.datacollector;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.logger.CustomFileLogger;

public class LogDataCollector {

	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			LogDataCollector.class.getName());
	HashMap<String, Integer> counter = new HashMap<String, Integer>();
	private static LogDataCollector collector = new LogDataCollector();

	private LogDataCollector() {
	}

	public static LogDataCollector getInstance() {
		return collector;
	}

	public synchronized void updateLogCounters(HashMap<String, Integer> counter) {
		for (Map.Entry<String, Integer> entry : counter.entrySet()) {
			if (this.counter.containsKey(entry.getKey())) {
				this.counter.put(entry.getKey(),
						this.counter.get(entry.getKey()) + entry.getValue());
			} else {
				this.counter.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public void printCounter(){
		for (Map.Entry<String,Integer> entry : counter.entrySet()) {
            logger.info(entry.getKey() + "===============" + entry.getValue());
        }		
		counter.clear();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Integer> entry : this.counter.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue())
					.append(", ");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
