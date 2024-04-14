package pt.up.fe.cpm.tiktek.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.model.FormFieldState
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
        nifState = viewModel.nif.state,
        onUpdateNif = viewModel.nif::update,
        birthdateState = viewModel.birthdate.state,
        onUpdateBirthdate = viewModel.birthdate::update,
        emailState = viewModel.email.state,
        onUpdateEmail = viewModel.email::update,
        oldPasswordState = viewModel.oldPassword.state,
        onUpdateOldPassword = viewModel.oldPassword::update,
        newPasswordState = viewModel.newPassword.state,
        onUpdateNewPassword = viewModel.newPassword::update,
        nameCcState = viewModel.nameCc.state,
        onUpdateNameCc = viewModel.nameCc::update,
        numberCcState = viewModel.numberCc.state,
        onUpdateNumberCc = viewModel.numberCc::update,
        expirationDateCcState = viewModel.expirationDateCc.state,
        onUpdateExpirationDateCc = viewModel.expirationDateCc::update,
        cvcCcState = viewModel.cvcCc.state,
        onUpdateCvcCc = viewModel.cvcCc::update,
        onLogout = viewModel::logout,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun ProfileScreen(
    nameState: FormFieldState<String>,
    onUpdateName: (String) -> Unit,
    nifState: FormFieldState<String>,
    onUpdateNif: (String) -> Unit,
    birthdateState: FormFieldState<LocalDate?>,
    onUpdateBirthdate: (LocalDate?) -> Unit,
    emailState: FormFieldState<String>,
    onUpdateEmail: (String) -> Unit,
    oldPasswordState: FormFieldState<String>,
    onUpdateOldPassword: (String) -> Unit,
    newPasswordState: FormFieldState<String>,
    onUpdateNewPassword: (String) -> Unit,
    nameCcState: FormFieldState<String>,
    onUpdateNameCc: (String) -> Unit,
    numberCcState: FormFieldState<String>,
    onUpdateNumberCc: (String) -> Unit,
    expirationDateCcState: FormFieldState<String>,
    onUpdateExpirationDateCc: (String) -> Unit,
    cvcCcState: FormFieldState<String>,
    onUpdateCvcCc: (String) -> Unit,
    onLogout: () -> Unit,
) {
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehaviour,
                title = { Text("Perfil") },
                actions = {
                    Row {
                        TextButton(onClick = onLogout) {
                            Text("Logout")
                        }
                    }
                },
            )
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
                    contentDescription = "avatar",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .size(128.dp)
                            .clip(MaterialTheme.shapes.extraLarge),
                )
                SmallFloatingActionButton(
                    onClick = { },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.align(Alignment.BottomEnd),
                ) {
                    Icon(Icons.Filled.Edit, "Edit profile picture")
                }
            }

            Text(
                text = "Editar Informação Pessoal",
                style = MaterialTheme.typography.headlineSmall,
            )
            PersonalInformationForm(
                nameState,
                onUpdateName,
                nifState,
                onUpdateNif,
                birthdateState,
                onUpdateBirthdate,
                emailState,
                onUpdateEmail,
            )
            Button(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = "Guardar mudanças",
                    modifier = Modifier.size(18.dp),
                )
                Spacer(
                    modifier = Modifier.width(8.dp),
                )
                Text(text = "Guardar mudanças")
            }

            // ----------------------------------------------
            Text(
                text = "Editar palavra-passe",
                style = MaterialTheme.typography.headlineSmall,
            )
            UpdatePasswordForm(
                oldPasswordState,
                onUpdateOldPassword,
                newPasswordState,
                onUpdateNewPassword,
            )
            Button(
                onClick = { },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Key,
                    contentDescription = "Mudar palavra-passe",
                    modifier = Modifier.size(18.dp),
                )
                Spacer(
                    modifier = Modifier.width(8.dp),
                )
                Text(text = "Mudar palavra-passe")
            }

            // ----------------------------------------------
            Text(
                text = "Editar cartão de crédito",
                style = MaterialTheme.typography.headlineSmall,
            )
            PaymentInformationForm(
                nameCcState,
                onUpdateNameCc,
                numberCcState,
                onUpdateNumberCc,
                expirationDateCcState,
                onUpdateExpirationDateCc,
                cvcCcState,
                onUpdateCvcCc,
            )
            Button(
                onClick = { },
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddCard,
                    contentDescription = "Editar cartão de crédito",
                    modifier = Modifier.size(18.dp),
                )
                Spacer(
                    modifier = Modifier.width(8.dp),
                )
                Text(text = "Editar cartão de crédito")
            }
        }
    }
}
