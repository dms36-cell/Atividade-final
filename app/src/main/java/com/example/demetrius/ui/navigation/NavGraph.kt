package com.example.demetrius.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demetrius.ui.screens.DetailsScreen
import com.example.demetrius.ui.screens.PostListScreen
import com.example.demetrius.ui.screens.SettingsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "post_list") {
        composable("post_list") {
            PostListScreen(navController = navController)
        }
        composable(
            route = "details/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: 0
            DetailsScreen(navController = navController, postId = postId)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
    }
}
