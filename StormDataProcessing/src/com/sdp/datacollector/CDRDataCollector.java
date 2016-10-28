package com.sdp.datacollector;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.logger.CustomFileLogger;
import com.sdp.utilities.Constants;

public class CDRDataCollector {

	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			LogDataCollector.class.getName());
	HashMap<String, Integer> counter = new HashMap<String, Integer>();
	private static CDRDataCollector collector = new CDRDataCollector();

	private CDRDataCollector() {
		// TODO Auto-generated constructor stub
	}

	public static CDRDataCollector getInstance() {
		return collector;
	}

	public synchronized void updateconnTypeCounters(String connType) {
		
			if (this.counter.containsKey(connType)) {
				this.counter.put(connType,
						this.counter.get(connType) + 1);
			} else {
				this.counter.put(connType, 1);
			}
		
	}

	public float getASR(){
		if(counter == null || counter.size() == 0){
			return -0.1f;
		}
		
		float totalcalls = 0.0f;
		
		for (Map.Entry<String,Integer> entry : counter.entrySet()) {
			totalcalls = totalcalls + entry.getValue();
		}
		
		float successcalls = counter.get(Constants.NORMAL_CALL_CHAR);
		logger.info("totalcalls==" + totalcalls + ",successcalls==" + successcalls);
		float asr = (successcalls/totalcalls)*100f;
		counter.clear();
		return asr;
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
