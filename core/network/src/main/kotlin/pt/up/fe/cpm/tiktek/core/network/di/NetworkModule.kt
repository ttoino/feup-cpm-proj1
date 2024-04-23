package pt.up.fe.cpm.tiktek.core.network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import pt.up.fe.cpm.tiktek.core.network.retrofit.ResultCallAdapterFactory
import pt.up.fe.cpm.tiktek.core.network.retrofit.RetrofitNetworkDataSource
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
        fun providesResultCallAdapter(json: Json) = ResultCallAdapterFactory(json)
    }

    @Binds
    abstract fun bindNetworkDataSource(impl: RetrofitNetworkDataSource): NetworkDataSource
}
