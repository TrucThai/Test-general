package com.biglabs.spark;

import com.biglabs.spark.model.ModelHelper;
import com.biglabs.spark.model.RawDevice;
import com.typesafe.config.Config;
import kafka.serializer.StringDecoder;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class KafkaStreamingSource implements StreamingSource<RawDevice> {
    @Override
    public JavaDStream<RawDevice> getSource(JavaStreamingContext ssc, Config kafkaConfig) {
        String topics = kafkaConfig.getString("topic.power");
        String brokers = kafkaConfig.getString("hosts");

        java.util.Map<String, String> kafkaParams = new HashMap<String, String>();
        kafkaParams.put("metadata.broker.list", brokers);
        Set<String> topicsSet = new HashSet<>(Arrays.asList(topics));

        JavaPairInputDStream<String, String> rootStream = KafkaUtils.createDirectStream(
                ssc,
                String.class,
                String.class,
                StringDecoder.class,
                StringDecoder.class,
                kafkaParams,
                topicsSet
        );

        JavaDStream<RawDevice> rawStream = rootStream
                .map((Function<Tuple2<String, String>, String[]>) tuple2 -> tuple2._2().split(" "))
                .map(raw -> ModelHelper.createDevice(raw));

        return rawStream;
    }
}
