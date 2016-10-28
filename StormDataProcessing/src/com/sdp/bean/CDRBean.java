package com.sdp.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.logger.CustomFileLogger;

public class CDRBean {

	private HashMap<String, String> dataMap;
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			CDRBean.class.getName());

	public CDRBean() {
		dataMap = new HashMap<String, String>();
	}

	public HashMap<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public void addFieldData(String fieldname, String value) {
		this.dataMap.put(fieldname, value);
	}

	public String getFieldData(String fieldname) {
		if (this.dataMap.containsKey(fieldname)) {
			return dataMap.get(fieldname);
		} else {
			return null;
		}
	}

	public void storeCdrValues(String[] cdrArr,
			HashMap<Integer, String> fieldMap) {
		if (cdrArr != null) {
			for (Map.Entry<Integer, String> entry : fieldMap.entrySet()) {
				if (entry.getKey() >= cdrArr.length) {
					logger.error("The index is out of bounds for the CDR array "
							+ cdrArr);
					return;
				}
				dataMap.put(entry.getValue(), cdrArr[entry.getKey()]);
			}
		} else {
			logger.error("CDR array is null");
		}
	}
}
