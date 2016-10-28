package com.sdp.storm.bolts;

import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.logger.CustomFileLogger;
import com.sdp.parsestream.DataStreamParser;
import com.sdp.utilities.StormUtility;

import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class DataBeanCreatorBolt implements IRichBolt {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			DataBeanCreatorBolt.class.getName());
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

			//logger.info("****input****" + input.getString(0));
			Object obj = dsp.getParsedObj(input.getString(0)
					.replaceAll("@", ""));
			if (obj != null) {
				collector.emit(new Values(obj));
			} else {
				logger.info("******** NULL BEAN");
			}
		}
		collector.ack(input);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));

	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		
		return null;
	}
}
