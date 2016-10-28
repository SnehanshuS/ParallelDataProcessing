package com.sdp.parsestream;

import java.io.IOException;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sdp.bean.DataBean;
import com.sdp.logger.CustomFileLogger;

public class DataStreamParser implements Serializable {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			DataStreamParser.class.getName());
	transient ObjectMapper objMapper = new ObjectMapper();

	public Object getParsedObj(String jsonStr) {
		try {
			if (objMapper == null) {
				objMapper = new ObjectMapper();
			}
			Object bean = objMapper.readValue(jsonStr, DataBean.class);
			logger.info("***bean***" + bean);
			return bean;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
