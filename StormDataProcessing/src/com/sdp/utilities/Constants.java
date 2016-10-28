package com.sdp.utilities;

import java.util.HashMap;

public class Constants {

	public static final String LOG_LOCATION = "/var/log/stormdp";
	public static final String LOG_FILENAME = "stormdp.log";

	public static final String TYPE_LOG = "log";
	public static final String TYPE_CDR = "cdr";
	public static final String TYPE_TRACE = "trace";
	public static final String TYPE_POLL  = "snmp";
	public static final HashMap<String, String> LOG_PATTERN = new HashMap<String, String>();
	public static final HashMap<Integer, String> CDR_DICT = new HashMap<Integer, String>();

	// CDR RELATED CONSTANTS
	public static final String CDR_DELIM = ";";
	public static final String CDR_DISCONNECT_ERROR = "disconnect-error-type";
	public static final String CDR_START_TIME = "start-time";
	public static final String NORMAL_CALL_CHAR = "N";
	
	//TRACE related constants
	public static final String TRACE_DELIM = ",";
	public static final String TRACE_PROTOCOL_DELIM = ":";
	public static final int TRACE_SRC_INDEX = 1;
	public static final int TRACE_DST_INDEX = 2;
	public static final int TRACE_PROTOCOL_INDEX = 6;
	
	static {
		LOG_PATTERN
				.put("iserverlog",
						"([\\w]+\\s[\\d]+\\s[\\d:]+)\\s([\\w]+)\\s([\\w]+)\\[([\\w]+)\\]:\\s(.[\\w]+):\\s(.[\\w]+):\\s([\\w]+)\\[.+\\]:\\s(.[\\w]+)\\s(.[\\w]+)\\s(.+)");

		CDR_DICT.put(12, CDR_DISCONNECT_ERROR);
		CDR_DICT.put(0, CDR_START_TIME);
	}
}
