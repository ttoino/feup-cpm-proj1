package pt.up.fe.cpm.tiktek.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.ExternalNavGraph
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthNavGraph
import com.ramcosta.composedestinations.generated.cafeteria.navgraphs.CafeteriaNavGraph
import com.ramcosta.composedestinations.generated.events.navgraphs.EventsNavGraph
import com.ramcosta.composedestinations.generated.profile.navgraphs.ProfileNavGraph
import com.ramcosta.composedestinations.generated.tickets.navgraphs.TicketsNavGraph

@NavHostGraph(route = "tiktek")
annotation class TikTekGraph {
    @ExternalNavGraph<AuthNavGraph>(start = true)
    companion object Includes
}

@NavGraph<TikTekGraph>
internal annotation class MainGraph {
    @ExternalNavGraph<EventsNavGraph>(start = true)
    @ExternalNavGraph<TicketsNavGraph>
    @ExternalNavGraph<CafeteriaNavGraph>
    @ExternalNavGraph<ProfileNavGraph>
    companion object Includes
}

// Dummy destination so it compiles :))
@Destination<MainGraph>
@Composable
fun DUMMY() {}
