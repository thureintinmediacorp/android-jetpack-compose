package tinl.thurein.android_jetpack_compose.network.response

data class ProductListResponse(
    val products: List<ProductResponse>
): WrapperResponse()
