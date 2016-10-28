package com.sdp.storm;

import com.sdp.storm.bolts.DataBeanCreatorBolt;
import com.sdp.storm.bolts.LogProcessingBolt;
import com.sdp.storm.spouts.LineReaderSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class StormTopologyBuilder {

	public static void main(String[] args) throws Exception {
		Config config = new Config();
		config.put("inputFile", args[0]);
		config.setDebug(true);
		config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("line-reader-spout", new LineReaderSpout());
		builder.setBolt("databeancreator", new DataBeanCreatorBolt())
				.shuffleGrouping("kafkaspout");
		builder.setBolt("logprocessing", new LogProcessingBolt())
				.shuffleGrouping("databeancreator");

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("KafkaStorm", config, builder.createTopology());
		Thread.sleep(300000);

		cluster.shutdown();
	}

}