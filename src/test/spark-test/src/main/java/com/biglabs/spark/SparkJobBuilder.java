package com.biglabs.spark;

import com.biglabs.spark.model.RawDevice;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkJobBuilder {
    int sparkCleanerTtl = 3600 * 2;

    public void run(String[] ars) throws InterruptedException {
        ClassLoader classLoader = getClass().getClassLoader();

        Config rootConf = ConfigFactory.parseResources(classLoader, "apocalypse.conf");
        Config spark = rootConf.getConfig("spark");
        String sparkMaster = spark.getString("master");// "local[*]";

        SparkConf conf = new SparkConf()
                .setAppName(SparkJobBuilder.class.getSimpleName())
                .setMaster(sparkMaster)
                .set("spark.cleaner.ttl", String.valueOf(sparkCleanerTtl))
                .set("spark.logConf", "true")
                .set("spark.executor.cores", "5");

        ConsoleStreamingSink sink = new ConsoleStreamingSink();
        sink.init(rootConf);
        sink.addSparkConfig(conf);

        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(5000));

        sink.startup(ssc.sparkContext());

        KafkaStreamingSource kafkaSource = new KafkaStreamingSource();
        JavaDStream<RawDevice> deviceStream = kafkaSource.getSource(ssc, rootConf.getConfig("kafka"));

        sink.sink(deviceStream);

        ssc.start();
        ssc.awaitTermination();
    }

    public static void main(String[] ars) throws InterruptedException {
        new SparkJobBuilder().run(ars);
    }
}
