package pt.up.fe.cpm.tiktek.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import pt.up.fe.cpm.tiktek.core.model.AuthResponse
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.LoginRequest
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.model.RegisterRequest
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.model.Voucher
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
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

    // Cafeteria
    @GET("cafeteria")
    suspend fun getCafeteriaItems(
        @Header("Authorization") authorization: String,
    ): List<CafeteriaItem>

    // Events
    @GET("events")
    suspend fun getEvents(
        @Header("Authorization") authorization: String,
    ): List<Event>

    // Orders
    @GET("orders")
    suspend fun getOrders(
        @Header("Authorization") authorization: String,
    ): List<Order>

    // Profile
    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") authorization: String,
    ): User

    @PUT("profile")
    suspend fun updateProfile(
        @Header("Authorization") authorization: String,
        @Body body: User,
    ): User

    @DELETE("profile")
    suspend fun deleteProfile(
        @Header("Authorization") authorization: String,
    ): Boolean

    // Tickets
    @GET("tickets")
    suspend fun getTickets(
        @Header("Authorization") authorization: String,
    ): List<Ticket>

    // Vouchers
    @GET("vouchers")
    suspend fun getVouchers(
        @Header("Authorization") authorization: String,
    ): List<Voucher>
}

private val String.auth
    get() = "Bearer $this"

class RetrofitNetworkDataSource
    @Inject
    constructor(
        json: Json,
    ) : NetworkDataSource {
        private val api =
            Retrofit.Builder()
                .baseUrl("http://192.168.105.39:8080")
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

        // Cafeteria
        override suspend fun getCafeteriaItems(token: String): List<CafeteriaItem> = api.getCafeteriaItems(token.auth)

        // Events
        override suspend fun getEvents(token: String): List<Event> = api.getEvents(token.auth)

        // Orders
        override suspend fun getOrders(token: String): List<Order> = api.getOrders(token.auth)

        // Profile
        override suspend fun getProfile(token: String): User = api.getProfile(token.auth)

        override suspend fun updateProfile(
            token: String,
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
                token.auth,
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

        override suspend fun deleteProfile(token: String): Boolean = api.deleteProfile(token.auth)

        // Tickets
        override suspend fun getTickets(token: String): List<Ticket> = api.getTickets(token.auth)

        // Vouchers
        override suspend fun getVouchers(token: String): List<Voucher> = api.getVouchers(token.auth)
    }
