package com.biglabs.spark.model;

import java.io.Serializable;

public class Device implements Serializable {
    //---- device
    public String deviceId;
    public String deviceName;
    public String deviceTypeId;
    public String deviceTypeName;
    public String deviceTypeModel;
    public String deviceTypeManufacturer;
    public String datapointType;
    public String datapointName;
    public String datapointUnit;
    public String datapointDataType;
    //---- host & area
    public String hostId;
    public String hostType;
    public String hostLat;
    public String hostLong;
    public String areaId;
    public String areaTypeName;
    public String areaPath;
}
