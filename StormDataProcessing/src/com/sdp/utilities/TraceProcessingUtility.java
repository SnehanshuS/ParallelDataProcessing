package com.sdp.utilities;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sdp.logger.CustomFileLogger;

public class TraceProcessingUtility {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			TraceProcessingUtility.class.getName());

	public static HashMap<String, Integer> getSrcCounter(String[] traceArr) {

		if (traceArr == null || traceArr.length == 0) {
			logger.error("Trace string is null or empty");
			return null;
		}
		HashMap<String, Integer> counters = new HashMap<String, Integer>();
		if (counters.containsKey(traceArr[Constants.TRACE_SRC_INDEX])) {
			counters.put(traceArr[Constants.TRACE_SRC_INDEX],
					((Integer) counters
							.get(traceArr[Constants.TRACE_SRC_INDEX]) + 1));
		} else {
			counters.put(traceArr[Constants.TRACE_SRC_INDEX], 1);
		}

		return counters;
	}

	public static HashMap<String, Integer> getDstCounter(String[] traceArr) {

		if (traceArr == null || traceArr.length == 0) {
			logger.error("Trace string is null or empty");
			return null;
		}
		HashMap<String, Integer> counters = new HashMap<String, Integer>();
		if (counters.containsKey(traceArr[Constants.TRACE_DST_INDEX])) {
			counters.put(traceArr[Constants.TRACE_DST_INDEX],
					((Integer) counters
							.get(traceArr[Constants.TRACE_DST_INDEX]) + 1));
		} else {
			counters.put(traceArr[Constants.TRACE_DST_INDEX], 1);
		}

		return counters;
	}

	public static HashMap<String, Integer> getProtocolCounter(
			String[] traceArr, String protocolDelim) {

		if (traceArr == null || traceArr.length == 0) {
			logger.error("Trace string is null or empty");
			return null;
		}
		String[] procolArr = traceArr[Constants.TRACE_PROTOCOL_INDEX]
				.split(protocolDelim);
		HashMap<String, Integer> counters = new HashMap<String, Integer>();

		for (String protocol : procolArr) {
			if (counters.containsKey(protocol)) {
				counters.put(protocol, ((Integer) counters.get(protocol) + 1));
			} else {
				counters.put(protocol, 1);
			}
		}

		return counters;
	}

	public static String[] parseTrace(String trace, String delim) {
		if (trace == null || trace.isEmpty()) {
			logger.error("Trace parsing is not possible. Empty or null string");
			return null;
		}
		return trace.split(delim);
	}
}
