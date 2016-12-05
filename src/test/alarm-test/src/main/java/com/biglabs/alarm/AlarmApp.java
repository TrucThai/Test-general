package com.biglabs.alarm;

import com.biglabs.iot.model.Alarm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class AlarmApp {

    public static void main(String[] args) throws JsonProcessingException {
        List<Alarm> alarms = generate(30);
        Jedis jedis = new Jedis("192.168.1.131", 6379);
        ObjectMapper mapper = new ObjectMapper();
        for (Alarm alarm: alarms) {
            String json = mapper.writeValueAsString(alarm);
            jedis.hset("alarm-list", alarm.getId() + alarm.getDevice() + alarm.getDataPointName(), json);
        }
    }

    public static List<Alarm> generate(int n){
        List<Alarm> alarms = new ArrayList<Alarm>();
        for(int i = 0; i < n;i ++){
            Alarm alarm = new Alarm();
            alarm.setId("alarm" + i);
            alarm.setDevice("device" + i);
            alarm.setDataPointName("POWER");
            alarm.setMessage("Threshold Alarm");
            alarm.setTime(System.currentTimeMillis());
            alarms.add(alarm);
        }
        return alarms;
    }
}
