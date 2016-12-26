package com.biglabs.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws MqttException {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    MqttClient client = new MqttClient("tcp://localhost:1883", "91a8deb1d9814fc4a8da02462705a639");
                    client.connect();
                    for (int i1 = 0; i1 < 1000; i1++) {
                        MqttMessage message = new MqttMessage();
                        message.setPayload("{\"name\":\"temperature\",\"value\":28, \"type\": \"long\"}".getBytes());
                        client.publish("91a8deb1d9814fc4a8da02462705a639", message);
                    }
                    client.disconnect();
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Total time (ms): " + duration);
        System.out.println("Medium: " + (duration/10000));
    }
}
