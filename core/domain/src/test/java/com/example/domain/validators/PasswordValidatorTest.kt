package com.example.domain.validators

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class PasswordValidatorTest{
    private lateinit var passwordValidator: PasswordValidator
    private val validPassword = listOf(
        "ABcf1729&",
        "agsj^7_9iispA",
        "783AHnssaj&"
    )

    private val invalidEmail = listOf(
        "",
        null,
        "kkalj",
        "ayz@abchkaiAIKAJLL992787299992992999929nnmanmdhk ahka shdjhhsajdhjh",
        "ahhajjak16617&",// No capital char
        "Akkkakk718992", // No special char
        "AAkkU&/&s@sas", // No Digital char
        "AHJJKJKS&&167"  // No small char
    )

    @Before
    fun setUp() {

        passwordValidator = PasswordValidator()
    }

    @Test
    fun checkForValidPassword() {
        Assert.assertTrue(passwordValidator.isValid(validPassword[0]).isSuccess)
        Assert.assertTrue(passwordValidator.isValid(validPassword[1]).isSuccess)
        Assert.assertTrue(passwordValidator.isValid(validPassword[2]).isSuccess)
    }

    @Test
    fun checkForInValidPasswordEmpty() {
        val response = passwordValidator.isValid(invalidEmail[0])
        Assert.assertTrue(response.isFailure)
        Assert.assertEquals(
            ValidationErrorCode.PASSWORD_NULL_OR_EMPTY,
            (response.exceptionOrNull() as? ValidationException)?.code
        )

    }

    @Test
    fun checkForInValidPasswordNull() {
        val response = passwordValidator.isValid(invalidEmail[1])
        Assert.assertTrue(response.isFailure)
        Assert.assertEquals(
            ValidationErrorCode.PASSWORD_NULL_OR_EMPTY,
            (response.exceptionOrNull() as? ValidationException)?.code
        )

    }

//    @Test
//    fun checkForInValidPasswordLengthLow() {
//        val response = passwordValidator.isValid(invalidEmail[2])
//        Assert.assertTrue(response.isFailure)
//        Assert.assertEquals(
//            ValidationErrorCode.PASSWORD_LENGTH_LOW,
//            (response.exceptionOrNull() as? ValidationException)?.code
//        )
//    }

//    @Test
//    fun checkForInValidPasswordLengthOverflow() {
//        val response = passwordValidator.isValid(invalidEmail[3])
//        Assert.assertTrue(response.isFailure)
//        Assert.assertEquals(
//            ValidationErrorCode.PASSWORD_LENGTH_OVERFLOW,
//            (response.exceptionOrNull() as? ValidationException)?.code
//        )
//    }
//
//    @Test
//    fun checkForInValidPasswordWeak() {
//        val response = passwordValidator.isValid(invalidEmail[4])
//        Assert.assertTrue(response.isFailure)
//        Assert.assertEquals(
//            ValidationErrorCode.PASSWORD_WEAK,
//            (response.exceptionOrNull() as? ValidationException)?.code
//        )
//
//        val response1 = passwordValidator.isValid(invalidEmail[5])
//        Assert.assertTrue(response1.isFailure)
//        Assert.assertEquals(
//            ValidationErrorCode.PASSWORD_WEAK,
//            (response1.exceptionOrNull() as? ValidationException)?.code
//        )
//
//        val response2 = passwordValidator.isValid(invalidEmail[6])
//        Assert.assertTrue(response2.isFailure)
//        Assert.assertEquals(
//            ValidationErrorCode.PASSWORD_WEAK,
//            (response2.exceptionOrNull() as? ValidationException)?.code
//        )
//
//        val response3 = passwordValidator.isValid(invalidEmail[6])
//        Assert.assertTrue(response3.isFailure)
//        Assert.assertEquals(
//            ValidationErrorCode.PASSWORD_WEAK,
//            (response3.exceptionOrNull() as? ValidationException)?.code
//        )
//    }
}