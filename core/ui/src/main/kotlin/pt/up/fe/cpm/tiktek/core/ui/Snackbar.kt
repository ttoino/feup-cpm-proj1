package pt.up.fe.cpm.tiktek.core.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun SnackbarProvider(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalSnackbar providesDefault SnackbarHostState(), content)
}

internal val LocalSnackbar = staticCompositionLocalOf { SnackbarHostState() }

val snackbar @Composable get() = LocalSnackbar.current
