package com.ew.devops.canteen.network

data class ApiResponse(
        var Error: String,
        var Content: Content,
        var FallbackUrl: String
)

data class Content(
        val ApiKey: String,
        val UserId: Int,
        val ProfileSecret: String,
        val ProfileToken: String
)