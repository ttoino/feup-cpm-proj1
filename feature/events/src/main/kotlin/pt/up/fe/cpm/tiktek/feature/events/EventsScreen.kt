package pt.up.fe.cpm.tiktek.feature.events

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.feature.events.navigation.EventsGraph

@Destination<EventsGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun EventsRoute(navigator: DestinationsNavigator) {
    // TODO: Get data
    EventsScreen()
    /*Button(
        onClick = { navigator.navigate(EventDestination("")) },
        modifier =
            Modifier
                .offset(y = 300.dp),
    ) {
        Text("Event")
    }*/
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun EventsScreen() {
    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = "Eventos") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) {
        Column (
            //verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp)
        ) {
            Text(
                text = "Recomendados",
                style = TextStyle(fontSize = 20.sp),
            )
            Row(
                modifier =
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 16.dp)
            ) {
                RecommendedEvents(
                    eventImageLink = "https://i.pinimg.com/originals/ee/78/c6/ee78c67c41f6439bb9ce406907c91f3d.jpg",
                    eventName = "O Pato Lindo",
                    eventDate = "20 de Fevereiro",
                    eventTime = "12:30"
                )
                RecommendedEvents(
                    eventImageLink = "https://parade.com/.image/t_share/MjAzMzU3NzQxMzU4NTIzOTgz/happy-birthday-wishes-messages.jpg",
                    eventName = "Aniversário",
                    eventDate = "2 de Abril",
                    eventTime = "20:30"
                )
                RecommendedEvents(
                    eventImageLink = "https://i.pinimg.com/564x/b8/85/4c/b8854cfb077f5e7f6646899455f27704.jpg",
                    eventName = "Fada Julia - Um momento bom",
                    eventDate = "10 de Abril",
                    eventTime = "14:45"
                )
                RecommendedEvents(
                    eventImageLink = "https://s.calendarr.com/upload/datas/di/ai/dia-internacional-da-mulher_c.jpg?auto_optimize=low&width=640",
                    eventName = "Dia das mulheres",
                    eventDate = "8 de Março",
                    eventTime = "14:45"
                )
            }
            Text(
                text = "Hoje",
                style = TextStyle(fontSize = 20.sp),
            )
        }
    }
}

@Composable
private fun FilterEvents() {

}


@Composable
private fun RecommendedEvents (
    eventImageLink: String,
    eventName: String,
    eventDate: String,
    eventTime: String
)  {
    Column(
        modifier =
        Modifier
            .size(width = 150.dp, height = 220.dp)
            .padding(10.dp)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                model = eventImageLink,
                contentDescription = "Event Image",
                contentScale = ContentScale.Crop, // Maintain aspect ratio and crop if necessary
                modifier = Modifier.fillMaxSize() // Fill the available space within the Box
            )
        }
        Text(
            text = eventName,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "$eventDate | $eventTime",
            fontSize = 15.sp
        )
    }
}


@Composable
private fun TodayEvents() {

}