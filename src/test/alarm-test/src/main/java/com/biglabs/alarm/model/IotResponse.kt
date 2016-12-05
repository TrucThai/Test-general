package com.biglabs.iot.model

import java.io.Serializable

data class IotResponse (
        var status : ResponseStatus = ResponseStatus.ERROR,
        var message : String = "",
        var errorCode : String = "",
        var data : Any?,
        var extra_data : Any?
) : Serializable {
    constructor() : this(ResponseStatus.ERROR,"","",null, null)
    constructor(status : ResponseStatus, message : String, errorCode: String, data : Any?) :
    this(status, message, errorCode, data, null)
}