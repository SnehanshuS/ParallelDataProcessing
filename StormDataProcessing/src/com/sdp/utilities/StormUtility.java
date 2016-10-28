package com.sdp.utilities;

import backtype.storm.tuple.Tuple;

public class StormUtility {
	public static boolean isTickTuple(Tuple tuple){
				
		return tuple.getSourceComponent().equals(backtype.storm.Constants.SYSTEM_COMPONENT_ID)
		&& tuple.getSourceStreamId().equals(backtype.storm.Constants.SYSTEM_TICK_STREAM_ID);

	}
}
