package tinl.thurein.android_jetpack_compose.ui.product.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import tinl.thurein.android_jetpack_compose.models.Product
import tinl.thurein.android_jetpack_compose.ui.common.AppBar
import tinl.thurein.android_jetpack_compose.ui.common.EmptyView
import tinl.thurein.android_jetpack_compose.ui.common.ErrorView
import tinl.thurein.android_jetpack_compose.ui.common.LoadingView
import tinl.thurein.android_jetpack_compose.ui.product.BodyText
import tinl.thurein.android_jetpack_compose.ui.product.ProductImage
import tinl.thurein.android_jetpack_compose.ui.product.TagText
import tinl.thurein.android_jetpack_compose.ui.product.TitleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    id: Long?,
    productViewModel: ProductDetailViewModel,
    navController: NavController
) {
    LaunchedEffect(key1 = id, block = {
        productViewModel.fetchProductDetail(id)
    })
    val productDetailViewState = productViewModel.productDetailViewState.collectAsState().value

    Scaffold(
        topBar = {
            AppBar(
                title = "Detail",
                isBackButtonVisible = true,
                onBack = { navController.navigateUp() }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                RenderProductDetailScreen(
                    productDetailViewState = productDetailViewState,
                    productDetailViewModel = productViewModel
                )
            }
        }
    )
}

@Composable
fun RenderProductDetailScreen(
    productDetailViewState: ProductDetailViewState,
    productDetailViewModel: ProductDetailViewModel
) {
    when(productDetailViewState) {
        is ProductDetailViewState.Loading ->
            LoadingView()

        is ProductDetailViewState.Empty ->
            EmptyView(message = productDetailViewState.message)

        is ProductDetailViewState.Success ->
            ProductDetailItem(
                productDetailViewModel = productDetailViewModel,
                product = productDetailViewState.product
            )

        is ProductDetailViewState.Error ->
            ErrorView(
                throwable = productDetailViewState.throwable,
                message = productDetailViewState.message
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailItem(
    productDetailViewModel: ProductDetailViewModel,
    product: Product
) {
    val isRefreshing by productDetailViewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { productDetailViewModel.onRefresh() }) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(PaddingValues(top = 10.dp))
        ) {
            LazyColumn {
                item {
                    ProductImage(
                        product = product,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
                        contentScale = ContentScale.FillWidth
                    )
                }

                product.title?.let { title ->
                    item {
                        TitleText(title = title)
                    }
                }

                item {
                    LazyRow(
                        modifier = Modifier.padding(PaddingValues(horizontal = 8.dp))
                    ) {
                        product.brand?.let { brand ->
                            item {
                                TagText(
                                    tag = brand,
                                    modifier = Modifier
                                        .padding(PaddingValues(end = 4.dp))
                                        .background(Color.Green)
                                )
                            }
                        }

                        product.category?.let { category ->
                            item {
                                TagText(
                                    tag = category,
                                    modifier = Modifier
                                        .background(Color.Cyan)
                                )
                            }
                        }
                    }
                }

                product.stock?.let { stock ->
                    item {
                        BodyText(body = "Available quantities => $stock")
                    }
                }

                product.price?.let { price ->
                    item {
                        BodyText(body = "$price $")
                    }
                }

                product.discountPercentage?.let { discount ->
                    item {
                        BodyText(body = "Discount : $discount %")
                    }
                }

                product.rating?.let { rating ->
                    item {
                        BodyText(body = "Rating => $rating")
                    }
                }

                product.stock?.let { stock ->
                    item {
                        BodyText(body = "Available quantities => $stock")
                    }
                }

                product.description?.let { description ->
                    item {
                        BodyText(body = description)
                    }
                }
            }
        }

    }
}