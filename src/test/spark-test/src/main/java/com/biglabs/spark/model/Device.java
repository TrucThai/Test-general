package com.biglabs.spark.model;

import java.io.Serializable;

public class Device implements Serializable {
    //private static final long serialVersionUID =  1654279024112373855L;
    //---- device
    private String deviceId;
    private String deviceName;
    private String deviceTypeId;
    private String deviceTypeName;
    private String deviceTypeModel;
    private String deviceTypeManufacturer;
    private String datapointType;
    private String datapointName;
    private String datapointUnit;
    private String datapointDataType;
    //---- host & area
    private String hostId;
    private String hostType;
    private String hostLat;
    private String hostLong;
    private String areaId;
    private String areaTypeName;
    private String areaPath;

    //---- runtime value
    private String timestamp;
    private Object value;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceTypeModel() {
        return deviceTypeModel;
    }

    public void setDeviceTypeModel(String deviceTypeModel) {
        this.deviceTypeModel = deviceTypeModel;
    }

    public String getDeviceTypeManufacturer() {
        return deviceTypeManufacturer;
    }

    public void setDeviceTypeManufacturer(String deviceTypeManufacturer) {
        this.deviceTypeManufacturer = deviceTypeManufacturer;
    }

    public String getDatapointType() {
        return datapointType;
    }

    public void setDatapointType(String datapointType) {
        this.datapointType = datapointType;
    }

    public String getDatapointName() {
        return datapointName;
    }

    public void setDatapointName(String datapointName) {
        this.datapointName = datapointName;
    }

    public String getDatapointUnit() {
        return datapointUnit;
    }

    public void setDatapointUnit(String datapointUnit) {
        this.datapointUnit = datapointUnit;
    }

    public String getDatapointDataType() {
        return datapointDataType;
    }

    public void setDatapointDataType(String datapointDataType) {
        this.datapointDataType = datapointDataType;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public String getHostLat() {
        return hostLat;
    }

    public void setHostLat(String hostLat) {
        this.hostLat = hostLat;
    }

    public String getHostLong() {
        return hostLong;
    }

    public void setHostLong(String hostLong) {
        this.hostLong = hostLong;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaTypeName() {
        return areaTypeName;
    }

    public void setAreaTypeName(String areaTypeName) {
        this.areaTypeName = areaTypeName;
    }

    public String getAreaPath() {
        return areaPath;
    }

    public void setAreaPath(String areaPath) {
        this.areaPath = areaPath;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    // -- tao lao
    private String room;
    private String country;
    private String city;
    private String district;
    private String ward;
    private String building;
    private String floor;

    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
}
