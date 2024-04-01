package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.DashboardRepository
import com.example.data.repositories.ProductDetailRepository
import com.example.data.sources.auth.AuthRemoteDataSource
import com.example.data.sources.auth.BaseAuthDataSource
import com.example.data.sources.dashboard.BaseDashboardDataSource
import com.example.data.sources.dashboard.DashboardRemoteDataSource
import com.example.data.sources.productdetail.BaseProductDetailDataSource
import com.example.data.sources.productdetail.ProductDetailRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModuleBinder {
    @Binds
    fun bindToAuthRemoteDataSource(dataSource: AuthRemoteDataSource): BaseAuthDataSource

    @Binds
    fun bindToDashboardRemoteDataSource(dataSource: DashboardRemoteDataSource): BaseDashboardDataSource

    @Binds
    fun bindToProductDetailRemoteDataSource(dataSource: ProductDetailRemoteDataSource): BaseProductDetailDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DataModuleProvider {
    @Provides
    fun provideAuthRepository(authDataSource: BaseAuthDataSource) = AuthRepository(authDataSource)

    @Provides
    fun provideDashboardRepository(dashboardDataSource: BaseDashboardDataSource) =
        DashboardRepository(dashboardDataSource)


    fun provideProductDetailRepository(productDetailDataSource: BaseProductDetailDataSource) =
        ProductDetailRepository(productDetailDataSource)


    @Provides
    fun provideFirebaseUser() = FirebaseAuth.getInstance()

    @Provides
    fun provideLocalStorage(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("SESSION_PREF"  , Context.MODE_PRIVATE)
    }

}