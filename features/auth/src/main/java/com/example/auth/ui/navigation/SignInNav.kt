package com.example.auth.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.auth.ui.SignInRoute

const val SIGN_IN_ROUTE = "sign_in"

fun NavController.navigateToSignIn(navOptions: NavOptions) = navigate(SIGN_IN_ROUTE, navOptions)

fun NavGraphBuilder.signInScreen(onSignInButtonClicked: () -> Unit) {
    composable(
        route = SIGN_IN_ROUTE
    ) {
        SignInRoute(onSignInButtonClicked)
    }
}