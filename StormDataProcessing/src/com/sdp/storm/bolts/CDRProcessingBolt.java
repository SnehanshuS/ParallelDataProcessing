package com.sdp.storm.bolts;

import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.bean.CDRBean;
import com.sdp.bean.DataBean;
import com.sdp.logger.CustomFileLogger;
import com.sdp.parsestream.DataStreamParser;
import com.sdp.utilities.Constants;
import com.sdp.utilities.StormUtility;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class CDRProcessingBolt implements IRichBolt {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			CDRProcessingBolt.class.getName());
	private OutputCollector collector;
	DataStreamParser dsp = new DataStreamParser();

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;

	}

	@Override
	public void execute(Tuple input) {
		if (!(StormUtility.isTickTuple(input))) {
			DataBean data = ((DataBean) (input.getValue(0)));
			if (data != null && Constants.TYPE_CDR.equalsIgnoreCase(data.getPlugin())) {
				logger.info("*** CDR = " + data.getValue());
				String[] cdrArr = parseCDR(data.getValue(), Constants.CDR_DELIM);
				CDRBean cdrbean = new CDRBean();
				cdrbean.storeCdrValues(cdrArr, Constants.CDR_DICT);
				collector.emit(new Values(cdrbean));
			} else {
				logger.info("####### DATA NULL OR NOT A CDR TYPE");
			}
		}
		collector.ack(input);
	}

	public String[] parseCDR(String cdr, String delim) {
		if (cdr == null || cdr.isEmpty()) {
			return null;
		}
		return cdr.split(delim);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
