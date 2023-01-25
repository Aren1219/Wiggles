package dev.spikeysanju.wiggles.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.spikeysanju.wiggles.ui.theme.MyTheme

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
private fun PreviewAdoptPage() {
    MyTheme() {
        Adopt(navController = rememberAnimatedNavController())
    }
}

@Composable
fun Adopt(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Adoption")
                },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.surface,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
                modifier = Modifier.background(MaterialTheme.colors.onSurface)
            )
        }
    ) {
        Signup()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun Signup() {

    val focusManager = LocalFocusManager.current

    var firstName by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 6.dp),
        contentPadding = PaddingValues(horizontal = 6.dp),
    ) {
        item {
            AdoptTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = "first name",
                focusManager = focusManager
            )

        }
        item {
            AdoptTextField(
                value = surname,
                onValueChange = { surname = it },
                label = "surname",
                focusManager = focusManager
            )
        }
        item {
            AdoptTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "phone number",
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
        }
        item {
            AdoptTextField(
                value = address,
                onValueChange = { address = it },
                label = "address",
                focusManager = focusManager
            )
        }
        item {
            AdoptTextField(
                value = email,
                onValueChange = { email = it },
                label = "email",
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
        }
        item {
            AdoptTextField(
                value = password,
                onValueChange = { password = it },
                label = "password",
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        item {}
        item {
            Button(onClick = { /*TODO*/ },
                Modifier
                    .fillMaxSize()
                    .padding(12.dp)) {
                Text(
                    text = "Submit",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun AdoptTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    focusManager: FocusManager,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = {
            if (!focusManager.moveFocus(FocusDirection.Right)) {
                focusManager.moveFocus(FocusDirection.Left)
                focusManager.moveFocus(FocusDirection.Down)
            }
        },
        onDone = { focusManager.clearFocus() }
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.surface,
            cursorColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = MaterialTheme.colors.surface,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colors.surface
        ),
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier.padding(horizontal = 4.dp),
        visualTransformation = visualTransformation
    )
}