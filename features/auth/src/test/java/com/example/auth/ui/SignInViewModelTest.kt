package com.example.auth.ui

import android.content.SharedPreferences
import com.example.data.ApiException
import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse
import com.example.data.repositories.AuthRepository
import com.example.data.sources.auth.BaseAuthDataSource
import com.example.domain.usecases.LoginUseCase
import com.example.domain.validators.LoginRequestValidator
import com.example.domain.validators.PasswordValidator
import com.example.domain.validators.UserNameValidator
import com.example.utils.FileUtils
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response.error


class SignInViewModelTest {

    private val loginUseCase = mockk<LoginUseCase>()
    private val sharedPreferences = mockk<SharedPreferences>(relaxed = true)

    private lateinit var testScope: TestScope

    @Before
    fun setUp() {
        testScope = TestScope(StandardTestDispatcher())
    }

    @Test
    fun uiStateSignIn_whenInitialized_thenShowLoading() = runTest {
        val viewModel = SignInViewModel(StandardTestDispatcher(), loginUseCase, sharedPreferences)
        Assert.assertFalse(viewModel.loginStateFlow.value.isLoading)
        Assert.assertFalse(viewModel.loginStateFlow.value.isLoggedIn)
        Assert.assertNull(viewModel.loginStateFlow.value.loginResponse)
        Assert.assertNull(viewModel.loginStateFlow.value.error)
    }

    @Test
    fun signInSuccessTestFakeRepo() {
        val expectedToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwidXNlcm5hbWUiOiJoYmluZ2xleTEiLCJlbWFpbCI6ImhiaW5nbGV5MUBwbGFsYS5vci5qcCIsImZpcnN0TmFtZSI6IlNoZWxkb24iLCJsYXN0TmFtZSI6IlF1aWdsZXkiLCJnZW5kZXIiOiJtYWxlIiwiaW1hZ2UiOiJodHRwczovL3JvYm9oYXNoLm9yZy9TaGVsZG9uLnBuZz9zZXQ9c2V0NCIsImlhdCI6MTcxMTg4OTg4NSwiZXhwIjoxNzExODkzNDg1fQ.nIX7i_XVrEDg-lvM5MRi9mcOoOa7SS_21IHNXzoJnr4"
        val repo = AuthRepository(FakeDataSourceForSuccessLogin())
        val useCase =
            LoginUseCase(LoginRequestValidator(UserNameValidator(), PasswordValidator()), repo)
        val request = LoginRequest("test@example.com", "password")
        val viewModel = SignInViewModel(Dispatchers.Unconfined, useCase, sharedPreferences)
        testScope.runTest {
            viewModel.signIn(request)
            Assert.assertFalse(viewModel.loginStateFlow.value.isLoading)
            Assert.assertTrue(viewModel.loginStateFlow.value.isLoggedIn)
            Assert.assertEquals(
                "hbingley1",
                viewModel.loginStateFlow.value.loginResponse?.userName
            )
            Assert.assertEquals(
                expectedToken,
                viewModel.loginStateFlow.value.loginResponse?.token
            )
            Assert.assertEquals(2, viewModel.loginStateFlow.value.loginResponse?.id)
            Assert.assertEquals("male", viewModel.loginStateFlow.value.loginResponse?.gender)
            Assert.assertNull(viewModel.loginStateFlow.value.error)
            verify { sharedPreferences.edit().putString(KEY_TOKEN, expectedToken).apply() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun signInFailureTestFakeRepo() {
        val repo = AuthRepository(FakeDataSourceForFailureLogin())
        val useCase =
            LoginUseCase(LoginRequestValidator(UserNameValidator(), PasswordValidator()), repo)
        val request = LoginRequest("test@example.com", "password")
        val viewModel = SignInViewModel(Dispatchers.Unconfined, useCase, sharedPreferences)
        testScope.runTest {
            viewModel.signIn(request)
            advanceUntilIdle()
            Assert.assertFalse(viewModel.loginStateFlow.value.isLoading)
            Assert.assertFalse(viewModel.loginStateFlow.value.isLoggedIn)
            Assert.assertNotNull(viewModel.loginStateFlow.value.error)
            Assert.assertTrue(viewModel.loginStateFlow.value.error is ApiException)
            Assert.assertEquals(
                viewModel.loginStateFlow.value.error?.message,
                "Invalid credentials"
            )
            Assert.assertNull(viewModel.loginStateFlow.value.loginResponse)
        }
    }
}

class FakeDataSourceForSuccessLogin : BaseAuthDataSource {
    override suspend fun signIn(request: LoginRequest): Result<LoginResponse> {
        val data = FileUtils.readFile("success_login_json.json")
        val json = Json { prettyPrint = true }
        return Result.success(json.decodeFromString(LoginResponse.serializer(), data ?: ""))

    }
}

class FakeDataSourceForFailureLogin : BaseAuthDataSource {
    override suspend fun signIn(request: LoginRequest): Result<LoginResponse> {
        val data = FileUtils.readFile("error_invalid_cred.json") ?: ""
        val responseBody = data.toResponseBody()
        return Result.failure(HttpException(error<LoginResponse>(400, responseBody)))
    }
}