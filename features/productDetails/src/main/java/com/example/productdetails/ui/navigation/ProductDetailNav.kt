package com.example.productdetails.ui.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.productdetails.ui.ProductDetailRoute

//const val PRODUCT_ID = "productId"
const val PRODUCT_DETAIL_NAV = "product-details?productId={productId}"

fun NavController.navigateToProductDetail(productId: Int, navOptions: NavOptions? = null) {

    navigate(PRODUCT_DETAIL_NAV.replace("{productId}", productId.toString()), navOptions)
}

fun NavGraphBuilder.productDetailScreen(
    navigateBack: () -> Unit,
) {
    composable(route = PRODUCT_DETAIL_NAV
    ) {
        val productId = it.arguments?.getString("productId")?.toInt()
        if (productId != null) {
            ProductDetailRoute(productId,navigateBack)
        }
    }
}