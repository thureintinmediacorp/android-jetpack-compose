package tinl.thurein.android_jetpack_compose.ui.product.listing

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tinl.thurein.android_jetpack_compose.models.Resource
import tinl.thurein.android_jetpack_compose.repositories.product.ProductRepository
import tinl.thurein.android_jetpack_compose.ui.common.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
): BaseViewModel() {

    private val _productListFlow: MutableStateFlow<ProductListViewState> = MutableStateFlow(
        ProductListViewState.Loading()
    )
    val productListViewState = _productListFlow

    fun fetchProductList() {
        viewModelScope.launch {
            getProductList()
        }
    }

    private suspend fun getProductList() {
        runCatching {
            val resource = productRepository.getProducts()
            val data = resource.data ?: emptyList()
            when(resource) {
                is Resource.Error -> _productListFlow.update { (ProductListViewState.Error(resource.throwable)) }
                is Resource.Success -> {
                    if (data.isEmpty()) {
                        _productListFlow.update { (ProductListViewState.Empty()) }
                    } else {
                        _productListFlow.update { (ProductListViewState.Success(products = data)) }
                    }
                }
            }
        }.onFailure { t ->
            _productListFlow.update { (ProductListViewState.Error(t)) }
        }
    }

    override fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.update { true }
            getProductList()
            _isRefreshing.update { false }
        }
    }
}