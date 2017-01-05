package com.biglabs.coap;

import com.codahale.metrics.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by lavalamp on 26/12/2016.
 */
public class Profiler {

    public static final String PREFIX = "iot.reporter.";
    public static final String METRIC_REGISTRY_NAME = PREFIX + "registry";
    public static Profiler INSTANCE = new Profiler();
    public CounterImpl CoapCounter = new CounterImpl(METRIC_REGISTRY_NAME,"CoapCounter");
//    public CounterImpl MqttCounter = new CounterImpl(METRIC_REGISTRY_NAME,"MqttCounter");
//    public CounterImpl RestCounter = new CounterImpl(METRIC_REGISTRY_NAME,"RestCounter");

    private Profiler(){
        registerDefault();
    }


    private  void registerDefault(){
//        SharedMetricRegistries.getOrCreate(METRIC_REGISTRY_NAME).register( "system.availableProcessors", new CachedGauge<Integer>(10, TimeUnit.MINUTES) {
//                    @Override
//                    protected Integer loadValue() {
//                        return Runtime.getRuntime().availableProcessors();
//                    }
//                }
//        );
//        SharedMetricRegistries.getOrCreate(METRIC_REGISTRY_NAME).register("system.freeMemory", new Gauge<Long>() {
//                    @Override
//                    public Long getValue() {
//                        return Runtime.getRuntime().freeMemory();
//                    }
//                }
//        );
//        SharedMetricRegistries.getOrCreate(METRIC_REGISTRY_NAME).register("queue.size", new Gauge<Integer>() {
//                    @Override
//                    public Integer getValue() {
//                        return TaskExcuter.INSTANCE.get().getQueue().size();
//                    }
//                }
//        );
//
//        SharedMetricRegistries.getOrCreate(METRIC_REGISTRY_NAME).register("active.count", new Gauge<Integer>() {
//                    @Override
//                    public Integer getValue() {
//                        return TaskExcuter.INSTANCE.get().getActiveCount();
//                    }
//                }
//        );
//
//        SharedMetricRegistries.getOrCreate(METRIC_REGISTRY_NAME).register("corepool.size", new Gauge<Integer>() {
//                    @Override
//                    public Integer getValue() {
//                        return TaskExcuter.INSTANCE.get().getCorePoolSize();
//                    }
//                }
//        );

//        SharedMetricRegistries.getOrCreate(METRIC_REGISTRY_NAME).register("system.maxMemory", new CachedGauge<Long>(10, TimeUnit.MINUTES) {
//                    @Override
//                    protected Long loadValue() {
//                        return Runtime.getRuntime().maxMemory();
//                    }
//                }
//        );
//        SharedMetricRegistries.getOrCreate(METRIC_REGISTRY_NAME).register("system.totalMemory", new CachedGauge<Long>(10, TimeUnit.MINUTES) {
//                    @Override
//                    protected Long loadValue() {
//                        return Runtime.getRuntime().totalMemory();
//                    }
//                }
//        );
    }
}
