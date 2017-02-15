package com.biglabs.spark;

import com.biglabs.spark.model.Device;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import kafka.serializer.StringDecoder;
import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.io.Serializable;
import java.util.*;

/**
 * Created by NgaVo on 2/14/2017.
 */
public class ParquetStreamWriter {
    private static final Logger logger = LoggerFactory.getLogger(ParquetStreamWriter.class);

    private void run(String[] ars) throws InterruptedException {
        ClassLoader classLoader = getClass().getClassLoader();

        Config rootConf = ConfigFactory.parseResources(classLoader, "application.conf");
        System.out.print(rootConf.entrySet());
        Config kafka = rootConf.getConfig("kafka-input");
        String brokers = kafka.getString("brokers");
        String hdfsUrl  = "hdfs://192.168.1.73:9000/temp/";

        Config spark = rootConf.getConfig("spark");
        String sparkMaster = spark.getString("master");// "local[*]";
        int sparkCleanerTtl = spark.getInt("cleaner.ttl");
        int streamingBatchInterval = spark.getInt("streaming.batch.interval");

        SparkConf conf = new SparkConf()
                .setAppName(ParquetStreamWriter.class.getSimpleName())
                .setMaster(sparkMaster)
                .set("spark.cleaner.ttl", String.valueOf(sparkCleanerTtl))
                .set("spark.logConf", "true")
                .set("spark.executor.cores", "5");

        Config kafkaProducerConfig = rootConf.getConfig("kafka-producer-druid");
        String druidTopic =  "druid-test";

        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(streamingBatchInterval));

        ssc.sparkContext().hadoopConfiguration().set("fs.hdfs.impl", DistributedFileSystem.class.getName());
        ssc.sparkContext().hadoopConfiguration().set("fs.file.impl", LocalFileSystem.class.getName());
        SparkSession sparkSession = SparkSession.builder().sparkContext(ssc.sparkContext().sc()).getOrCreate();

        Broadcast<ObjectMapperProvider> mapperProviderBroadcast = ssc.sparkContext().broadcast(new ObjectMapperProvider());

        Map<String, Object> producerConfig = new HashedMap();
        for (Map.Entry<String, ConfigValue> e: kafkaProducerConfig.getConfig("producer").entrySet()) {
            logger.info("====== Produder config {} - {}", e.getKey(), e.getValue().unwrapped());
            producerConfig.put(e.getKey(), e.getValue().unwrapped());
        }

        java.util.Map<String, String> kafkaParams = new HashMap<String, String>();
        kafkaParams.put("metadata.broker.list", brokers);
        Set<String> topicsSet = new HashSet<>(Arrays.asList(druidTopic));

        JavaPairInputDStream<String, String> rootStream = null;
        try {
            rootStream = KafkaUtils.createDirectStream(
                    ssc,
                    String.class,
                    String.class,
                    StringDecoder.class,
                    StringDecoder.class,
                    kafkaParams,
                    topicsSet
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        JavaDStream<String> deviceStrings = rootStream.map(tuple -> tuple._2);
        deviceStrings.repartition(1).foreachRDD(stringrdd -> stringrdd.saveAsTextFile(hdfsUrl + "raw/" + System.currentTimeMillis(), GzipCodec.class));

        JavaDStream<Device> devices = rootStream.flatMap(tuple -> {
            ObjectMapper mapper = mapperProviderBroadcast.getValue().getMapper();
            ArrayList<Device> deviceList = new ArrayList<Device>();
            try {
                Device d = mapper.readValue(tuple._2(), Device.class);
                deviceList.add(d);
            } catch (Exception e){
                logger.error("parse device failed:\n " + e.getMessage());
            }
            return deviceList.iterator();
        });

        devices.foreachRDD(deviceRDD -> {
            Dataset<Row> df = sparkSession.createDataFrame(deviceRDD, Device.class);
            //long count = df.count();
            //logger.info("Number of items: " + df.count());
            //if(count > 0) {
                try {
                    df.repartition(1).write().parquet(hdfsUrl + "parquet/" + System.currentTimeMillis());
                } catch (Exception e) {
                    logger.error("failed to write\n" + e.getMessage());
                }
            //}
            //df.count();
        });

        ssc.start();
        ssc.awaitTermination();
    }

    public static void main(String[] args) throws InterruptedException {
        ParquetStreamWriter app = new ParquetStreamWriter();
        app.run(args);
    }

    public static class ObjectMapperProvider implements Serializable{
        private ObjectMapper mapper = null;

        public ObjectMapper getMapper(){
            synchronized (this){
                if(mapper == null){
                    mapper = new ObjectMapper();
                }
            }
            return mapper;
        }
    }
}
