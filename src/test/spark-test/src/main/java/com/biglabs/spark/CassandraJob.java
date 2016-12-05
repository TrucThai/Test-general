package com.biglabs.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import org.apache.commons.collections.map.HashedMap;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.rdd.JdbcRDD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;
import scala.reflect.ClassManifestFactory$;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;

public class CassandraJob {
    private static final Logger logger = LoggerFactory.getLogger(CassandraJob.class);
    private static final String MYSQL_DRIVER = "org.postgresql.Driver";
    private static final String MYSQL_CONNECTION_URL = "jdbc:postgresql://192.168.1.69:5432/iot_antt";
    private static final String MYSQL_USERNAME = "iot";
    private static final String MYSQL_PWD = "123456";

    int sparkCleanerTtl = 3600 * 2;

    public static void main(String[] args) throws Exception{
        new CassandraJob().run(args);
    }

    public void run(String[] args) throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();

        Config rootConf = ConfigFactory.parseResources(classLoader, "apocalypse.conf");
        System.out.print(rootConf.entrySet());
        Config apocalypse = rootConf.getConfig("apocalypse");
        String CassandraKeyspace = apocalypse.getString("cassandra.power.keyspace");
        String rawUnCompressTable = apocalypse.getString("cassandra.power.table.raw_power_data_double");

        Config spark = rootConf.getConfig("spark");
        String sparkMaster = spark.getString("master");// "local[*]";
        Config cassandra = rootConf.getConfig("cassandra");
        String cassandraHosts = cassandra.getString("connection.host");//"localhost";
        String aggregatedDuration = apocalypse.getString("aggregatedDuration");

        Config kafkaProducerConfig = rootConf.getConfig("kafka-producer-druid");
        String producerTopic = kafkaProducerConfig.getString("topic.iot.double");

        SparkConf conf = new SparkConf()
                .setAppName(CassandraJob.class.getSimpleName())
                .setMaster(sparkMaster)
                .set("spark.cassandra.connection.host", cassandraHosts)
                .set("spark.cleaner.ttl", String.valueOf(sparkCleanerTtl))
                .set("spark.executor.memory", "8g")
                .set("spark.logConf", "true")
                .set("spark.cassandra.output.batch.size.rows", "400")
                .set("spark.cassandra.output.concurrent.writes", "100")
                .set("spark.cassandra.output.batch.size.bytes", "2000000")
                .set("spark.executor.cores", "5")
                .set("spark.cassandra.output.consistency.level", "ONE")
                .set("spark.cassandra.connection.keep_alive_ms", "60000");

        JavaSparkContext sc = new JavaSparkContext(conf);

        try {
            Tuple2<LocalDateTime, LocalDateTime> fromTo = parseAggregatedDuration(aggregatedDuration);
            JavaPairRDD<String, RawData> rawRdd = javaFunctions(sc).cassandraTable(
                    CassandraKeyspace, rawUnCompressTable, mapRowTo(RawData.class))
                    .where("time >= ? AND time <= ?",
                            localtimeToDate(fromTo._1()),
                            localtimeToDate(fromTo._2()))
                    .mapToPair(r -> new Tuple2(String.format("%s_%s",
                            r.getDeviceid(),
                            r.getDatapointname()),
                            r));
            rawRdd.cache();
            logger.info("===== Raw count {}", rawRdd.count());
            logger.info("=== rawRdd.partitions().size() = {}", rawRdd.partitions().size());
            List<Tuple2<String, RawData>> oneEle = rawRdd.take(10); //take action to flushing rdd to cache
            for(Tuple2<String, RawData> x : oneEle) {
                logger.info("===== Raw {}", x);
            }

        } catch (Exception e){
            logger.error("run failed", e);
        } finally {
            logger.info("============ DONE");
        }
    }

    private Date localtimeToDate(LocalDateTime fromTime) {
        return Date.from(fromTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Tuple2<LocalDateTime, LocalDateTime> parseAggregatedDuration(String aggregatedDuration)  {
        System.out.println("===== Aggregation Duration " + aggregatedDuration);
        Matcher matcher = Pattern.compile("(\\d+)([dhm])").matcher(aggregatedDuration.trim());
        matcher.matches();
        System.out.print(matcher.groupCount());
        if (matcher.groupCount() != 2) {
            throw new IllegalArgumentException("Invalid duration: " + aggregatedDuration);
        }
        int duration = Integer.parseInt(matcher.group(1));
        String timeUnit = matcher.group(2);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime from = to;
        switch (timeUnit) {
            case "d":
                from = to.minusDays(1);
                break;
            case "h":
                int tohour = duration * Math.round(now.getHour() / duration);
                to = to.plusHours(tohour);
                from = to.minusHours(duration);
                break;
            case "m":
                int toMinute = duration * Math.round(now.getMinute() / duration);
                to = to.plusHours(now.getHour());
                to = to.plusMinutes(toMinute);
                from = to.minusMinutes(duration);
                break;
            default:
                break;
        }
        logger.info("===== Query from {} to {}", from, to);
        return new Tuple2<>(from, to);
    }

    public static class RawData implements Serializable {
        private String deviceid;
        private String datapointname;
        private String day; // cassandra connector does not support LocalDateTime
        private String time;
        private double value;

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getDatapointname() {
            return datapointname;
        }

        public void setDatapointname(String datapointname) {
            this.datapointname = datapointname;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "RawData{" +
                    "deviceid='" + deviceid + '\'' +
                    ", datapointname='" + datapointname + '\'' +
                    ", day=" + day +
                    ", time=" + time +
                    ", value=" + value +
                    '}';
        }
    }
}
