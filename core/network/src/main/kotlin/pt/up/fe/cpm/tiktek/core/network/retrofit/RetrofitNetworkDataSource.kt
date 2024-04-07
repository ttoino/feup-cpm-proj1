package pt.up.fe.cpm.tiktek.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import pt.up.fe.cpm.tiktek.core.model.AuthResponse
import pt.up.fe.cpm.tiktek.core.model.LoginRequest
import pt.up.fe.cpm.tiktek.core.model.RegisterRequest
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import javax.inject.Inject

private interface TikTekApi {
    // Auth
    @POST("login")
    suspend fun login(
        @Body body: LoginRequest,
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest,
    ): AuthResponse

    // Profile
    @GET("profile")
    suspend fun getProfile(): User

    @PUT("profile")
    suspend fun updateProfile(
        @Body body: User,
    ): User

    @DELETE("profile")
    suspend fun deleteProfile(): Boolean
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

        // Auth
        override suspend fun login(
            email: String,
            password: String,
        ) = api.login(LoginRequest(email, password))

        override suspend fun register(
            name: String,
            nif: String,
            birthdate: LocalDate,
            email: String,
            password: String,
            nameCc: String,
            numberCc: String,
            expirationDateCc: String,
            cvvCc: String,
        ): AuthResponse =
            api.register(
                RegisterRequest(
                    name,
                    nif,
                    birthdate,
                    email,
                    password,
                    nameCc,
                    numberCc,
                    expirationDateCc,
                    cvvCc,
                ),
            )

        // Profile
        override suspend fun getProfile(): User = api.getProfile()

        override suspend fun updateProfile(
            name: String,
            nif: String,
            birthdate: LocalDate,
            email: String,
            nameCc: String,
            numberCc: String,
            expirationDateCc: String,
            cvvCc: String,
        ): User =
            api.updateProfile(
                User(
                    name,
                    nif,
                    birthdate,
                    email,
                    nameCc,
                    numberCc,
                    expirationDateCc,
                    cvvCc,
                ),
            )

        override suspend fun deleteProfile(): Boolean = api.deleteProfile()
    }
