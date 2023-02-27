package tinl.thurein.android_jetpack_compose.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val id: String,
    val name: String?
): Parcelable
