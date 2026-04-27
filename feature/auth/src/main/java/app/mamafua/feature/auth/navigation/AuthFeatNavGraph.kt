package app.mamafua.feature.auth.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.mamafua.feature.auth.screen.SignInScreen
import app.mamafua.feature.auth.screen.SignUpScreen

const val SIGN_UP_HOME = "sign_up_home"
const val SIGN_IN_HOME = "sign_in_home"

private fun NavController.signUpHome() = navigate(SIGN_UP_HOME) { launchSingleTop = true }
private fun NavController.signInHome() = navigate(SIGN_IN_HOME) { launchSingleTop = true }

fun NavGraphBuilder.authFeatNavGraph(
    navController: NavController
) {
    composable(
        route = SIGN_UP_HOME
    ) {
        BackHandler(enabled = true) {}
        SignUpScreen()
    }

    composable(
        route = SIGN_IN_HOME
    ) {
        BackHandler(enabled = true) {}
        SignInScreen()
    }
}