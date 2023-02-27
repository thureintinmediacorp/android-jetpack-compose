package tinl.thurein.android_jetpack_compose.ui.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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
class ProductDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
): BaseViewModel() {
    private val _productDetailFlow: MutableStateFlow<ProductDetailViewState> = MutableStateFlow(
        ProductDetailViewState.Loading()
    )
    val productDetailViewState = _productDetailFlow

    fun fetchProductDetail(id: Long?) {
        viewModelScope.launch {
            getProduct(id)
        }
    }

    private suspend fun getProduct(id: Long?) {
        runCatching {
            val resource = productRepository.getProduct(id = id)
            val data = resource.data
            when(resource) {
                is Resource.Error -> _productDetailFlow.update { (ProductDetailViewState.Error(resource.throwable)) }
                is Resource.Success -> {
                    if (data == null) {
                        _productDetailFlow.update { (ProductDetailViewState.Empty()) }
                    } else {
                        _productDetailFlow.update { (ProductDetailViewState.Success(product = data)) }
                    }
                }
            }
        }.onFailure { t ->
            _productDetailFlow.update { (ProductDetailViewState.Error(t)) }
        }
    }

    override fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.update { true }
            getProduct(savedStateHandle["id"])
            _isRefreshing.update { false }
        }
    }
}