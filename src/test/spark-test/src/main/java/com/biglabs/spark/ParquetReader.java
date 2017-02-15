package com.biglabs.spark;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by NgaVo on 2/15/2017.
 */
public class ParquetReader {
    private static final Logger logger = LoggerFactory.getLogger(ParquetReader.class);
    public void run(String[] ars){
        ClassLoader classLoader = getClass().getClassLoader();

        Config rootConf = ConfigFactory.parseResources(classLoader, "application.conf");
        System.out.print(rootConf.entrySet());
        String hdfsUrl  = "hdfs://192.168.1.73:9000/temp/parquet/1487135391716/part-r-00000-3d29b38e-a860-47d9-bb16-3ca518fa2c3e.snappy.parquet";

        Config spark = rootConf.getConfig("spark");
        String sparkMaster = spark.getString("master");// "local[*]";
        int sparkCleanerTtl = spark.getInt("cleaner.ttl");
        int streamingBatchInterval = spark.getInt("streaming.batch.interval");

        SparkConf conf = new SparkConf()
                .setAppName(ParquetReader.class.getSimpleName())
                .setMaster(sparkMaster)
                .set("spark.cleaner.ttl", String.valueOf(sparkCleanerTtl))
                .set("spark.logConf", "true")
                .set("spark.executor.cores", "5");

        SparkContext context = new SparkContext(conf);
        SparkSession sparkSession = SparkSession.builder().sparkContext(context).getOrCreate();

        Dataset<Row> dataSet = sparkSession.read().parquet(hdfsUrl);
        dataSet.printSchema();

        logger.info("Number of items: " + dataSet.count());
    }

    public static void main(String[] args){
        ParquetReader reader = new ParquetReader();
        reader.run(args);
    }
}
