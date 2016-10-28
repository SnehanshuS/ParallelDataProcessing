package com.sdp.storm.bolts;

import java.util.Map;

import org.apache.log4j.Logger;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import com.sdp.bean.DataBean;
import com.sdp.logger.CustomFileLogger;
import com.sdp.parsestream.DataStreamParser;
import com.sdp.utilities.Constants;
import com.sdp.utilities.StormUtility;

public class SNMPPollProcessingBolt implements IRichBolt{
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			SNMPPollProcessingBolt.class.getName());
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
			if (data != null && Constants.TYPE_POLL.equalsIgnoreCase(data.getPlugin())) {
				logger.info("TYPE INSTANCE--->>>"+data.getType_instance());
			} else {
				logger.info("####### DATA NULL OR NOT A " + Constants.TYPE_POLL);
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

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
