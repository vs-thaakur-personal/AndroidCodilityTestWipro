package com.example.domain.validators

import com.example.data.models.request.LoginRequest
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class LoginRequestValidatorTest {
    lateinit var loginRequestValidator: LoginRequestValidator

    @MockK
    lateinit var emailValidator: UserNameValidator

    @MockK
    lateinit var passwordValidator: PasswordValidator

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loginRequestValidator = LoginRequestValidator(emailValidator, passwordValidator)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun checkValidationSuccess() {
        every { emailValidator.isValid(any()) } returns Result.success(true)
        every { passwordValidator.isValid(any()) } returns Result.success(true)

        val result = loginRequestValidator.isValid(LoginRequest("v@gmail.com", "2179hjshdjs"))

        Assert.assertTrue(result.isSuccess)
    }

    @Test
    fun checkValidationEmailFailurePasswordSuccess() {
        val exception = ValidationException(ValidationErrorCode.EMAIL_NULL_OR_EMPTY)
        every { emailValidator.isValid(any()) } returns Result.failure(exception)
        every { passwordValidator.isValid(any()) } returns Result.success(true)

        val result = loginRequestValidator.isValid(LoginRequest("v@gmail.com", "2179hjshdjs"))

        Assert.assertTrue(result.isFailure)
        Assert.assertEquals(exception, result.exceptionOrNull())
        Assert.assertEquals(exception.code, (result.exceptionOrNull() as? ValidationException)?.code)
    }

    @Test
    fun checkValidationEmailSuccessPasswordFailure() {
        val exception = ValidationException(ValidationErrorCode.PASSWORD_LENGTH_LOW)
        every { emailValidator.isValid(any()) } returns Result.success(true)
        every { passwordValidator.isValid(any()) } returns Result.failure(exception)

        val result = loginRequestValidator.isValid(LoginRequest("v@gmail.com", "2179hjshdjs"))

        Assert.assertTrue(result.isFailure)
        Assert.assertEquals(exception, result.exceptionOrNull())
        Assert.assertEquals(exception.code, (result.exceptionOrNull() as? ValidationException)?.code)
    }
}