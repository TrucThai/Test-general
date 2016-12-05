package com.biglabs.spark;

import com.biglabs.spark.model.ModelHelper;
import com.biglabs.spark.model.RawDevice;
import org.apache.spark.streaming.api.java.JavaDStream;

public class ConsoleStreamingSink extends BaseStreamingSink<RawDevice> {

    @Override
    public void sink(JavaDStream<RawDevice> dataStream) {
        dataStream.foreachRDD((v1, v2) -> {
            v1.foreach(s -> System.out.println(ModelHelper.print(s)));
        });
    }
}
