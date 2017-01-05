package com.biglabs.coap;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.SharedMetricRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by lavalamp on 03/01/2017.
 */
public class Main {

    static Random random = new Random();
    static JedisPoolWraper jedisPool = new JedisPoolWraper("192.168.1.131",6379);
    static long BLOCK = 10000;
    static int SLEEP = 1000;

    public static void main(String[] args) throws Exception{

        if(args.length>0){
            BLOCK = Long.parseLong(args[0]);
            System.out.println("Send with block size : " + BLOCK + "reqs/" + SLEEP  + "ms.") ;
        }

        final ConsoleReporter reporter = ConsoleReporter.forRegistry( SharedMetricRegistries.getOrCreate(Profiler.METRIC_REGISTRY_NAME))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(5, TimeUnit.SECONDS);


        String[] deviceKeys =  Util.loadFromResource("devices.conf",1000);
        assert deviceKeys.length==1000;

        List<Device> devices = new ArrayList<>();

        for (String deviceKey: deviceKeys){
            Device device =  jedisPool.get(deviceKey,Device.class);
            if(device!=null){
                devices.add(device);
            }
        }
        assert devices.size()==1000 ;

        while(true){
            long t = System.currentTimeMillis();
            for (int i=0;i<BLOCK;i++){
                int index = random.nextInt(devices.size());
                EndpointPool.INSTANCE.post(genPayload(devices.get(index)));
            }
            t = SLEEP-(System.currentTimeMillis()-t);
            Thread.sleep((t>0)?t:100);
        }


    }

    private  static String genPayload(Device d){
        String device = d.deviceId;
        String metric = d.datapointType;
        double value = random.nextDouble();
        return String.format("%s %s %s %.2f double",device,metric,System.currentTimeMillis(),value);
    }
}
