package pt.up.fe.cpm.tiktek.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.ui.form.FormFieldState
import pt.up.fe.cpm.tiktek.core.ui.forms.PaymentInformationForm
import pt.up.fe.cpm.tiktek.core.ui.forms.PersonalInformationForm
import pt.up.fe.cpm.tiktek.core.ui.forms.UpdatePasswordForm
import pt.up.fe.cpm.tiktek.feature.profile.navigation.ProfileGraph

@Destination<ProfileGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun ProfileRoute(viewModel: ProfileViewModel = hiltViewModel()) {
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
        oldPasswordState = viewModel.oldPassword.state,
        onUpdateOldPassword = viewModel.oldPassword::update,
        onShowOldPasswordError = viewModel.oldPassword::showError,
        newPasswordState = viewModel.newPassword.state,
        onUpdateNewPassword = viewModel.newPassword::update,
        onShowNewPasswordError = viewModel.oldPassword::showError,
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
        onUpdatePasswordInformation = viewModel::updatePassword,
        onUpdatePaymentInformation = viewModel::updatePaymentInformation,
        viewModel,
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
    oldPasswordState: FormFieldState<String>,
    onUpdateOldPassword: (String) -> Unit,
    onShowOldPasswordError: () -> Unit,
    newPasswordState: FormFieldState<String>,
    onUpdateNewPassword: (String) -> Unit,
    onShowNewPasswordError: () -> Unit,
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
    onUpdatePersonalInformation: suspend () -> Unit,
    onUpdatePasswordInformation: suspend () -> Unit,
    onUpdatePaymentInformation: suspend () -> Unit,
    viewModel: ProfileViewModel,
) {
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(viewModel.successPersonal) {
        viewModel.successPersonal.collect { success ->
            if (success) {
                snackbarHostState.showSnackbar(
                    message = "Informação pessoal mudada com sucesso!",
                )
            }
        }
    }

    LaunchedEffect(viewModel.successPassword) {
        viewModel.successPassword.collect { success ->
            if (success) {
                snackbarHostState.showSnackbar(
                    message = "Palavra-passe mudada com sucesso!",
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

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehaviour,
                title = { Text(stringResource(R.string.profile_title)) },
                actions = {
                    Row {
                        TextButton(onClick = onLogout) {
                            Text(stringResource(R.string.logout_action))
                        }
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(16.dp),
        ) {
            Box {
                // profile picture

                AsyncImage(
                    model = "https://i.pinimg.com/736x/b1/cb/57/b1cb57bcb04183c6aaf293210a6ba8a8.jpg",
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
                onClick =
                    {
                        keyboardController?.hide()
                        scope.launch {
                            onUpdatePersonalInformation()
                        }
                    },
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

            // ---------------------------------------------- PASSWORD CHANGE ------------------------------------------
            Text(
                text = stringResource(R.string.password_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
            )
            UpdatePasswordForm(
                oldPasswordState,
                onUpdateOldPassword,
                onShowOldPasswordError,
                newPasswordState,
                onUpdateNewPassword,
                onShowNewPasswordError,
            )
            Button(
                onClick = {
                    keyboardController?.hide()
                    scope.launch {
                        onUpdatePasswordInformation()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Key,
                    contentDescription = stringResource(R.string.password_action),
                    modifier = Modifier.size(18.dp),
                )
                Spacer(
                    modifier = Modifier.width(8.dp),
                )
                Text(text = stringResource(R.string.password_action))
            }

            // ---------------------------------------------- CREDIT CARD CHANGE ------------------------------------------
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
                onClick = {
                    keyboardController?.hide()
                    scope.launch {
                        onUpdatePaymentInformation()
                    }
                },
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
}
