package pt.up.fe.cpm.tiktek.core.ui.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pt.up.fe.cpm.tiktek.core.model.FormFieldState
import pt.up.fe.cpm.tiktek.core.ui.R
import pt.up.fe.cpm.tiktek.core.ui.form.FormField
import pt.up.fe.cpm.tiktek.core.ui.form.SeparatorVisualTransformation

@Composable
fun PaymentInformationForm(
    nameCcState: FormFieldState<String>,
    onUpdateNameCc: (String) -> Unit,
    onShowNameCcError: () -> Unit,
    numberCcState: FormFieldState<String>,
    onUpdateNumberCc: (String) -> Unit,
    onShowNumberCcError: () -> Unit,
    expirationDateCcState: FormFieldState<String>,
    onUpdateExpirationDateCc: (String) -> Unit,
    onShowExpirationDateCcError: () -> Unit,
    cvcCcState: FormFieldState<String>,
    onUpdateCvcCc: (String) -> Unit,
    onShowCvcCcError: () -> Unit,
) {
    FormField(
        state = nameCcState,
        onValueChange = onUpdateNameCc,
        onLoseFocus = onShowNameCcError,
        label = stringResource(R.string.name_cc),
        leadingIcon = {
            Icon(Icons.Default.Person, contentDescription = stringResource(R.string.name_cc))
        },
        keyboardOptions =
            KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
            ),
        modifier = Modifier.fillMaxWidth(),
    )

    FormField(
        state = numberCcState,
        onValueChange = onUpdateNumberCc,
        onLoseFocus = onShowNumberCcError,
        label = stringResource(R.string.number_cc),
        leadingIcon = {
            Icon(Icons.Default.CreditCard, contentDescription = stringResource(R.string.number_cc))
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        visualTransformation = SeparatorVisualTransformation(4, maxSize = 16),
        modifier = Modifier.fillMaxWidth(),
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        FormField(
            state = expirationDateCcState,
            onValueChange = onUpdateExpirationDateCc,
            onLoseFocus = onShowExpirationDateCcError,
            label = stringResource(R.string.expiry_cc),
            leadingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.expiry_cc))
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
            visualTransformation = SeparatorVisualTransformation(2, '/', 4),
            modifier = Modifier.weight(1f),
        )

        FormField(
            state = cvcCcState,
            onValueChange = onUpdateCvcCc,
            onLoseFocus = onShowCvcCcError,
            label = stringResource(R.string.cvc_cc),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = stringResource(R.string.cvc_cc))
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
            modifier = Modifier.weight(1f),
        )
    }
}
