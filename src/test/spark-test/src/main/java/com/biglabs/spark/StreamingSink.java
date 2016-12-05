package com.biglabs.spark;

import com.typesafe.config.Config;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.api.java.JavaDStream;

public interface StreamingSink<T> {
    void init(Config config);
    void addSparkConfig(SparkConf conf);
    void startup(JavaSparkContext context);
    void sink(JavaDStream<T> dataStream);
}
