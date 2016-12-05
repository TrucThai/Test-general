package com.biglabs.spark;

import com.typesafe.config.Config;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.api.java.JavaDStream;

public abstract class BaseStreamingSink<T> implements StreamingSink<T> {
    @Override
    public void init(Config config) {

    }

    @Override
    public void addSparkConfig(SparkConf conf) {

    }

    @Override
    public void startup(JavaSparkContext context) {

    }

    @Override
    public void sink(JavaDStream<T> dataStream) {

    }
}
