package pt.up.fe.cpm.tiktek.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

private interface TikTekApi {
    @GET("events")
    suspend fun getEvents(): List<Event>
}

class RetrofitNetworkDataSource
    @Inject
    constructor(
        json: Json,
    ) : NetworkDataSource {
        private val api =
            Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
                .build()
                .create(TikTekApi::class.java)

        override suspend fun getEvents(): List<Event> = api.getEvents()
    }
