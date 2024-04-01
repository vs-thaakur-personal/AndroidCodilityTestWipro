package com.example.data.repositories

import com.example.data.ApiException
import com.example.data.sources.dashboard.BaseDashboardDataSource
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

class DashboardRepositoryTest {
    private lateinit var testScope: TestScope
    private val dashboardDataSource: BaseDashboardDataSource = mockk()
    private val categoryResponse =
        listOf("category1", "category1", "category1", "category1", "category1")

    @Before
    fun setUp() {
        testScope = TestScope(StandardTestDispatcher())
    }
    @Test
    fun getDashboardData_forSuccessResponse() {
        testScope.runTest {
            coEvery { dashboardDataSource.getAllCategories() } returns Result.success(
                categoryResponse
            )
            val repository = DashboardRepository(dashboardDataSource)
            val response = repository.getAllCategories()

            Assert.assertEquals(categoryResponse,response.getOrNull())
        }
    }

    @Test
    fun getDashboardData_forApiErrorResponse() {

        testScope.runTest {
            val errorMessage = "No Categories found"
            val exceptionResult = getHttpException<List<String>?>(errorMessage)
            coEvery { dashboardDataSource.getAllCategories() } returns exceptionResult
            val repository = DashboardRepository(dashboardDataSource)
            val result = repository.getAllCategories()

            Assert.assertTrue(result.exceptionOrNull() is ApiException)
            Assert.assertEquals(errorMessage, result.exceptionOrNull()?.message)
            val code = (result.exceptionOrNull() as? ApiException)?.statusCode
            Assert.assertEquals(400, code)
        }
    }

    private fun <T>getHttpException(message: String): Result<T> {
        val data = """
            {
                "message": "$message"
            }
        """.trimIndent()
        val responseBody = data.toResponseBody()
        return Result.failure(HttpException(Response.error<T>(400, responseBody)))
    }
}