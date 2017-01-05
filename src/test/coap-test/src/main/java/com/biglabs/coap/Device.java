package com.biglabs.coap;

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
    
    //---- runtime value
    public String timestamp;
    public Object value;


    @Override
    public String toString() {
        return "DruidData{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceTypeId='" + deviceTypeId + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", deviceTypeModel='" + deviceTypeModel + '\'' +
                ", deviceManufacturer='" + deviceTypeManufacturer + '\'' +
                ", datapointType='" + datapointType + '\'' +
                ", datapointName='" + datapointName + '\'' +
                ", datapointUnit='" + datapointUnit + '\'' +
                ", datapointDataType='" + datapointDataType + '\'' +
                ", hostId='" + hostId + '\'' +
                ", hostType='" + hostType + '\'' +
                ", hostLat='" + hostLat + '\'' +
                ", hostLong='" + hostLong + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaTypeName='" + areaTypeName + '\'' +
                ", areaPath='" + areaPath + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
