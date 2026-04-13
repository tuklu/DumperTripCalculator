package dev.tuklu.dumpertripcalculator.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tuklu.dumpertripcalculator.DumperCalculator
import dev.tuklu.dumpertripcalculator.ui.screen.BayScreen
import dev.tuklu.dumpertripcalculator.ui.screen.ProfileScreen

private sealed class Destination(val route: String, val label: String, val icon: ImageVector) {
    object Home : Destination("home", "Home", Icons.Default.Home)
    object Bay : Destination("bay", "Bay", Icons.Default.DirectionsCar)
    object Profile : Destination("profile", "Profile", Icons.Default.Person)
}

private val destinations = listOf(Destination.Home, Destination.Bay, Destination.Profile)

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destination.Home.route) { DumperCalculator() }
            composable(Destination.Bay.route) { BayScreen() }
            composable(Destination.Profile.route) { ProfileScreen() }
        }
    }
}
