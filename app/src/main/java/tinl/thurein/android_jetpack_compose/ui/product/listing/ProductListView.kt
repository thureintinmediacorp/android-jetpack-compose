package tinl.thurein.android_jetpack_compose.ui.product.listing

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
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
import tinl.thurein.android_jetpack_compose.ui.product.ProductImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productListViewModel: ProductListViewModel,
    navController: NavController
) {
    LaunchedEffect(key1 = Unit, block = {
        productListViewModel.fetchProductList()
    })
    val productListViewState = productListViewModel.productListViewState.collectAsState()

    Scaffold(
        topBar = { AppBar(
            title = "Home",
            isBackButtonVisible = false,
            onBack = { navController.navigateUp() }) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                ProductsView(
                    productListViewState = productListViewState.value,
                    navController = navController,
                    productListViewModel = productListViewModel
                )
            }
        }
    )


}

@Composable
fun ProductsView(
    productListViewState: ProductListViewState,
    navController: NavController,
    productListViewModel: ProductListViewModel
) {
    when(productListViewState) {
        is ProductListViewState.Loading ->
            LoadingView()

        is ProductListViewState.Empty ->
            EmptyView(message = productListViewState.message)

        is ProductListViewState.Success ->
            SuccessView(
                productListViewState = productListViewState,
                navController = navController,
                productListViewModel = productListViewModel
            )

        is ProductListViewState.Error ->
            ErrorView(
                throwable = productListViewState.throwable,
                message = productListViewState.message
            )
    }
}

@Composable
fun SuccessView(
    productListViewState: ProductListViewState.Success,
    navController: NavController,
    productListViewModel: ProductListViewModel
) {
    val isRefreshing by productListViewModel.isRefreshing.collectAsState()

    val products = remember { productListViewState.products }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { productListViewModel.onRefresh() }) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                count = products.size,
                itemContent = { index ->
                    ProductListItem(
                        product = products[index],
                        navController = navController
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListItem(
    product: Product,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = {
            navController.navigate("product/${product.id}")
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ProductImage(
                product = product,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)) {

                product.title?.let { title ->
                    Text(
                        text = title,
                        style = typography.headlineMedium,
                        modifier = Modifier.padding(
                            paddingValues = PaddingValues(bottom = 4.dp)
                        )
                    )
                }

                product.description?.let { description ->
                    Text(
                        text = description,
                        style = typography.bodyMedium,
                        modifier = Modifier.padding(
                            paddingValues = PaddingValues(bottom = 4.dp)
                        )
                    )
                }
            }
        }
    }
}