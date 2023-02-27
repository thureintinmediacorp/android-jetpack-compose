package tinl.thurein.android_jetpack_compose.repositories.product

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tinl.thurein.android_jetpack_compose.models.Product
import tinl.thurein.android_jetpack_compose.models.Resource
import tinl.thurein.android_jetpack_compose.network.service.ProductService
import tinl.thurein.android_jetpack_compose.repositories.product.ProductMapper.toProduct
import tinl.thurein.android_jetpack_compose.repositories.product.ProductMapper.toProducts
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
) {
    private val TAG = "ProductRepository"
    suspend fun getProducts() = withContext(Dispatchers.IO) {
        try {
            val response = productService.getProducts()
            Log.e(TAG, "$response")
            val products = response.toProducts()
            return@withContext (Resource.Success(data = products))
        } catch (ex: Exception){
            Log.e(TAG, "$ex")
            return@withContext (Resource.Error<List<Product>>(throwable = ex))
        }
    }

    suspend fun getProduct(id: Long?) = withContext(Dispatchers.IO) {
        try {
            val response = productService.getProduct(id)
            Log.e(TAG, "$response")
            val product = response.toProduct()
            return@withContext (Resource.Success(data = product))
        } catch (ex: Exception){
            Log.e(TAG, "$ex")
            return@withContext (Resource.Error<Product>(throwable = ex))
        }
    }
}