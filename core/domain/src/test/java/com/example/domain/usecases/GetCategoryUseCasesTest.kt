package com.example.domain.usecases

import com.example.data.ApiException
import com.example.data.repositories.DashboardRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class GetCategoryUseCasesTest {

    private val repository: DashboardRepository = mockk()
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
    @Test
    fun test_success() = runTest{
        val categoryUseCases = GetCategoryUseCases(repository)
        val list = listOf("A","B","C","D")
        coEvery { repository.getAllCategories() }.returns(Result.success(list))

        val res = categoryUseCases.execute(Unit)
        Assert.assertEquals(list,res.getOrNull())
    }
    @Test
    fun test_failure() = runTest{
        val categoryUseCases = GetCategoryUseCases(repository)
        val exception = ApiException(100, "test message")
        coEvery { repository.getAllCategories() }.returns(Result.failure(exception))

        val res = categoryUseCases.execute(Unit)
        Assert.assertEquals(exception,res.exceptionOrNull())
    }

}