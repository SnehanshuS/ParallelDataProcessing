package com.sdp.datacollector;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.logger.CustomFileLogger;

public class TraceDataCollector {

	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			TraceDataCollector.class.getName());
	HashMap<String, Integer> srcCounter = new HashMap<String, Integer>();
	HashMap<String, Integer> dstCounter = new HashMap<String, Integer>();
	HashMap<String, Integer> protocolCounter = new HashMap<String, Integer>();

	private static TraceDataCollector collector = new TraceDataCollector();

	private TraceDataCollector() {
		// TODO Auto-generated constructor stub
	}

	public static TraceDataCollector getInstance() {
		return collector;
	}

	public synchronized void updateSrcCounters(
			HashMap<String, Integer> srcCounter) {
		for (Map.Entry<String, Integer> entry : srcCounter.entrySet()) {
			if (this.srcCounter.containsKey(entry.getKey())) {
				this.srcCounter.put(entry.getKey(),
						this.srcCounter.get(entry.getKey()) + entry.getValue());
			} else {
				this.srcCounter.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public synchronized void updateDstCounters(
			HashMap<String, Integer> dstCounter) {
		for (Map.Entry<String, Integer> entry : dstCounter.entrySet()) {
			if (this.dstCounter.containsKey(entry.getKey())) {
				this.dstCounter.put(entry.getKey(),
						this.dstCounter.get(entry.getKey()) + entry.getValue());
			} else {
				this.dstCounter.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public synchronized void updateProtocolCounters(
			HashMap<String, Integer> protocolCounter) {
		for (Map.Entry<String, Integer> entry : protocolCounter.entrySet()) {
			if (this.protocolCounter.containsKey(entry.getKey())) {
				this.protocolCounter.put(
						entry.getKey(),
						this.protocolCounter.get(entry.getKey())
								+ entry.getValue());
			} else {
				this.protocolCounter.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public void printSrcCounts() {
		if (srcCounter == null || srcCounter.size() == 0) {
			logger.info("Source counter map is empty");
			return;
		}
		for (Map.Entry<String, Integer> entry : srcCounter.entrySet()) {
			logger.info("Source: " + entry.getKey() + "==" + entry.getValue());
		}
	}

	public void printDstCounts() {
		if (dstCounter == null || dstCounter.size() == 0) {
			logger.info("Destination counter map is empty");
			return;
		}
		for (Map.Entry<String, Integer> entry : dstCounter.entrySet()) {
			logger.info("Destination: " + entry.getKey() + "=="
					+ entry.getValue());
		}
	}

	public void printProtocolCounts() {
		if (protocolCounter == null || protocolCounter.size() == 0) {
			logger.info("Protocol counter map is empty");
			return;
		}
		for (Map.Entry<String, Integer> entry : protocolCounter.entrySet()) {
			logger.info("Protocol: " + entry.getKey() + "==" + entry.getValue());
		}
	}
}
