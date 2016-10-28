package com.sdp.storm;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

import com.sdp.storm.bolts.ASRBolt;
import com.sdp.storm.bolts.CDRProcessingBolt;
import com.sdp.storm.bolts.DataBeanCreatorBolt;
import com.sdp.storm.bolts.LogProcessingBolt;
import com.sdp.storm.bolts.SNMPPollProcessingBolt;
import com.sdp.storm.bolts.TraceProcessingBolt;
import com.sdp.storm.bolts.TraceProtocolCounterBolt;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

public class KafkaStormTopologyBuilder {
	private static int STORM_KAFKA_FROM_READ_FROM_CURRENT_OFFSET = -1;
	private static int NUMBER_OF_PARALLEL_TASK = 4;

	private static String topicName = "logstashdata";

	public static void main(String[] args) throws Exception {
		Config config = new Config();
		config.setDebug(true);
		config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		config.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 10);

		TopologyBuilder builder = new TopologyBuilder();

		BrokerHosts inputBrokerHost = new ZkHosts("10.201.2.148:2181");
		SpoutConfig kafkaConfig = new SpoutConfig(inputBrokerHost, topicName,
				"", "sdp");
		kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		kafkaConfig
				.forceStartOffsetTime(STORM_KAFKA_FROM_READ_FROM_CURRENT_OFFSET);
		builder.setSpout("kafkaspout", new KafkaSpout(kafkaConfig));
		builder.setBolt("databeancreator", new DataBeanCreatorBolt())
				.shuffleGrouping("kafkaspout");
		builder.setBolt("logprocessing", new LogProcessingBolt())
				.shuffleGrouping("databeancreator");
		builder.setBolt("traceprocessing", new TraceProcessingBolt())
				.shuffleGrouping("databeancreator");
		builder.setBolt("traceprotocolcounter", new TraceProtocolCounterBolt())
				.shuffleGrouping("traceprocessing");
		builder.setBolt("cdrprocessing", new CDRProcessingBolt())
				.shuffleGrouping("databeancreator");
		builder.setBolt("polledstats", new SNMPPollProcessingBolt())
				.shuffleGrouping("databeancreator");
		builder.setBolt("asrcalc", new ASRBolt()).shuffleGrouping(
				"cdrprocessing");

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("KafkaStorm", config, builder.createTopology());
		Thread.sleep(300000);

		cluster.shutdown();
	}

}
