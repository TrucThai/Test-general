package com.biglabs.spark;

import org.apache.spark.streaming.api.java.JavaDStream;

public interface StreamingTransfomer<T1, T2> {
    JavaDStream<T2> transform(JavaDStream<T1> dataStream);
}
