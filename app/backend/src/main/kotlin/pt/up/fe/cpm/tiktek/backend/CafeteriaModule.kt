package pt.up.fe.cpm.tiktek.backend

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.model.OrderItem
import pt.up.fe.cpm.tiktek.core.model.OrderItemWithModels
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.SendCartRequest
import pt.up.fe.cpm.tiktek.core.model.Voucher
import pt.up.fe.cpm.tiktek.core.model.VoucherWithModels

fun Application.cafeteriaModule() {
    routing {
        authenticate {
            get("/cafeteria") {
                val cafeteria = application.database.cafeteriaItem.getAll()

                call.respond(cafeteria)
            }

            get("/cafeteria/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)

                val cafeteria = application.database.cafeteriaItem.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(cafeteria)
            }
        }

        post("/cart") {
            val body = call.receive<SendCartRequest>()

            if (!application.database.user.existsById(body.userId)) return@post call.respond(HttpStatusCode.NotFound)

            val order =
                application.database.order.create(
                    Order(
                        id = uuid(),
                        userId = body.userId,
                        items =
                            body.cart.items.map {
                                OrderItem(
                                    itemId = it.key,
                                    quantity = it.value,
                                )
                            },
                    ),
                )

            val vouchers =
                body.cart.vouchers.map {
                    val voucher = application.database.voucher.getById(it) ?: return@post call.respond(HttpStatusCode.NotFound)
                    application.database.voucher.update(voucher.copy(orderId = order.id))
                }

            call.respond(
                OrderWithModels(
                    id = order.id,
                    userId = order.userId,
                    items =
                        order.items.map {
                            OrderItemWithModels(
                                item =
                                    application.database.cafeteriaItem.getById(
                                        it.itemId,
                                    ) ?: return@post call.respond(HttpStatusCode.NotFound),
                                quantity = it.quantity,
                            )
                        },
                    vouchers =
                        vouchers.map {
                            when (it) {
                                is Voucher.Free ->
                                    VoucherWithModels.Free(
                                        id = it.id,
                                        item =
                                            application.database.cafeteriaItem.getById(
                                                it.itemId,
                                            ) ?: return@post call.respond(HttpStatusCode.NotFound),
                                        orderId = it.orderId,
                                        userId = it.userId,
                                    )
                                is Voucher.Discount ->
                                    VoucherWithModels.Discount(
                                        id = it.id,
                                        discount = it.discount,
                                        orderId = it.orderId,
                                        userId = it.userId,
                                    )
                            }
                        },
                ),
            )
        }
    }
}
