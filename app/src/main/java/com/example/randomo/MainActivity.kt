package com.example.randomo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.homescreen.homeScreen.HomeScreen
import com.example.loginandregistration.presentation.policeItemListScreen.PoliceListScreen
import com.example.loginandregistration.presentation.policeDetailScreen.PoliceDetailScreen
import com.example.randomo.presentation.theme.RandomoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Route.PoliceListItemScreen) {
                        composable<Route.PoliceListItemScreen> {
                            PoliceListScreen(
                                onItemClicked = { policeItem ->
                                    navController.navigate(Route.PoliceDetailScreen(policeItem.id, policeItem.name))
                                }
                            )
                        }
                        composable<Route.PoliceDetailScreen> {
                            val args = it.toRoute<Route.PoliceDetailScreen>()
                            PoliceDetailScreen(args.id, args.name)
                        }
                        composable<Route.HomeScreen> {
                            HomeScreen()
                        }
                    }
                }

            }
        }
    }
}