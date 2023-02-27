package tinl.thurein.android_jetpack_compose.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import tinl.thurein.android_jetpack_compose.models.Product

@Composable
fun ProductImage(product: Product, modifier: Modifier, contentScale: ContentScale = ContentScale.Crop) {
    AsyncImage(
        model = product.thumbnail,
        contentDescription = "Product Detail",
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding(paddingValues = PaddingValues(
                bottom = 4.dp,
                start = 10.dp,
                end = 10.dp
            )
            )
    )
}

@Composable
fun BodyText(body: String) {
    Text(
        text = body,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(
            paddingValues = PaddingValues(
                bottom = 4.dp,
                start = 10.dp,
                end = 10.dp
            )
        )
    )
}

@Composable
fun TagText(tag: String, modifier: Modifier) {
    Text(
        text = tag,
        style = TextStyle(
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        ),
        modifier = modifier.padding(
            4.dp
        )
    )
}