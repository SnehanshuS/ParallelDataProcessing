package com.sdp.storm.bolts;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.bean.DataBean;
import com.sdp.datacollector.TraceDataCollector;
import com.sdp.logger.CustomFileLogger;
import com.sdp.parsestream.DataStreamParser;
import com.sdp.utilities.Constants;
import com.sdp.utilities.StormUtility;
import com.sdp.utilities.TraceProcessingUtility;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class TraceProtocolCounterBolt implements IRichBolt {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			TraceProtocolCounterBolt.class.getName());
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
			if (data != null) {
				String[] traceArr = TraceProcessingUtility.parseTrace(
						data.getValue(), Constants.TRACE_DELIM);
				HashMap<String, Integer> protocolCounter = TraceProcessingUtility
						.getProtocolCounter(traceArr,
								Constants.TRACE_PROTOCOL_DELIM);
				TraceDataCollector.getInstance().updateProtocolCounters(
						protocolCounter);
			}
		} else {
			TraceDataCollector.getInstance().printProtocolCounts();
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
