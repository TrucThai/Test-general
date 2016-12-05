package com.biglabs.iot.model

enum class ThresholdAlarmOperator(val value: Int) {
    GREATER(0x01), GREATEREQUAL(0x02), EQUAL(0x03), NOTEQUAL(0x04), LESS(0x05), LESSEQUAL(0x06);

    companion object {
        fun get(value: Int): ThresholdAlarmOperator? {
            for (t in ThresholdAlarmOperator.values())
                if(value==t.value){
                    return t;
                }
            return null;
        }
    }
    
}