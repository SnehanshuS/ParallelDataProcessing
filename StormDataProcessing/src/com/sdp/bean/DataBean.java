package com.sdp.bean;

public class DataBean {

	private String host;
	private String value;
	private String plugin;
	private String plugin_instance;
	private String version;
	private String timestamp;
	private String[] tags;
	private String collectd_type;
	private String type_instance;
	
	public String getType_instance() {
		return type_instance;
	}

	public void setType_instance(String type_instance) {
		this.type_instance = type_instance;
	}

	public String getPlugin_instance() {
		return plugin_instance;
	}

	public void setPlugin_instance(String plugin_instance) {
		this.plugin_instance = plugin_instance;
	}

	public String getCollectd_type() {
		return collectd_type;
	}

	public void setCollectd_type(String collectd_type) {
		this.collectd_type = collectd_type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String toString() {
		return "******* host=" + host + ", value=" + value + ", plugin="
				+ plugin + ", version=" + version + ", timestamp=" + timestamp
				+ ", collected_type=" + collectd_type;
	}

}
