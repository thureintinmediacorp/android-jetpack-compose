package tinl.thurein.android_jetpack_compose.network.service

import retrofit2.http.GET
import retrofit2.http.Path
import tinl.thurein.android_jetpack_compose.network.response.ProductListResponse
import tinl.thurein.android_jetpack_compose.network.response.ProductResponse

interface ProductService {
    @GET("products")
    suspend fun getProducts(): ProductListResponse

    @GET("product/{id}")
    suspend fun getProduct(@Path("id") id: Long?): ProductResponse
}