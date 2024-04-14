package pt.up.fe.cpm.tiktek.core.ui.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.model.FormFieldState
import pt.up.fe.cpm.tiktek.core.ui.R
import pt.up.fe.cpm.tiktek.core.ui.form.DatePicker
import pt.up.fe.cpm.tiktek.core.ui.form.FormField

@Composable
fun PersonalInformationForm(
    nameState: FormFieldState<String>,
    onUpdateName: (String) -> Unit,
    nifState: FormFieldState<String>,
    onUpdateNif: (String) -> Unit,
    birthdateState: FormFieldState<LocalDate?>,
    onUpdateBirthdate: (LocalDate?) -> Unit,
    emailState: FormFieldState<String>,
    onUpdateEmail: (String) -> Unit,
    lastImeAction: ImeAction = ImeAction.Done,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        FormField(
            state = nameState,
            onValueChange = onUpdateName,
            label = stringResource(R.string.name),
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = stringResource(R.string.name))
            },
            keyboardOptions =
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next,
                ),
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            FormField(
                state = nifState,
                onValueChange = onUpdateNif,
                label = stringResource(R.string.nif),
                leadingIcon = {
                    Icon(Icons.Default.Pin, contentDescription = stringResource(R.string.nif))
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                modifier = Modifier.weight(1f),
            )

            DatePicker(
                state = birthdateState,
                onValueChange = onUpdateBirthdate,
                label = stringResource(R.string.birthdate),
                leadingIcon = {
                    Icon(Icons.Default.Today, contentDescription = stringResource(R.string.birthdate))
                },
                modifier = Modifier.weight(1f),
            )
        }

        EmailField(
            emailState,
            onUpdateEmail,
            imeAction = lastImeAction,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
