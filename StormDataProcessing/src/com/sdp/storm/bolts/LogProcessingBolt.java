package com.sdp.storm.bolts;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.bean.DataBean;
import com.sdp.datacollector.LogDataCollector;
import com.sdp.logger.CustomFileLogger;
import com.sdp.parsestream.DataStreamParser;
import com.sdp.utilities.Constants;
import com.sdp.utilities.LogProcessingUtility;
import com.sdp.utilities.StormUtility;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import java.util.regex.Matcher;

public class LogProcessingBolt implements IRichBolt {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			LogProcessingBolt.class.getName());
	private OutputCollector collector;
	DataStreamParser dsp = new DataStreamParser();

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;

	}

	@Override
	public void execute(Tuple input) {

		if (StormUtility.isTickTuple(input)) {
			LogDataCollector.getInstance().printCounter();
		} else {

			DataBean data = ((DataBean) (input.getValue(0)));
			if (data != null && Constants.TYPE_LOG.equals(data.getPlugin())) {

				Matcher match = LogProcessingUtility.getLogDetails(
						data.getValue(), data.getCollectd_type());
				if (match != null && match.matches()) {
					HashMap<String, Integer> counter = LogProcessingUtility
							.getLogLevelCounter(match, 5);
					LogDataCollector.getInstance().updateLogCounters(counter);
					logger.info("********LOG COUNTER "
							+ LogDataCollector.getInstance());
				} else {
					logger.error("No match found " + data);
				}
			} else {
				logger.info("####### DATA NULL OR NOT A LOG TYPE");
			}
			collector.ack(input);
		}
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
