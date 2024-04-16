package pt.up.fe.cpm.tiktek.feature.auth.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun AuthLayout(
    @StringRes title: Int,
    @StringRes subtitle: Int,
    @StringRes mainAction: Int,
    onMainAction: () -> Unit,
    mainActionDisabled: Boolean = false,
    @StringRes secondaryAction: Int,
    onSecondaryAction: () -> Unit,
    @StringRes errorMessage: Int? = null,
    content: @Composable () -> Unit,
) {
    Scaffold {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .padding(24.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column {
                    Text(
                        stringResource(title),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        stringResource(subtitle),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                content()
            }

            Column(
                modifier = Modifier.padding(top = 16.dp),
            ) {
                if (errorMessage != null) {
                    Text(
                        stringResource(errorMessage),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Button(
                    onClick = onMainAction,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !mainActionDisabled,
                ) {
                    Text(stringResource(mainAction))
                }

                TextButton(
                    onClick = onSecondaryAction,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(secondaryAction))
                }
            }
        }
    }
}
