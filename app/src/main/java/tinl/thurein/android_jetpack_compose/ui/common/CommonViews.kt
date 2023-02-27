package tinl.thurein.android_jetpack_compose.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp


@Composable
fun EmptyView(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = message)
    }

}

@Composable
fun LoadingView() {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        items(
            count = 15,
            itemContent = {
                Row {
                    Column {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .padding(10.dp)
                                .shimmerBackground(RoundedCornerShape(4.dp))
                        )
                    }
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(84.dp)
                                .padding(10.dp)
                                .shimmerBackground(RoundedCornerShape(4.dp))
                        )
                    }
                }
            }
        )
    }

}

@Composable
fun ErrorView(throwable: Throwable?, message: String?) {
    Text(text = "Hello world! ${throwable?.message} : $message")
}

fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
    )
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.4f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 100f, translateAnimation + 100f),
        tileMode = TileMode.Mirror,
    )
    return@composed this.then(background(brush, shape))
}

@Composable
fun AppBar(
    title: String,
    isBackButtonVisible: Boolean = true,
    onBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (isBackButtonVisible) {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Magenta,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}