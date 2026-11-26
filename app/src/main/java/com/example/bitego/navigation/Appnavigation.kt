package com.example.bitego.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bitego.screens.cartscreen.CartScreen
import com.example.bitego.screens.favoritescreen.FavoriteScreen
import com.example.bitego.screens.homescreen.HomeScreen
import com.example.bitego.screens.profilescreen.ProfileScreen
import com.example.bitego.screens.ui_components.MyBottomNavBar
import com.example.bitego.screens.welcomescreen.Welcomescreen
import com.example.bitego.viewmodel.FoodViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val foodViewModel: FoodViewModel = viewModel()

    val backStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        bottomBar = {
            if (
                currentRoute in listOf(
                    "home",
                    "favorite",
                    "cart",
                    "profile"
                )
            ) {
                MyBottomNavBar(navController)
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            composable("welcome") {
                Welcomescreen(navController)
            }

            composable("home") {
                HomeScreen(foodViewModel)
            }

            composable("favorite") {
                FavoriteScreen(foodViewModel)
            }

            composable("cart") {
                CartScreen(foodViewModel)
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}