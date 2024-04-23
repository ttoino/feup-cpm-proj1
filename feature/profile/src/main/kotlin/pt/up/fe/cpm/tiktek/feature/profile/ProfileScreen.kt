package pt.up.fe.cpm.tiktek.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.core.ui.form.FormFieldState
import pt.up.fe.cpm.tiktek.core.ui.forms.PaymentInformationForm
import pt.up.fe.cpm.tiktek.core.ui.forms.PersonalInformationForm
import pt.up.fe.cpm.tiktek.core.ui.snackbar
import pt.up.fe.cpm.tiktek.feature.profile.navigation.ProfileGraph

@Destination<ProfileGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun ProfileRoute(viewModel: ProfileViewModel = hiltViewModel()) {
    val snackbarHostState = snackbar

    LaunchedEffect(viewModel.successPersonal) {
        viewModel.successPersonal.collect { success ->
            if (success) {
                snackbarHostState.showSnackbar(
                    message = "Informação pessoal mudada com sucesso!",
                )
            }
        }
    }

    LaunchedEffect(viewModel.successCreditCard) {
        viewModel.successCreditCard.collect { success ->
            if (success) {
                snackbarHostState.showSnackbar(
                    message = "Informação bancária mudada com sucesso!",
                )
            }
        }
    }

    ProfileScreen(
        nameState = viewModel.name.state,
        onUpdateName = viewModel.name::update,
        onShowNameError = viewModel.name::showError,
        nifState = viewModel.nif.state,
        onUpdateNif = viewModel.nif::update,
        onShowNifError = viewModel.nif::showError,
        birthdateState = viewModel.birthdate.state,
        onUpdateBirthdate = viewModel.birthdate::update,
        onShowBirthdateError = viewModel.birthdate::showError,
        emailState = viewModel.email.state,
        onUpdateEmail = viewModel.email::update,
        onShowEmailError = viewModel.email::showError,
        nameCcState = viewModel.nameCc.state,
        onUpdateNameCc = viewModel.nameCc::update,
        onShowNameCcError = viewModel.nameCc::showError,
        numberCcState = viewModel.numberCc.state,
        onUpdateNumberCc = viewModel.numberCc::update,
        onShowNumberCcError = viewModel.numberCc::showError,
        expirationDateCcState = viewModel.expirationDateCc.state,
        onUpdateExpirationDateCc = viewModel.expirationDateCc::update,
        onShowExpirationDateCcError = viewModel.expirationDateCc::showError,
        cvcCcState = viewModel.cvcCc.state,
        onUpdateCvcCc = viewModel.cvcCc::update,
        onShowCvcCcError = viewModel.cvcCc::showError,
        onLogout = viewModel::logout,
        onUpdatePersonalInformation = viewModel::updatePersonalInformation,
        onUpdatePaymentInformation = viewModel::updatePaymentInformation,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun ProfileScreen(
    nameState: FormFieldState<String>,
    onUpdateName: (String) -> Unit,
    onShowNameError: () -> Unit,
    nifState: FormFieldState<String>,
    onUpdateNif: (String) -> Unit,
    onShowNifError: () -> Unit,
    birthdateState: FormFieldState<LocalDate?>,
    onUpdateBirthdate: (LocalDate?) -> Unit,
    onShowBirthdateError: () -> Unit,
    emailState: FormFieldState<String>,
    onUpdateEmail: (String) -> Unit,
    onShowEmailError: () -> Unit,
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
    onLogout: () -> Unit,
    onUpdatePersonalInformation: () -> Unit,
    onUpdatePaymentInformation: () -> Unit,
) {
    AppBarLayout(
        title = stringResource(R.string.profile_title),
        actions = {
            TextButton(onClick = onLogout) {
                Text(stringResource(R.string.logout_action))
            }
        },
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        Box {
            AsyncImage(
                model = "https://i.pinimg.com/564x/e3/c9/a9/e3c9a9e5934d65cff25d83a2ac655230.jpg",
                contentDescription = stringResource(R.string.profile_picture),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(128.dp)
                        .clip(CircleShape),
            )
            SmallFloatingActionButton(
                onClick = { },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.align(Alignment.BottomEnd),
            ) {
                Icon(Icons.Filled.Edit, stringResource(R.string.profile_picture_action))
            }
        }

        Text(
            text = stringResource(R.string.personal_information_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
        )
        PersonalInformationForm(
            nameState,
            onUpdateName,
            onShowNameError,
            nifState,
            onUpdateNif,
            onShowNifError,
            birthdateState,
            onUpdateBirthdate,
            onShowBirthdateError,
            emailState,
            onUpdateEmail,
            onShowEmailError,
        )

        Button(
            onClick = onUpdatePersonalInformation,
        ) {
            Icon(
                imageVector = Icons.Outlined.Save,
                contentDescription = stringResource(R.string.personal_information_action),
                modifier = Modifier.size(18.dp),
            )
            Spacer(
                modifier = Modifier.width(8.dp),
            )
            Text(text = stringResource(R.string.personal_information_action))
        }

        Text(
            text = stringResource(R.string.payment_information_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
        )
        PaymentInformationForm(
            nameCcState,
            onUpdateNameCc,
            onShowNameCcError,
            numberCcState,
            onUpdateNumberCc,
            onShowNumberCcError,
            expirationDateCcState,
            onUpdateExpirationDateCc,
            onShowExpirationDateCcError,
            cvcCcState,
            onUpdateCvcCc,
            onShowCvcCcError,
        )
        Button(
            onClick = onUpdatePaymentInformation,
        ) {
            Icon(
                imageVector = Icons.Outlined.AddCard,
                contentDescription = stringResource(R.string.payment_information_action),
                modifier = Modifier.size(18.dp),
            )
            Spacer(
                modifier = Modifier.width(8.dp),
            )
            Text(text = stringResource(R.string.payment_information_action))
        }
    }
}
