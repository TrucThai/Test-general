package com.biglabs.coap;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;

public class CounterImpl {

    private static final String totalRequestName="request.total";
    private static final String errorRequestName="request.error";
    private static final String successRequestName="request.success";

    private final String name;
    private final MetricRegistry registry ;
    private final String totalKey;
    private final String errorKey;
    private final String successKey;

    protected CounterImpl(String registryName, String uniqueName){
        this.name = uniqueName;
        this.totalKey = String.format("%s.%s",uniqueName,totalRequestName);
        this.errorKey = String.format("%s.%s",uniqueName,errorRequestName);
        this.successKey = String.format("%s.%s",uniqueName,successRequestName);

        registry =  SharedMetricRegistries.getOrCreate(registryName);
        registry.meter(totalKey);
        registry.meter(errorKey);
        registry.meter(successKey);
    }

    public void markError(){
        mark(errorKey);
        mark(totalKey);
    }

    public void markSuccess(){
        mark(successKey);
        mark(totalKey);
    }

    protected void mark(String key){
        registry.getMeters().get(key).mark();
    }



}
