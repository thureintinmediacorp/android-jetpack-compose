package tinl.thurein.android_jetpack_compose.ui.product.listing

import tinl.thurein.android_jetpack_compose.models.Product

sealed class ProductListViewState {
    data class Loading(val message: String = "Loading..."): ProductListViewState()
    data class Success(val products: List<Product>): ProductListViewState()
    data class Empty(val message: String = "No story available at the moment!"): ProductListViewState()
    data class Error(val throwable: Throwable, val message: String? = throwable.message): ProductListViewState()
}
