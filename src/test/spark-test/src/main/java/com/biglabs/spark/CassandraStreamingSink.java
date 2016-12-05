package com.biglabs.spark;

import com.biglabs.spark.model.RawDevice;
import com.biglabs.spark.model.RawDoubleDevice;
import com.datastax.spark.connector.japi.CassandraStreamingJavaUtil;
import com.typesafe.config.Config;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.api.java.JavaDStream;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

public class CassandraStreamingSink extends BaseStreamingSink<RawDevice>{
    String keyspace;
    String table;
    String host;
    @Override
    public void init(Config config) {
        keyspace = config.getString("power.keyspace");
        table = config.getString("power.table.raw_power_data_double");
        host = config.getString("connection.host");
    }

    @Override
    public void addSparkConfig(SparkConf conf) {
        conf.set("spark.cassandra.connection.host", host)
                .set("spark.cassandra.output.batch.size.rows", "400")
                .set("spark.cassandra.output.concurrent.writes", "100")
                .set("spark.cassandra.output.batch.size.bytes", "2000000")
                .set("spark.cassandra.output.consistency.level", "ONE")
                .set("spark.cassandra.connection.keep_alive_ms", "60000");
    }

    @Override
    public void sink(JavaDStream<RawDevice> dataStream) {
        CassandraStreamingJavaUtil.javaFunctions(dataStream)
                .writerBuilder(keyspace, table, mapToRow(RawDevice.class)).saveToCassandra();
    }
}
