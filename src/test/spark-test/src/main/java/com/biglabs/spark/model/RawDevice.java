package com.biglabs.spark.model;

import java.io.Serializable;

public class RawDevice implements Serializable {
    private String deviceid;
    private String datapointname;
    private Object value;
    private long time;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDatapointname() {
        return datapointname;
    }

    public void setDatapointname(String datapointname) {
        this.datapointname = datapointname;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
