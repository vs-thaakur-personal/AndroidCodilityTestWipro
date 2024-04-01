package com.example.assessment.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.auth.ui.navigation.SIGN_IN_ROUTE
import com.example.auth.ui.navigation.signInScreen
import com.example.dashboard.ui.navigation.DASHBOARD_ROUTE
import com.example.dashboard.ui.navigation.dashboardScreen
import com.example.dashboard.ui.navigation.navigateToDashboardScreen
import com.example.productdetails.ui.navigation.navigateToProductDetail
import com.example.productdetails.ui.navigation.productDetailScreen

@Composable
fun AppNavHost(isLoggedIn: Boolean) {
    val navController = rememberNavController()
    val startDestination = if (isLoggedIn) DASHBOARD_ROUTE else SIGN_IN_ROUTE
    val activity = LocalContext.current as? ComponentActivity

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier
    ) {

        signInScreen {
            navController.navigateToDashboardScreen()
        }
        dashboardScreen(
            navigateBack = {
                navController.popBackStack()
                activity?.finish()
            },
            onProductClicked = { productId ->
                navController.navigateToProductDetail(productId)
            }
        )
        productDetailScreen(navigateBack = { navController.popBackStack() })

    }

}