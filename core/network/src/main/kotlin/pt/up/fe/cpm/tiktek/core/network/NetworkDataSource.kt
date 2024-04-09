package pt.up.fe.cpm.tiktek.core.network

import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.model.AuthResponse
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.model.Voucher

interface NetworkDataSource {
    // Auth
    suspend fun login(
        email: String,
        password: String,
    ): AuthResponse

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
    ): AuthResponse

    // Cafeteria
    suspend fun getCafeteriaItems(token: String): List<CafeteriaItem>

    // Events
    suspend fun getEvents(token: String): List<Event>

    // Orders
    suspend fun getOrders(token: String): List<Order>

    // Profile
    suspend fun getProfile(token: String): User

    suspend fun updateProfile(
        token: String,
        name: String,
        nif: String,
        birthdate: LocalDate,
        email: String,
        nameCc: String,
        numberCc: String,
        expirationDateCc: String,
        cvvCc: String,
    ): User

    suspend fun deleteProfile(token: String): Boolean

    // Tickets
    suspend fun getTickets(token: String): List<Ticket>

    // Vouchers
    suspend fun getVouchers(token: String): List<Voucher>
}
