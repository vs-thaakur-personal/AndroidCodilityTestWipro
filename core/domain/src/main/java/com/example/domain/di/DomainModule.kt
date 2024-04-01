package com.example.domain.di

import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.DashboardRepository
import com.example.data.repositories.ProductDetailRepository
import com.example.domain.usecases.BaseSuspendedUseCase
import com.example.domain.usecases.GetCategoryUseCases
import com.example.domain.usecases.GetProductByIdUseCase
import com.example.domain.usecases.GetProductsUseCase
import com.example.domain.usecases.LoginUseCase
import com.example.domain.usecases.SearchProductUseCase
import com.example.domain.validators.BaseValidator
import com.example.domain.validators.UserNameValidator
import com.example.domain.validators.LoginRequestValidator
import com.example.domain.validators.PasswordValidator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModuleBinder {

    @Binds
    fun bindEmailValidator(validator: UserNameValidator): BaseValidator<String?>

    @Binds
    fun bindPasswordValidator(validator: PasswordValidator): BaseValidator<String?>

    @Binds
    fun bindLoginRequestValidator(validator: LoginRequestValidator): BaseValidator<LoginRequest>

}

@Module
@InstallIn(SingletonComponent::class)
object DomainModuleProvider {
    @Provides
    fun provideBaseSuspendedUseCase(
        validator: LoginRequestValidator,
        repository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(validator, repository)
    }

    @Provides
    fun provideSearchProductUseCase(repository: DashboardRepository): SearchProductUseCase{
        return SearchProductUseCase(repository)
    }

    @Provides
    fun provideGetProductUseCase(repository: DashboardRepository): GetProductsUseCase{
        return GetProductsUseCase(repository)
    }

    @Provides
    fun provideGetCategoryUseCase(repository: DashboardRepository): GetCategoryUseCases{
        return GetCategoryUseCases(repository)
    }

    @Provides
    fun provideGetProductById(repository: ProductDetailRepository): GetProductByIdUseCase{
        return GetProductByIdUseCase(repository)
    }

}