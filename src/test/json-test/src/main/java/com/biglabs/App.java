package com.biglabs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        Map valuesMap = new HashMap();
        valuesMap.put("device", "elevator");
        valuesMap.put("datapoint", "floor");
        valuesMap.put("value", "8");
        valuesMap.put("threshold", "10");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(valuesMap);
        System.out.println(json);
        Model model = mapper.readValue(json, Model.class);
        System.out.println(mapper.writeValueAsString(model));
    }

    public static class Model implements Serializable{
        public String device;
        public String datapoint;
        public String value;
        public String threshold;
    }
}
