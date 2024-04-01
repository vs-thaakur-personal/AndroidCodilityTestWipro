package com.example.data.repositories

import com.example.data.ApiException
import com.example.data.models.Product
import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse
import com.example.data.sources.auth.BaseAuthDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class AuthRepositoryTest {
    private lateinit var testScope: TestScope
    private val authDataSource: BaseAuthDataSource = mockk()
    private val loginResponse = LoginResponse(
        id = 0,
        email = "abc@hjd.com",
        firstName = "abcnksdm",
        gender = "male",
        image = "",
        lastName = "iimdl",
        token = "token",
        userName = "mskaos0ss"
    )
    private val loginRequest = LoginRequest(
        userName = "mskaos0ss",
        password = "kmslaslas"
    )

    @Before
    fun setUp() {
        testScope = TestScope(StandardTestDispatcher())
    }

    @Test
    fun getProductById_forSuccessResult() {
        val response = Result.success(loginResponse)
        coEvery { authDataSource.signIn(any()) } returns response
        val repository = AuthRepository(authDataSource)

        testScope.runTest {
            val result = repository.signIn(loginRequest)

            Assert.assertEquals(response, result)
        }
    }

    @Test
    fun getProductById_forHttpExceptionResult() {
        val response = getHttpException()
        coEvery { authDataSource.signIn(any()) } returns response
        val repository = AuthRepository(authDataSource)

        testScope.runTest {
            val result = repository.signIn(loginRequest)

            Assert.assertTrue(result.exceptionOrNull() is ApiException)
            Assert.assertEquals("Invalid credentials", result.exceptionOrNull()?.message)
            val code = (result.exceptionOrNull() as? ApiException)?.statusCode
            Assert.assertEquals(400, code)
        }
    }

    @Test
    fun getProductById_forOtherExceptionResult() {
        val message = "NullPointer Exception"
        val response = NullPointerException(message)
        coEvery { authDataSource.signIn(any()) } returns Result.failure(response)
        val repository = AuthRepository(authDataSource)

        testScope.runTest {
            val result = repository.signIn(loginRequest)

            Assert.assertTrue(result.exceptionOrNull() is ApiException)
            Assert.assertEquals(message, result.exceptionOrNull()?.message)
            val code = (result.exceptionOrNull() as? ApiException)?.statusCode
            Assert.assertEquals(0, code)
        }
    }

    private fun getHttpException(): Result<LoginResponse> {
        val data = """
            {
                "message": "Invalid credentials"
            }
        """.trimIndent()
        val responseBody = data.toResponseBody()
        return Result.failure(HttpException(Response.error<Product>(400, responseBody)))
    }
}