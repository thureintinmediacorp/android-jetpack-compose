package tinl.thurein.android_jetpack_compose.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
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
): Parcelable
