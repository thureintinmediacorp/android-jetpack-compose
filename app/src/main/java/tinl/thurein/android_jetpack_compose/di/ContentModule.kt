package tinl.thurein.android_jetpack_compose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tinl.thurein.android_jetpack_compose.network.service.ProductService
import tinl.thurein.android_jetpack_compose.repositories.product.ProductRepository

@Module
@InstallIn(ViewModelComponent::class)
object ContentModule {
    @Provides
    fun provideLandingRepository(productService: ProductService) = ProductRepository(productService)
}