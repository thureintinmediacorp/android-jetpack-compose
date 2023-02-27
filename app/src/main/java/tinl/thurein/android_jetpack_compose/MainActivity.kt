package tinl.thurein.android_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import tinl.thurein.android_jetpack_compose.ui.product.detail.ProductDetailScreen
import tinl.thurein.android_jetpack_compose.ui.product.detail.ProductDetailViewModel
import tinl.thurein.android_jetpack_compose.ui.product.listing.ProductListScreen
import tinl.thurein.android_jetpack_compose.ui.product.listing.ProductListViewModel
import tinl.thurein.android_jetpack_compose.ui.theme.AndroidjetpackcomposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidjetpackcomposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "products") {

                        composable(route = "products") {
                            val viewModel = hiltViewModel<ProductListViewModel>()
                            ProductListScreen(
                                productListViewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable(
                            route = "product/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<ProductDetailViewModel>()
                            ProductDetailScreen(
                                productViewModel = viewModel,
                                id = backStackEntry.arguments?.getLong("id"),
                                navController = navController
                            )
                        }

                    }
                }
            }
        }
    }
}