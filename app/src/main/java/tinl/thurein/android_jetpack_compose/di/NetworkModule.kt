package tinl.thurein.android_jetpack_compose.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tinl.thurein.android_jetpack_compose.network.service.ProductService
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideGsonConverterFactory(gson: Gson) = GsonConverterFactory.create(gson)

    @Provides
    @CommonInterceptorOkHttpClient
    fun provideCommonInterceptor() = Interceptor { chain ->
        val request = chain.request()
        chain.proceed(request)
    }
    @Provides
    fun provideOkHttpClient(
        @CommonInterceptorOkHttpClient commonInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(commonInterceptor)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl("https://dummyjson.com")
            .build()
    }

    @Provides
    fun provideLandingService(
        retrofit: Retrofit
    ): ProductService {
        return retrofit
            .create(ProductService::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CommonInterceptorOkHttpClient