package com.example.auth.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse
import com.example.domain.usecases.BaseSuspendedUseCase
import com.example.domain.usecases.LoginUseCase
import com.example.utils.di.DispatcherOptions
import com.example.utils.di.DispatcherQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val KEY_TOKEN = "KEY_TOKEN"
@HiltViewModel
class SignInViewModel @Inject constructor(
    @DispatcherQualifier(DispatcherOptions.IO) private val signInDispatcher: CoroutineDispatcher,
    private val signInUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _loginFlow = MutableStateFlow(SignInUiState())
    val loginStateFlow: StateFlow<SignInUiState> = _loginFlow

    fun signIn(request: LoginRequest) {
        viewModelScope.launch(signInDispatcher) {
            _loginFlow.update {
                it.copy(isLoading = true, isLoggedIn = false, error = null)
            }
            signInUseCase.execute(request)
                .onFailure { error ->
                    _loginFlow.update {
                        it.copy(isLoading = false, isLoggedIn = false, error = error)
                    }
                }
                .onSuccess { response ->
                    sharedPreferences.edit().putString(KEY_TOKEN,response.token).apply()
                    _loginFlow.update {
                        it.copy(isLoading = false, isLoggedIn = true, loginResponse = response)
                    }
                }
        }
    }
}

data class SignInUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val loginResponse: LoginResponse? = null,
    val error: Throwable? = null
)