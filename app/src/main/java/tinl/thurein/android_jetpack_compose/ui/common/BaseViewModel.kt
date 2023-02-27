package tinl.thurein.android_jetpack_compose.ui.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel: ViewModel() {
    protected val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing

    abstract fun onRefresh()
}