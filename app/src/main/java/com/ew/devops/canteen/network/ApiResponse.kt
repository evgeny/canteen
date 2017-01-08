package com.ew.devops.canteen.network

/**
 * high-level response for all api requests
 */
data class ApiResponse<T>(
        var Error: String,
        var Content: T,
        var FallbackUrl: String
)

/**
 * "newidentitiy" content
 */
data class ContentNewIdentity(
        val ApiKey: String,
        val UserId: Int,
        val ProfileSecret: String,
        val ProfileToken: String
)

data class ContentMenu(
        val MenuCardId: Int
)

//data class Content2{
//    var Categories: Array<String>
//}