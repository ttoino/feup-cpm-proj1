package pt.up.fe.cpm.tiktek.core.ui.form

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import kotlinx.datetime.LocalTime
import pt.up.fe.cpm.tiktek.core.model.FormFieldState
import androidx.compose.material3.TimePicker as MaterialTimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    state: FormFieldState<LocalTime?>,
    onValueChange: (LocalTime?) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    helperText: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val timePickerState =
        rememberTimePickerState(
            state.value?.hour ?: 0,
            state.value?.minute ?: 0,
        )
    var dialogOpen by remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()

    FormField(
        state.map { it?.toString() ?: "" },
        onValueChange = {},
        label = label,
        modifier =
            modifier.focusRequester(focusRequester).onFocusChanged {
                if (it.isFocused && !readOnly && enabled) {
                    dialogOpen = true
                }
            },
        readOnly = true,
        enabled = enabled,
        helperText = helperText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
    )

    if (dialogOpen) {
        DatePickerDialog(
            onDismissRequest = {
                dialogOpen = false
            },
            confirmButton = {
                TextButton(onClick = {
                    dialogOpen = false
                    onValueChange(
                        LocalTime(timePickerState.hour, timePickerState.minute),
                    )
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogOpen = false
                }) {
                    Text("Cancel")
                }
            },
        ) {
            MaterialTimePicker(
                timePickerState,
            )
        }
    }
}
