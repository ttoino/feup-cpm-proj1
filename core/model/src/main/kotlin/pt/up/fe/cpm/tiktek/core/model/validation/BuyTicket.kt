package pt.up.fe.cpm.tiktek.core.model.validation

import dev.nesk.akkurate.constraints.builders.isGreaterThanOrEqualTo
import dev.nesk.akkurate.constraints.builders.isLowerThanOrEqualTo
import pt.up.fe.cpm.tiktek.core.model.BuyTicketRequest
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.ticketAmount

val buyTicketRequestValidator =
    Validator<BuyTicketRequest> {
        ticketAmount {
            isGreaterThanOrEqualTo(1)
            isLowerThanOrEqualTo(4)
        }
    }
