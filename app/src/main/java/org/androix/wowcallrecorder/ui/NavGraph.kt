package org.androix.wowcallrecorder.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.androix.wowcallrecorder.feature.home.HomeScreen
import org.androix.wowcallrecorder.feature.setup.SetupScreen

@Composable
fun NavGraph(startDestination: String = "setup") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("setup") {
            SetupScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        // TODO: Add other destinations
    }
}
