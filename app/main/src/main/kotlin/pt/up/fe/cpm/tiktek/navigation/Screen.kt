package pt.up.fe.cpm.tiktek.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.EmojiFoodBeverage
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.generated.cafeteria.navgraphs.CafeteriaNavGraph
import com.ramcosta.composedestinations.generated.events.navgraphs.EventsNavGraph
import com.ramcosta.composedestinations.generated.profile.navgraphs.ProfileNavGraph
import com.ramcosta.composedestinations.generated.tickets.navgraphs.TicketsNavGraph
import com.ramcosta.composedestinations.spec.DirectionNavGraphSpec
import pt.up.fe.cpm.tiktek.R

enum class Screen(
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val label: Int,
    val graph: DirectionNavGraphSpec,
) {
    EVENTS(
        Icons.Outlined.CalendarMonth,
        Icons.Filled.CalendarMonth,
        R.string.events,
        EventsNavGraph,
    ),
    TICKETS(
        Icons.Outlined.ConfirmationNumber,
        Icons.Filled.ConfirmationNumber,
        R.string.tickets,
        TicketsNavGraph,
    ),
    CAFETERIA(
        Icons.Outlined.EmojiFoodBeverage,
        Icons.Filled.EmojiFoodBeverage,
        R.string.cafeteria,
        CafeteriaNavGraph,
    ),
    PROFILE(
        Icons.Outlined.Person,
        Icons.Filled.Person,
        R.string.profile,
        ProfileNavGraph,
    ),
}
