package com.ew.devops.canteen.network

/**
 * wrapper response for all api requests
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

//---- MENU ------
data class ContentMenu(
        val MenuCardId: Int,
        val Categories: List<Category>
)

data class Category(val Id: Int, val Name: String, val Products: List<Product>)

data class Product(val Id: Int, val Name: String)