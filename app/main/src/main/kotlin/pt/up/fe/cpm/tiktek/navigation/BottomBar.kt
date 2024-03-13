package pt.up.fe.cpm.tiktek.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pt.up.fe.cpm.tiktek.navigation.Screen

@Composable
fun BottomBar(
    currentScreen: Screen?,
    navigateTo: (Screen) -> Unit
) {
    NavigationBar {
        for (screen in Screen.entries) {
            val selected = screen == currentScreen
            NavigationBarItem(
                selected = selected,
                icon = {
                    AnimatedContent(
                        selected,
                        label = "${screen.label} bottom bar icon",
                        transitionSpec = {
                            fadeIn() togetherWith fadeOut()
                        }
                    ) {
                        Icon(if (it) screen.selectedIcon else screen.icon, stringResource(screen.label))
                    }
                },
                label = { Text(stringResource(screen.label)) },
                onClick = { navigateTo(screen) },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
@Preview
private fun BottomBarPreview() {
    var screen by remember {
        mutableStateOf(Screen.EVENTS)
    }

    BottomBar(screen) { screen = it }
}
