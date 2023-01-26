package dev.spikeysanju.wiggles.view

import android.util.Patterns
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
import java.util.regex.Pattern

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

    var firstNameErrMsg by remember { mutableStateOf("") }
    var surnameErrMsg by remember { mutableStateOf("") }
    var phoneErrMsg by remember { mutableStateOf("") }
    var addressErrMsg by remember { mutableStateOf("") }
    var emailErrMsg by remember { mutableStateOf("") }
    var passwordErrMsg by remember { mutableStateOf("") }

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
                focusManager = focusManager,
                errorMessage = firstNameErrMsg
            )
        }
        item {
            AdoptTextField(
                value = surname,
                onValueChange = { surname = it },
                label = "surname",
                focusManager = focusManager,
                errorMessage = surnameErrMsg
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
                ),
                errorMessage = phoneErrMsg
            )
        }
        item {
            AdoptTextField(
                value = address,
                onValueChange = { address = it },
                label = "address",
                focusManager = focusManager,
                errorMessage = addressErrMsg
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
                ),
                errorMessage = emailErrMsg
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
                visualTransformation = PasswordVisualTransformation(),
                errorMessage = passwordErrMsg
            )
        }
        item {}
        item {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    firstNameErrMsg = validateName(firstName)
                    surnameErrMsg = validateName(surname)
                    phoneErrMsg = validatePhone(phoneNumber)
                    emailErrMsg = validateEmail(email)
                    passwordErrMsg = validatePassword(password)
                    addressErrMsg = validateAddress(address)
                },
                Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Submit",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun validateName(name: String): String {
    return if (name.isEmpty()) "name can't be blank"
    else if (!Pattern.compile("\\p{Alpha}+").matcher(name).matches()) "invalid name"
    else ""
}

fun validatePhone(number: String): String {
    return if (number.isEmpty()) "phone number can't be blank"
    else if (!Patterns.PHONE.matcher(number).matches()) "invalid phone number"
    else ""
}

fun validateAddress(address: String): String =
    if (address.isEmpty()) "address can't be blank" else ""

fun validateEmail(email: String): String {
    return if (email.isEmpty()) "email can't be blank"
    else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "invalid email"
    else ""
}

fun validatePassword(password: String): String {
    return if (password.length < 6) "at least 6 characters"
    else if (!password.contains(Regex("[0-9]"))) "must include number"
    else if (!password.contains(Regex("[A-Z]"))) "must include uppercase"
    else if (!password.contains(Regex("[a-z]"))) "must include lowercase"
    else ""
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
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessage: String = ""
) {
    Column(modifier = modifier.padding(horizontal = 4.dp)) {
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
            visualTransformation = visualTransformation,
            isError = errorMessage.isNotEmpty()
        )
        Text(text = errorMessage, color = MaterialTheme.colors.error)
    }
}