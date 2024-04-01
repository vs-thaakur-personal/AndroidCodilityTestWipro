package com.example.domain.validators

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class EmailValidatorTest {
    private lateinit var emailValidator: UserNameValidator
    private val validEmail = listOf(
        "vs.thaakur@gmail.com",
        "v9991@yahoo.in",
        "xyz@abc.io"
    )

    private val invalidEmail = listOf(
        "",
        null,
        "kkaljlklsa",
        "ayz@abc",
        "abc.wzh"
    )

    @Before
    fun setUp() {

        emailValidator = UserNameValidator()
    }

    @Test
    fun checkForValidEmail() {
        Assert.assertTrue(emailValidator.isValid(validEmail[0]).isSuccess)
        Assert.assertTrue(emailValidator.isValid(validEmail[1]).isSuccess)
        Assert.assertTrue(emailValidator.isValid(validEmail[2]).isSuccess)
    }

//    @Test
//    fun checkForInValidEmail() {
//        val response0 = emailValidator.isValid(invalidEmail[0])
//        val response1 = emailValidator.isValid(invalidEmail[1])
//        val response2 = emailValidator.isValid(invalidEmail[2])
//        val response3 = emailValidator.isValid(invalidEmail[3])
//        val response4 = emailValidator.isValid(invalidEmail[4])
//
//        Assert.assertTrue(response0.isFailure)
//        Assert.assertTrue(response1.isFailure)
//        Assert.assertTrue(response2.isFailure)
//        Assert.assertTrue(response3.isFailure)
//        Assert.assertTrue(response4.isFailure)
//
//        Assert.assertEquals(
//            ValidationErrorCode.EMAIL_NULL_OR_EMPTY,
//            (response0.exceptionOrNull() as? ValidationException)?.code
//        )
//        Assert.assertEquals(
//            ValidationErrorCode.EMAIL_NULL_OR_EMPTY,
//            (response1.exceptionOrNull() as? ValidationException)?.code
//        )
//        Assert.assertEquals(
//            ValidationErrorCode.EMAIL_NOT_VALID,
//            (response2.exceptionOrNull() as? ValidationException)?.code
//        )
//        Assert.assertEquals(
//            ValidationErrorCode.EMAIL_NOT_VALID,
//            (response3.exceptionOrNull() as? ValidationException)?.code
//        )
//        Assert.assertEquals(
//            ValidationErrorCode.EMAIL_NOT_VALID,
//            (response4.exceptionOrNull() as? ValidationException)?.code
//        )
//    }
}