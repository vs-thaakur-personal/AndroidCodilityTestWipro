package com.example.dashboard.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dashboard.ui.DashboardRoute
import com.example.data.models.Product

const val DASHBOARD_ROUTE = "dashboard"

fun NavController.navigateToDashboardScreen() = navigate(DASHBOARD_ROUTE)

fun NavGraphBuilder.dashboardScreen(
    navigateBack: () -> Unit,
    onProductClicked: (Int) -> Unit,
) {
    composable(
        route = DASHBOARD_ROUTE
    ) {
        DashboardRoute(onProductClicked, navigateBack)
    }
}