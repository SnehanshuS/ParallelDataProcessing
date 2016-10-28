package com.sdp.utilities;

import java.util.HashMap;

public class WordCounter {

	public static HashMap<String, Integer> getCounters(String[] input) {
		HashMap<String, Integer> counters = new HashMap<String, Integer>();
		for (String str : input) {
			if (!counters.containsKey(str)) {
				counters.put(str, 1);
			} else {
				Integer c = counters.get(str) + 1;
				counters.put(str, c);
			}
		}
		return counters;
	}
}
