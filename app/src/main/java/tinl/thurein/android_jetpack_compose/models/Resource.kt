package tinl.thurein.android_jetpack_compose.models

import tinl.thurein.android_jetpack_compose.AppException

sealed class Resource<T>(open val data: T?, open val throwable: Throwable?) {
    data class Success<T>(override val data: T): Resource<T>(data = data, throwable = null)
    data class Error<T>(override val throwable: Throwable, val errorMessage: String? = null): Resource<T>(data = null, throwable = throwable)
}

fun <T> Resource<T>.isSuccess() = this is Resource.Success<T>
fun <T> Resource<T>.isError() = this is Resource.Error<T>
