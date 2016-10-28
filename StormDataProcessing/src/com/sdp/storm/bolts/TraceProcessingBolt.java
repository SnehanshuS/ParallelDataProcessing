package com.sdp.storm.bolts;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

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

public class TraceProcessingBolt implements IRichBolt {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			TraceProcessingBolt.class.getName());
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
			if (data != null && Constants.TYPE_TRACE.equals(data.getPlugin())) {
				collector.emit(new Values(data));
			} else {
				logger.info("####### DATA NULL OR NOT A TRACE TYPE");
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
