package com.biglabs.spark.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Device implements Serializable {
    //private static final long serialVersionUID =  1654279024112373855L;
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("device_name")
    private String deviceName;
    @JsonProperty("device_type_id")
    private String deviceTypeId;
    @JsonProperty("device_type_name")
    private String deviceTypeName;
    @JsonProperty("device_type_model")
    private String deviceTypeModel;
    @JsonProperty("device_type_manufacturer")
    private String deviceTypeManufacturer;
    @JsonProperty("datapoint_name")
    private String datapointName;
    @JsonProperty("datapoint_unit")
    private String datapointUnit;
    @JsonProperty("datapoint_data_type")
    private String datapointDataType;
    @JsonProperty("host_id")
    private String hostId;
    @JsonProperty("host_type")
    private String hostType;
    @JsonProperty("host_lat")
    private String hostLat;
    @JsonProperty("host_long")
    private String hostLong;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("area_id")
    private String areaId;
    @JsonProperty("value")
    private Double value;
    @JsonProperty("room")
    private String room;
    @JsonProperty("country")
    private String country;
    @JsonProperty("city")
    private String city;
    @JsonProperty("district")
    private String district;
    @JsonProperty("ward")
    private String ward;
    @JsonProperty("building")
    private String building;
    @JsonProperty("floor")
    private String floor;
    @JsonProperty("year")
    private String year;
    @JsonProperty("month")
    private String month;
    @JsonProperty("day")
    private String day;
    @JsonProperty("hour")
    private String hour;
    @JsonProperty("minute")
    private String minute;

    @JsonProperty("device_id")
    public String getDeviceId() {
        return deviceId;
    }

    @JsonProperty("device_id")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @JsonProperty("device_name")
    public String getDeviceName() {
        return deviceName;
    }

    @JsonProperty("device_name")
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @JsonProperty("device_type_id")
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    @JsonProperty("device_type_id")
    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    @JsonProperty("device_type_name")
    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    @JsonProperty("device_type_name")
    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    @JsonProperty("device_type_model")
    public String getDeviceTypeModel() {
        return deviceTypeModel;
    }

    @JsonProperty("device_type_model")
    public void setDeviceTypeModel(String deviceTypeModel) {
        this.deviceTypeModel = deviceTypeModel;
    }

    @JsonProperty("device_type_manufacturer")
    public String getDeviceTypeManufacturer() {
        return deviceTypeManufacturer;
    }

    @JsonProperty("device_type_manufacturer")
    public void setDeviceTypeManufacturer(String deviceTypeManufacturer) {
        this.deviceTypeManufacturer = deviceTypeManufacturer;
    }

    @JsonProperty("datapoint_name")
    public String getDatapointName() {
        return datapointName;
    }

    @JsonProperty("datapoint_name")
    public void setDatapointName(String datapointName) {
        this.datapointName = datapointName;
    }

    @JsonProperty("datapoint_unit")
    public String getDatapointUnit() {
        return datapointUnit;
    }

    @JsonProperty("datapoint_unit")
    public void setDatapointUnit(String datapointUnit) {
        this.datapointUnit = datapointUnit;
    }

    @JsonProperty("datapoint_data_type")
    public String getDatapointDataType() {
        return datapointDataType;
    }

    @JsonProperty("datapoint_data_type")
    public void setDatapointDataType(String datapointDataType) {
        this.datapointDataType = datapointDataType;
    }

    @JsonProperty("host_id")
    public String getHostId() {
        return hostId;
    }

    @JsonProperty("host_id")
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    @JsonProperty("host_type")
    public String getHostType() {
        return hostType;
    }

    @JsonProperty("host_type")
    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    @JsonProperty("host_lat")
    public String getHostLat() {
        return hostLat;
    }

    @JsonProperty("host_lat")
    public void setHostLat(String hostLat) {
        this.hostLat = hostLat;
    }

    @JsonProperty("host_long")
    public String getHostLong() {
        return hostLong;
    }

    @JsonProperty("host_long")
    public void setHostLong(String hostLong) {
        this.hostLong = hostLong;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("area_id")
    public String getAreaId() {
        return areaId;
    }

    @JsonProperty("area_id")
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @JsonProperty("value")
    public Double getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Double value) {
        this.value = value;
    }

    @JsonProperty("room")
    public String getRoom() {
        return room;
    }

    @JsonProperty("room")
    public void setRoom(String room) {
        this.room = room;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("district")
    public String getDistrict() {
        return district;
    }

    @JsonProperty("district")
    public void setDistrict(String district) {
        this.district = district;
    }

    @JsonProperty("ward")
    public String getWard() {
        return ward;
    }

    @JsonProperty("ward")
    public void setWard(String ward) {
        this.ward = ward;
    }

    @JsonProperty("building")
    public String getBuilding() {
        return building;
    }

    @JsonProperty("building")
    public void setBuilding(String building) {
        this.building = building;
    }

    @JsonProperty("floor")
    public String getFloor() {
        return floor;
    }

    @JsonProperty("floor")
    public void setFloor(String floor) {
        this.floor = floor;
    }

    @JsonProperty("year")
    public String getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(String year) {
        this.year = year;
    }

    @JsonProperty("month")
    public String getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(String month) {
        this.month = month;
    }

    @JsonProperty("day")
    public String getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(String day) {
        this.day = day;
    }

    @JsonProperty("hour")
    public String getHour() {
        return hour;
    }

    @JsonProperty("hour")
    public void setHour(String hour) {
        this.hour = hour;
    }

    @JsonProperty("minute")
    public String getMinute() {
        return minute;
    }

    @JsonProperty("minute")
    public void setMinute(String minute) {
        this.minute = minute;
    }
}
