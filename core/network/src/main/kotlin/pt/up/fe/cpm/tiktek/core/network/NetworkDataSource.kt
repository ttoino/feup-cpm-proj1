package pt.up.fe.cpm.tiktek.core.network

import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.model.AuthResponse
import pt.up.fe.cpm.tiktek.core.model.BuyTicketResponse
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.SendCartRequest
import pt.up.fe.cpm.tiktek.core.model.SendTicketRequest
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.model.Voucher
import java.security.PrivateKey
import java.security.PublicKey

interface NetworkDataSource {
    // Auth
    suspend fun login(
        key: PublicKey,
        email: String,
        password: String,
    ): NetworkResult<AuthResponse>

    suspend fun register(
        key: PublicKey,
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
    suspend fun getCafeteriaItems(
        token: String,
        key: PrivateKey,
    ): NetworkResult<List<CafeteriaItem>>

    // Events
    suspend fun getEvents(
        token: String,
        key: PrivateKey,
    ): NetworkResult<List<Event>>

    suspend fun getEvent(
        token: String,
        key: PrivateKey,
        eventId: String,
    ): NetworkResult<Event>

    suspend fun buyTickets(
        token: String,
        key: PrivateKey,
        eventId: String,
        ticketAmount: Int,
    ): NetworkResult<BuyTicketResponse>

    // Orders
    suspend fun getOrders(
        token: String,
        key: PrivateKey,
    ): NetworkResult<List<Order>>

    // Profile
    suspend fun getProfile(
        token: String,
        key: PrivateKey,
    ): NetworkResult<User>

    suspend fun updateProfile(
        token: String,
        key: PrivateKey,
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

    suspend fun deleteProfile(
        token: String,
        key: PrivateKey,
    ): NetworkResult<Boolean>

    // Tickets
    suspend fun getTickets(
        token: String,
        key: PrivateKey,
    ): NetworkResult<List<Ticket>>

    suspend fun getTicket(
        token: String,
        key: PrivateKey,
        ticketId: String,
    ): NetworkResult<Ticket>

    // Vouchers
    suspend fun getVouchers(
        token: String,
        key: PrivateKey,
    ): NetworkResult<List<Voucher>>

    // Cafeteria Terminal
    suspend fun sendCart(request: SendCartRequest): NetworkResult<OrderWithModels>

    // Ticket Terminal
    suspend fun sendTicket(request: SendTicketRequest): NetworkResult<TicketWithEvent>
}
