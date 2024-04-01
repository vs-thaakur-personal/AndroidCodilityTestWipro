package com.example.productdetails.ui

import com.example.data.models.Product
import com.example.data.repositories.ProductDetailRepository
import com.example.data.sources.productdetail.BaseProductDetailDataSource
import com.example.domain.usecases.GetProductByIdUseCase
import com.example.utils.FileUtils
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response.error


class ProductDetailViewModelTest {


    private lateinit var testScope: TestScope

    @Before
    fun setUp() {
        testScope = TestScope(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_initialState() {
        val repo = ProductDetailRepository(FakeDataSourceForSuccessProductDetail())
        val useCase = GetProductByIdUseCase(repo)
        val viewModel = ProductDetailViewModel(StandardTestDispatcher(), useCase)
        Assert.assertFalse(viewModel.productDetailStateFlow.value.isLoading)
        Assert.assertNull(viewModel.productDetailStateFlow.value.product)
        Assert.assertNull(viewModel.productDetailStateFlow.value.errorMessage)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductDetails_successWithFakeRepo() {
        val repo = ProductDetailRepository(FakeDataSourceForSuccessProductDetail())
        val useCase = GetProductByIdUseCase(repo)
        val viewModel = ProductDetailViewModel(Dispatchers.Unconfined, useCase)
        val description = "HP Pavilion 15-DK1056WM Gaming Laptop 10th Gen Core i5, 8GB, 256GB SSD, GTX 1650 4GB, Windows 10"
        testScope.runTest {
            viewModel.getProductDetails(10)


            Assert.assertFalse(viewModel.productDetailStateFlow.value.isLoading)
            Assert.assertNull(viewModel.productDetailStateFlow.value.errorMessage)
            Assert.assertNotNull(viewModel.productDetailStateFlow.value.product)
            Assert.assertEquals("HP Pavilion 15-DK1056WM",viewModel.productDetailStateFlow.value.product?.title)
            Assert.assertEquals(description,viewModel.productDetailStateFlow.value.product?.description)
            Assert.assertEquals(1099,viewModel.productDetailStateFlow.value.product?.price?.toInt())
            Assert.assertEquals(4.43,viewModel.productDetailStateFlow.value.product?.rating)
            Assert.assertEquals(89,viewModel.productDetailStateFlow.value.product?.stock)
        }
    }

    @Test
    fun getProductDetails_errorWithFakeRepo() {
        val repo = ProductDetailRepository(FakeDataSourceForFailureProductDetail())
        val useCase = GetProductByIdUseCase(repo)
        val viewModel = ProductDetailViewModel(Dispatchers.Unconfined, useCase)
        testScope.runTest {
            viewModel.getProductDetails(10)

            Assert.assertFalse(viewModel.productDetailStateFlow.value.isLoading)
            Assert.assertNotNull(viewModel.productDetailStateFlow.value.errorMessage)
            Assert.assertNull(viewModel.productDetailStateFlow.value.product)
            Assert.assertEquals("Product with id '10' not found",viewModel.productDetailStateFlow.value.errorMessage)
        }
    }
}

class FakeDataSourceForSuccessProductDetail : BaseProductDetailDataSource {
    override suspend fun getProductById(id: Int): Result<Product?> {
        val data = FileUtils.readFile("product_detail_success.json")
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            isLenient = true
        }
        return Result.success(json.decodeFromString(Product.serializer(), data ?: ""))
    }
}

class FakeDataSourceForFailureProductDetail : BaseProductDetailDataSource {
    override suspend fun getProductById(id: Int): Result<Product?> {
        val data = FileUtils.readFile("product_detail_error.json") ?: ""
        val responseBody = data.toResponseBody()
        return Result.failure(HttpException(error<Product>(400, responseBody)))
    }
}