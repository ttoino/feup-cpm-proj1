package pt.up.fe.cpm.tiktek.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import pt.up.fe.cpm.tiktek.core.model.AuthResponse
import pt.up.fe.cpm.tiktek.core.model.BuyTicketRequest
import pt.up.fe.cpm.tiktek.core.model.BuyTicketResponse
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.LoginRequest
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.PartialRegisterRequest
import pt.up.fe.cpm.tiktek.core.model.RegisterRequest
import pt.up.fe.cpm.tiktek.core.model.SendCartRequest
import pt.up.fe.cpm.tiktek.core.model.SendTicketRequest
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
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
import retrofit2.http.Path
import javax.inject.Inject

private interface TikTekApi {
    // Auth
    @POST("login")
    suspend fun login(
        @Body body: LoginRequest,
    ): NetworkResult<AuthResponse>

    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest,
    ): NetworkResult<AuthResponse>

    @POST("partial-register")
    suspend fun partialRegister(
        @Body body: PartialRegisterRequest,
    ): NetworkResult<Unit>

    // Cafeteria
    @GET("cafeteria")
    suspend fun getCafeteriaItems(
        @Header("Authorization") authorization: String,
    ): NetworkResult<List<CafeteriaItem>>

    // Events
    @GET("events")
    suspend fun getEvents(
        @Header("Authorization") authorization: String,
    ): NetworkResult<List<Event>>

    @GET("events/{id}")
    suspend fun getEvent(
        @Header("Authorization") authorization: String,
        @Path("id") eventId: String,
    ): NetworkResult<Event>

    @POST("events/{id}/buy")
    suspend fun buyTickets(
        @Header("Authorization") authorization: String,
        @Path("id") eventId: String,
        @Body body: BuyTicketRequest,
    ): NetworkResult<BuyTicketResponse>

    // Orders
    @GET("orders")
    suspend fun getOrders(
        @Header("Authorization") authorization: String,
    ): NetworkResult<List<Order>>

    // Profile
    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") authorization: String,
    ): NetworkResult<User>

    @PUT("profile")
    suspend fun updateProfile(
        @Header("Authorization") authorization: String,
        @Body body: User,
    ): NetworkResult<User>

    @DELETE("profile")
    suspend fun deleteProfile(
        @Header("Authorization") authorization: String,
    ): NetworkResult<Boolean>

    // Tickets
    @GET("tickets")
    suspend fun getTickets(
        @Header("Authorization") authorization: String,
    ): NetworkResult<List<Ticket>>

    @GET("tickets/{id}")
    suspend fun getTicket(
        @Header("Authorization") authorization: String,
        @Path("id") eventId: String,
    ): NetworkResult<Ticket>

    // Vouchers
    @GET("vouchers")
    suspend fun getVouchers(
        @Header("Authorization") authorization: String,
    ): NetworkResult<List<Voucher>>

    // Send cart -> feature for cafeteria terminal
    @POST("cart")
    suspend fun sendCart(
        @Body body: SendCartRequest,
    ): NetworkResult<OrderWithModels>

    // Send ticket -> feature for ticket terminal
    @POST("validateTicket")
    suspend fun sendTicket(
        @Body body: SendTicketRequest,
    ): NetworkResult<TicketWithEvent>
}

private val String.auth
    get() = "Bearer $this"

class RetrofitNetworkDataSource
    @Inject
    internal constructor(
        json: Json,
        adapterFactory: ResultCallAdapterFactory,
    ) : NetworkDataSource {
        private val api =
            Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
                .addCallAdapterFactory(adapterFactory)
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
        ): NetworkResult<AuthResponse> =
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

        override suspend fun partialRegister(
            name: String,
            nif: String,
            birthdate: LocalDate,
            email: String,
        ): NetworkResult<Unit> =
            api.partialRegister(
                PartialRegisterRequest(
                    name,
                    nif,
                    birthdate,
                    email,
                ),
            )

        // Cafeteria
        override suspend fun getCafeteriaItems(token: String): NetworkResult<List<CafeteriaItem>> = api.getCafeteriaItems(token.auth)

        // Events
        override suspend fun getEvents(token: String): NetworkResult<List<Event>> = api.getEvents(token.auth)

        override suspend fun getEvent(
            token: String,
            eventId: String,
        ): NetworkResult<Event> = api.getEvent(token.auth, eventId)

        override suspend fun buyTickets(
            token: String,
            eventId: String,
            ticketAmount: Int,
        ): NetworkResult<BuyTicketResponse> = api.buyTickets(token.auth, eventId, BuyTicketRequest(ticketAmount))

        // Orders
        override suspend fun getOrders(token: String): NetworkResult<List<Order>> = api.getOrders(token.auth)

        // Profile
        override suspend fun getProfile(token: String): NetworkResult<User> = api.getProfile(token.auth)

        override suspend fun updateProfile(
            token: String,
            id: String,
            name: String,
            nif: String,
            birthdate: LocalDate,
            email: String,
            nameCc: String,
            numberCc: String,
            expirationDateCc: String,
            cvvCc: String,
        ): NetworkResult<User> =
            api.updateProfile(
                token.auth,
                User(
                    id,
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

        override suspend fun deleteProfile(token: String): NetworkResult<Boolean> = api.deleteProfile(token.auth)

        // Tickets
        override suspend fun getTickets(token: String): NetworkResult<List<Ticket>> = api.getTickets(token.auth)

        override suspend fun getTicket(
            token: String,
            ticketId: String,
        ): NetworkResult<Ticket> = api.getTicket(token.auth, ticketId)

        // Vouchers
        override suspend fun getVouchers(token: String): NetworkResult<List<Voucher>> = api.getVouchers(token.auth)

        override suspend fun sendCart(request: SendCartRequest): NetworkResult<OrderWithModels> = api.sendCart(request)

        override suspend fun sendTicket(ticket: Ticket): NetworkResult<TicketWithEvent> = api.sendTicket(SendTicketRequest(ticket))
    }
