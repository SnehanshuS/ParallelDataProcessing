package com.sdp.utilities;

public class WordSpliter{
	
	public static final String SPLIT_SPACE_DELIMITER = " "; 

	public static String[] splitString(String input, String delim) {
		
		String sentence = input;
		String[] words = sentence.split(delim);
		for(String word: words){
			word = word.trim();
			if(!word.isEmpty()){
				word = word.toLowerCase();
			}
		}
		return words;
	}
}
