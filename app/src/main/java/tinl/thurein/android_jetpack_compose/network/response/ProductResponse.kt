package tinl.thurein.android_jetpack_compose.network.response

data class ProductResponse(
    val id: Long? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val discountPercentage: Double? = null,
    val rating: Double? = null,
    val stock: Long? = null,
    val brand: String? = null,
    val thumbnail: String? = null,
    val category: String? = null,
    val images: List<String>? = null
)