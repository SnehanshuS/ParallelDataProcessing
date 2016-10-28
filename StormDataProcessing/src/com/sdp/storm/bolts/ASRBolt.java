package com.sdp.storm.bolts;

import java.util.Map;

import org.apache.log4j.Logger;

import com.sdp.bean.CDRBean;
import com.sdp.datacollector.CDRDataCollector;
import com.sdp.logger.CustomFileLogger;
import com.sdp.utilities.Constants;
import com.sdp.utilities.StormUtility;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class ASRBolt implements IRichBolt {
	private static Logger logger = CustomFileLogger.getInstance().getLogger(
			ASRBolt.class.getName());
	private OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;

	}

	@Override
	public void execute(Tuple input) {
		if (StormUtility.isTickTuple(input)) {
			float asr = CDRDataCollector.getInstance().getASR();
			logger.info("asr is ["+asr+"]");
		}else{
			CDRBean cdrBean = ((CDRBean) (input.getValue(0)));
			if(cdrBean != null){
				String connType = cdrBean.getFieldData(Constants.CDR_DISCONNECT_ERROR);
				CDRDataCollector.getInstance().updateconnTypeCounters(connType);
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
