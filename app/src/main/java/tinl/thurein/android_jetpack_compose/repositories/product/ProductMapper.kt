package tinl.thurein.android_jetpack_compose.repositories.product

import tinl.thurein.android_jetpack_compose.models.Product
import tinl.thurein.android_jetpack_compose.network.response.ProductListResponse
import tinl.thurein.android_jetpack_compose.network.response.ProductResponse

object ProductMapper {
    fun ProductResponse.toProduct() =
        Product(
            id = id,
            title = title,
            description = description,
            images = images,
            rating = rating,
            price = price,
            discountPercentage = discountPercentage,
            stock = stock,
            brand = brand,
            thumbnail = thumbnail,
            category = category
        )


    fun ProductListResponse.toProducts() = products.map { it.toProduct() }
}