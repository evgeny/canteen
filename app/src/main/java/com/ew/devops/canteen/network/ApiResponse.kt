package com.ew.devops.canteen.network

/**
 * high-level response for all api requests
 */
data class ApiResponse(
        var Error: String,
        var Content: Content,
        var FallbackUrl: String
)

/**
 * "newidentitiy" content
 */
data class Content(
        val ApiKey: String,
        val UserId: Int,
        val ProfileSecret: String,
        val ProfileToken: String
)

//data class Content2{
//    var Categories: Array<String>
//}