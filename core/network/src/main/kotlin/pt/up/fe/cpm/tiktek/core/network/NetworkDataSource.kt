package pt.up.fe.cpm.tiktek.core.network

import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.model.AuthResponse
import pt.up.fe.cpm.tiktek.core.model.BuyTicketResponse
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.model.Cart
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.model.Voucher

interface NetworkDataSource {
    // Auth
    suspend fun login(
        email: String,
        password: String,
    ): NetworkResult<AuthResponse>

    suspend fun register(
        name: String,
        nif: String,
        birthdate: LocalDate,
        email: String,
        password: String,
        nameCc: String,
        numberCc: String,
        expirationDateCc: String,
        cvvCc: String,
    ): NetworkResult<AuthResponse>

    suspend fun partialRegister(
        name: String,
        nif: String,
        birthdate: LocalDate,
        email: String,
    ): NetworkResult<Unit>

    // Cafeteria
    suspend fun getCafeteriaItems(token: String): NetworkResult<List<CafeteriaItem>>

    // Events
    suspend fun getEvents(token: String): NetworkResult<List<Event>>

    suspend fun getEvent(
        token: String,
        eventId: String,
    ): NetworkResult<Event>

    suspend fun buyTickets(
        token: String,
        eventId: String,
        ticketAmount: Int,
    ): NetworkResult<BuyTicketResponse>

    // Orders
    suspend fun getOrders(token: String): NetworkResult<List<Order>>

    // Profile
    suspend fun getProfile(token: String): NetworkResult<User>

    suspend fun updateProfile(
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
    ): NetworkResult<User>

    suspend fun deleteProfile(token: String): NetworkResult<Boolean>

    // Tickets
    suspend fun getTickets(token: String): NetworkResult<List<Ticket>>

    suspend fun getTicket(
        token: String,
        ticketId: String,
    ): NetworkResult<Ticket>

    // Vouchers
    suspend fun getVouchers(token: String): NetworkResult<List<Voucher>>

    // Cafeteria Terminal
    suspend fun sendCart(
        userId: String,
        items: Cart,
    ): NetworkResult<OrderWithModels>
}
