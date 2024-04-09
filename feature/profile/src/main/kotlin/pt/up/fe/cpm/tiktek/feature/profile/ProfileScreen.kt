package pt.up.fe.cpm.tiktek.feature.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import pt.up.fe.cpm.tiktek.feature.profile.navigation.ProfileGraph

@Destination<ProfileGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        onLogout = viewModel::logout,
    )
}

@Composable
fun DrawCircle() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = Color.Red,
            radius = size.minDimension / 4,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun ProfileScreen(
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
                        Spacer(modifier = Modifier.width(16.dp))
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
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(16.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                // profile picture
                Image(
                    painter = rememberAsyncImagePainter("https://i.pinimg.com/736x/b1/cb/57/b1cb57bcb04183c6aaf293210a6ba8a8.jpg"),
                    contentDescription = "avatar",
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
                    contentColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(start = 100.dp),
                ) {
                    Icon(Icons.Filled.Edit, "Add profile picture.")
                }
            }

            Text(
                text = "Editar Informação Pessoal",
                style = TextStyle(fontSize = 20.sp),
            )
            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Nome")
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Nome")
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                modifier = Modifier.fillMaxWidth(),
            )
            Row {
                TextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text("NIF")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Pin, contentDescription = "NIF")
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                    modifier =
                        Modifier.weight(1f)
                            .padding(end = 8.dp),
                )

                TextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text("Data de Nascimento")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Today, contentDescription = "Data de Nascimento")
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                    modifier =
                        Modifier.weight(1.5f)
                            .padding(start = 8.dp),
                )
            }
            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Email")
                },
                leadingIcon = {
                    Icon(Icons.Default.Mail, contentDescription = "Email")
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                    ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier =
                        Modifier.align(
                            Alignment.Center,
                        ),
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
            }

            // ----------------------------------------------
            Text(
                text = "Editar palavra-passe",
                style = TextStyle(fontSize = 20.sp),
            )

            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Nova palavra-passe")
                },
                leadingIcon = {
                    Icon(Icons.Default.Key, contentDescription = "Nova palavra-passe")
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )

            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Confirme a nova palavra-passe")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Key,
                        contentDescription = "Confirme a nova palavra-passe",
                    )
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
            ) {
                Button(
                    onClick = { },
                    modifier =
                        Modifier.align(
                            Alignment.Center,
                        ),
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
            }
            // ----------------------------------------------
            Text(
                text = "Editar cartão de crédito",
                style = TextStyle(fontSize = 20.sp),
            )

            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Nome do proprietário")
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Nome do proprietário")
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )

            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Número do cartão")
                },
                leadingIcon = {
                    Icon(Icons.Default.CreditCard, contentDescription = "Número do cartão")
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )
            Row {
                TextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text("Validade")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Today, contentDescription = "Validade")
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier =
                        Modifier.weight(1.3f)
                            .padding(end = 8.dp),
                )
                TextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text("CVC")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "CVC")
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier =
                        Modifier.weight(1f)
                            .padding(start = 8.dp),
                )
            }

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
            ) {
                Button(
                    onClick = { },
                    modifier =
                        Modifier.align(
                            Alignment.Center,
                        ),
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
}
