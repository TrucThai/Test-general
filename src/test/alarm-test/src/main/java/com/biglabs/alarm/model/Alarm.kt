package com.biglabs.iot.model;
import com.biglabs.iot.model.AlarmState
import java.io.Serializable

data class Alarm(
        var id: String?,
        var instanceId: String?,
        var state: AlarmState,
        var device: String?,
        var dataPointName: String?,
        var message: String?,
        var time: Long,
        var returnTime: Long
) : Serializable{
    constructor(): this(null, null,  AlarmState.NEW, null, null, null, 0, 0)
}