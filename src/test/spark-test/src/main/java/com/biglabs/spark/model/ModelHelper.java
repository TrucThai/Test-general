package com.biglabs.spark.model;

import org.apache.commons.lang3.time.DateUtils;
import scala.runtime.AbstractFunction1;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class ModelHelper {

    public static RawDoubleDevice createDoubleDevice(String[] strs){
        RawDoubleDevice device = new RawDoubleDevice();
        device.setDeviceid(strs[0]);
        device.setDatapointname(strs[1]);
        Date date = new Date(Long.parseLong(strs[2]));
        device.setDay(DateUtils.truncate(date, Calendar.DAY_OF_MONTH));
        device.setTime(date);
        device.setValue(Double.parseDouble(strs[3]));
        return device;
    }

    public static RawLongDevice createLongDevice(String[] strs){
        RawLongDevice device = new RawLongDevice();
        device.setDeviceid(strs[0]);
        device.setDatapointname(strs[1]);
        Date date = new Date(Long.parseLong(strs[2]));
        device.setDay(DateUtils.truncate(date, Calendar.DAY_OF_MONTH));
        device.setTime(date);
        device.setValue(Long.parseLong(strs[3]));
        return device;
    }

    public static RawDevice createDevice(String[] strs){
        RawDevice device = new RawDevice();
        device.setDeviceid(strs[0]);
        device.setDatapointname(strs[1]);
        device.setTime(Long.parseLong(strs[2]));
        if(strs.length > 4 && "double".equalsIgnoreCase(strs[4])){
            device.setValue(Double.parseDouble(strs[3]));
        } else{
            device.setValue(Long.parseLong(strs[3]));
        }
        return device;
    }

    public static RawDevice latest(RawDevice device1, RawDevice device2){
        return device1.getTime() > device2.getTime() ? device1 : device2;
    }

    public static String print(RawDevice device){
        return device.getDeviceid()
                + " " + device.getDatapointname()
                + " " + device.getTime()
                + " " + device.getValue();
    }
}
