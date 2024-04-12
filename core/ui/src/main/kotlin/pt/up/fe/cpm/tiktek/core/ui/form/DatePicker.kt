package pt.up.fe.cpm.tiktek.core.ui.form

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import androidx.compose.material3.DatePicker as MaterialDatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    value: LocalDate?,
    onValueChange: (LocalDate?) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val datePickerState =
        rememberDatePickerState(
            value?.atStartOfDayIn(TimeZone.UTC)?.toEpochMilliseconds(),
        )
    var dialogOpen by remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()

    TextField(
        value?.toString() ?: "",
        onValueChange = {},
        modifier =
            modifier.focusRequester(focusRequester).onFocusChanged {
                if (it.isFocused) {
                    dialogOpen = true
                    focusRequester.freeFocus()
                }
            },
        readOnly = true,
        label = label,
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
