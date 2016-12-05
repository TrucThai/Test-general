package com.biglabs.spark;

import com.typesafe.config.Config;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public interface StreamingSource<T> {
    JavaDStream<T> getSource(JavaStreamingContext ssc, Config config);
}
