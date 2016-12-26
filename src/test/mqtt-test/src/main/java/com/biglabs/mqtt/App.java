package com.biglabs.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class App {
    public static void main(String[] args) throws MqttException {
        MqttClient client = new MqttClient("tcp://localhost:1883", "pahomqttpublish1");
        client.connect();
        MqttMessage message = new MqttMessage();
        message.setPayload("A single message".getBytes());
        client.publish("pahodemo/test", message);
        client.disconnect();
    }
}
