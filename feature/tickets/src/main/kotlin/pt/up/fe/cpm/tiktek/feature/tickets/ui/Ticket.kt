package pt.up.fe.cpm.tiktek.feature.tickets.ui

import androidx.compose.runtime.Composable
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import pt.up.fe.cpm.tiktek.core.ui.CardRow

@Composable
fun TicketCard(
    ticket: TicketWithEvent,
    onClick: () -> Unit,
) {
    if (ticket.useDate == null) {
        CardRow(
            title = ticket.event.name,
            subtitle = "Seat ${ticket.seat} | Available until ${ticket.event.date}",
            imageUrl = ticket.event.imageUrl,
            onClick = onClick,
        )
    } else {
        CardRow(
            title = ticket.event.name,
            subtitle = "Seat ${ticket.seat} | Used on ${ticket.useDate}",
            imageUrl = ticket.event.imageUrl,
            onClick = onClick,
        )
    }
}
