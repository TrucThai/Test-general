package com.biglabs.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class App {
    public static void main(String[] args) throws MqttException {
        MqttClient client = new MqttClient("tcp://localhost:1883", "91a8deb1d9814fc4a8da02462705a639");
        client.connect();
        MqttMessage message = new MqttMessage();
        message.setPayload("{\"name\":\"temperature\",\"value\":28, \"type\": \"long\"}".getBytes());
        client.publish("91a8deb1d9814fc4a8da02462705a639", message);
        client.disconnect();
    }
}
