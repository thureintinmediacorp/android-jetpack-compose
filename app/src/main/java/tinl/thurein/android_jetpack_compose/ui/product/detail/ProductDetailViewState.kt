package tinl.thurein.android_jetpack_compose.ui.product.detail

import tinl.thurein.android_jetpack_compose.models.Product

sealed class ProductDetailViewState {
    data class Loading(val message: String = "Loading..."): ProductDetailViewState()
    data class Success(val product: Product): ProductDetailViewState()
    data class Empty(val message: String = "No story available at the moment!"): ProductDetailViewState()
    data class Error(val throwable: Throwable, val message: String? = throwable.message): ProductDetailViewState()
}
