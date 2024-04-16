package pt.up.fe.cpm.tiktek.core.ui.form

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import androidx.compose.material3.DatePicker as MaterialDatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    state: FormFieldState<LocalDate?>,
    onValueChange: (LocalDate?) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    helperText: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onLoseFocus: () -> Unit = {},
) {
    val datePickerState =
        rememberDatePickerState(
            state.value?.atStartOfDayIn(TimeZone.UTC)?.toEpochMilliseconds(),
        )
    var dialogOpen by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    FormField(
        state.map { it?.toString() ?: "" },
        onValueChange = {},
        label = label,
        modifier =
            modifier.onFocusChanged {
                if (it.isFocused && !readOnly && enabled) {
                    dialogOpen = true
                }
            },
        readOnly = true,
        enabled = enabled,
        helperText = helperText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onLoseFocus = onLoseFocus,
    )

    if (dialogOpen) {
        DatePickerDialog(
            onDismissRequest = {
                dialogOpen = false
                focusManager.clearFocus()
            },
            confirmButton = {
                TextButton(onClick = {
                    dialogOpen = false
                    focusManager.moveFocus(FocusDirection.Next)
                    onValueChange(
                        datePickerState.selectedDateMillis?.let {
                            Instant.fromEpochMilliseconds(it).toLocalDateTime(
                                TimeZone.UTC,
                            ).date
                        },
                    )
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogOpen = false
                    focusManager.clearFocus()
                }) {
                    Text("Cancel")
                }
            },
        ) {
            MaterialDatePicker(
                datePickerState,
            )
        }
    }
}
