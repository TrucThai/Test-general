package com.biglabs.iot.model

import java.io.Serializable

data class ThresholdAlarm(
        var alarmId: String,
        var value: Any,
        var operator: ThresholdAlarmOperator,
        var message: String?
) : Serializable {
    constructor(): this("", 0, ThresholdAlarmOperator.GREATER, null)
}
