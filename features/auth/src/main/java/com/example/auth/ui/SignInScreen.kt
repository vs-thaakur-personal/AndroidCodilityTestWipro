package com.example.auth.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.R
import com.example.data.ApiException
import com.example.data.models.request.LoginRequest
import com.example.domain.validators.ValidationErrorCode
import com.example.domain.validators.ValidationException
import com.example.resources.composables.ErrorText
import com.example.theme.AssessmentTheme

@Composable
fun SignInRoute(
    navigateToHomeScreen: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val loginState by viewModel.loginStateFlow.collectAsState()
    if (loginState.isLoggedIn) {
        navigateToHomeScreen()
    } else {
        SignInScreen(
            uiState = loginState,
            onSignInButtonClicked = {
                Log.e("SignInRoute", it.toString())
                viewModel.signIn(it)
            },
            onEmailTextChanged = {},
            onPasswordTextChanged = {}
        )
    }
}

@Composable
fun SignInScreen(
    uiState: SignInUiState = SignInUiState(),
    onSignInButtonClicked: (LoginRequest) -> Unit,
    onPasswordTextChanged: () -> Unit,
    onEmailTextChanged: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            val keyboardController = LocalSoftwareKeyboardController.current
            var email by remember { mutableStateOf("hbingley1") }
            var password by remember { mutableStateOf("CQutx25i8r") }
            val focusRequester = remember { FocusRequester() }

            var errorMessage by remember { mutableStateOf("") }

            errorMessage = when (uiState.error) {
                is ValidationException -> {
                    getValidationErrorMessage(uiState.error)
                }

                is ApiException -> uiState.error.errorMessage
                else -> stringResource(com.example.resources.R.string.something_went_wrong)
            }

            Image(
                painter = painterResource(id = com.example.resources.R.drawable.ecom_icon),
                modifier = Modifier.size(200.dp),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = stringResource(R.string.prompt_email)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                isError = uiState.error != null,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequester.requestFocus()
                    },
                ),
                enabled = uiState.isLoading.not()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = stringResource(R.string.prompt_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle done action
                    },
                ),
                isError = uiState.error != null,
                enabled = uiState.isLoading.not()
            )

            Spacer(modifier = Modifier.height(15.dp))

            uiState.error?.let {
                ErrorText(text = errorMessage)
            }

            Button(
                onClick = {
                    if (uiState.isLoading.not())
                        onSignInButtonClicked(LoginRequest(email, password))
                },
                modifier = Modifier.fillMaxWidth(),

                ) {
                Text(text = stringResource(R.string.action_sign_in))
            }

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}

@Composable
private fun getValidationErrorMessage(error: ValidationException) =
    when (error.code) {
        ValidationErrorCode.EMAIL_NULL_OR_EMPTY -> stringResource(com.example.resources.R.string.email_can_not_be_null_or_empty)
        ValidationErrorCode.EMAIL_NOT_VALID -> stringResource(com.example.resources.R.string.email_is_not_valid)
        ValidationErrorCode.PASSWORD_NULL_OR_EMPTY -> stringResource(com.example.resources.R.string.password_can_not_be_null_or_empty)
        ValidationErrorCode.PASSWORD_LENGTH_LOW -> stringResource(com.example.resources.R.string.password_should_be_at_least_of_6_chars)
        ValidationErrorCode.PASSWORD_LENGTH_OVERFLOW -> stringResource(com.example.resources.R.string.password_can_not_be_more_that_30_characters)
        ValidationErrorCode.PASSWORD_WEAK -> stringResource(com.example.resources.R.string.please_create_password_with_upper_case_lower_case_digit_and_special_characters)
        else -> stringResource(com.example.resources.R.string.something_went_wrong)
    }

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    AssessmentTheme(false) {
        SignInScreen(SignInUiState(), {}, {}, {})
    }
}