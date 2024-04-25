package pt.up.fe.cpm.tiktek.core.network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import pt.up.fe.cpm.tiktek.core.network.retrofit.ResultCallAdapterFactory
import pt.up.fe.cpm.tiktek.core.network.retrofit.RetrofitNetworkDataSource
import pt.up.fe.cpm.tiktek.core.network.retrofit.SignedRequestInterceptor
import java.security.SecureRandom
import java.security.Signature
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {
    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        @Provides
        @Singleton
        fun providesJson() =
            Json {
                prettyPrint = false
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }

        @Provides
        @Singleton
        fun providesSignature() = Signature.getInstance("SHA256WithRSA")

        @Provides
        @Singleton
        fun providesSecureRandom() = SecureRandom()

        @Provides
        @Singleton
        fun providesOkhttpClient(interceptor: SignedRequestInterceptor) = OkHttpClient.Builder().addNetworkInterceptor(interceptor).build()

        @Provides
        @Singleton
        fun providesResultCallAdapter(json: Json) = ResultCallAdapterFactory(json)
    }

    @Binds
    abstract fun bindNetworkDataSource(impl: RetrofitNetworkDataSource): NetworkDataSource
}
