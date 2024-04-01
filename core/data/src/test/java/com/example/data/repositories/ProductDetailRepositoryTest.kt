package com.example.data.repositories

import com.example.data.ApiException
import com.example.data.models.Product
import com.example.data.sources.productdetail.BaseProductDetailDataSource
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


class ProductDetailRepositoryTest {

    private lateinit var testScope: TestScope
    private val productDetailDataSource: BaseProductDetailDataSource = mockk()
    private val product = Product(
        id = 0,
        title = "title",
        category = "",
        description = "",
        images = listOf(),
        price = 1009.0F,
        rating = 19.9,
        stock = 10,
        discountPercentage = 19F
    )

    @Before
    fun setUp() {
        testScope = TestScope(StandardTestDispatcher())
    }
    @Test
    fun getProductById_forSuccessResult() {
        val response = Result.success(product)
        coEvery { productDetailDataSource.getProductById(any()) } returns response
        val repository = ProductDetailRepository(productDetailDataSource)

        testScope.runTest {
            val result = repository.getProductDetail(10)

            Assert.assertEquals(response, result)
        }
    }

    @Test
    fun getProductById_forHttpExceptionResult() {
        val response = getHttpException()
        coEvery { productDetailDataSource.getProductById(any()) } returns response
        val repository = ProductDetailRepository(productDetailDataSource)

        testScope.runTest {
            val result = repository.getProductDetail(10)

            Assert.assertTrue(result.exceptionOrNull() is ApiException)
            Assert.assertEquals("Product with id '10' not found", result.exceptionOrNull()?.message)
            val code = (result.exceptionOrNull() as? ApiException)?.statusCode
            Assert.assertEquals(400, code)
        }
    }

    @Test
    fun getProductById_forOtherExceptionResult() {
        val message = "NullPointer Exception"
        val response = NullPointerException(message)
        coEvery { productDetailDataSource.getProductById(any()) } returns Result.failure(response)
        val repository = ProductDetailRepository(productDetailDataSource)

        testScope.runTest {
            val result = repository.getProductDetail(10)

            Assert.assertTrue(result.exceptionOrNull() is ApiException)
            Assert.assertEquals(message, result.exceptionOrNull()?.message)
            val code = (result.exceptionOrNull() as? ApiException)?.statusCode
            Assert.assertEquals(0, code)
        }
    }

    private fun getHttpException(): Result<Product> {
        val data = """
            {
              "message": "Product with id '10' not found"
            }
        """.trimIndent()
        val responseBody = data.toResponseBody()
        return Result.failure(HttpException(Response.error<Product>(400, responseBody)))
    }
}