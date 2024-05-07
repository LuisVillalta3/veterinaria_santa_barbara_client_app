@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GreenPrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.IconNavBarColor

@Composable
fun AppLayout(navController: NavHostController, content: @Composable () -> Unit) {
    val isBlueVersion = navController.currentDestination?.route == Router.homeRoute

    Scaffold(
        bottomBar = { BottomBar(navController) },
        containerColor = Color.White,
        topBar = { CustomAppBar(isBlueVersion = isBlueVersion, navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }

    when (navController.currentDestination?.route) {
        Router.homeRoute -> selectedItem = 0
        Router.citasRoute -> selectedItem = 1
        "medication" -> selectedItem = 2
        "person" -> selectedItem = 3
        "pet" -> selectedItem = 5
    }

    NavigationBar(
        tonalElevation = 8.dp,
        containerColor = Color.White,
    ) {
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = {
                if (selectedItem != 0) {
                    navController.navigate(Router.homeRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                NavBarItemIcon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    isSelected = selectedItem == 0
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.White
            )
        )
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = {
                if (selectedItem != 1) {
                    navController.navigate(Router.citasRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                NavBarItemIcon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Calendar",
                    isSelected = selectedItem == 1
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.White
            )
        )
        Box(
            modifier = Modifier
                .size(65.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    if (selectedItem != 5) {
                        navController.navigate(Router.citasRoute) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                containerColor = GreenPrimary,
                shape = RoundedCornerShape(percent = 100)
            ) {
                Icon(
                    imageVector = Icons.Filled.Pets,
                    contentDescription = "Pet",
                    modifier = Modifier.size(40.dp),
                    tint = if (selectedItem == 5) BluePrimary else Color.White
                )
            }
        }
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = {
                if (selectedItem != 2) {
                    navController.navigate(Router.citasRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                NavBarItemIcon(
                    imageVector = Icons.Filled.Medication,
                    contentDescription = "Medication",
                    isSelected = selectedItem == 2
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.White
            )
        )
        NavigationBarItem(
            selected = selectedItem == 3,
            onClick = {
                if (selectedItem != 3) {
                    navController.navigate(Router.citasRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                NavBarItemIcon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Person",
                    isSelected = selectedItem == 3
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.White
            )
        )
    }
}

@Composable
private fun NavBarItemIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    isSelected: Boolean = false
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = if (isSelected) BluePrimary else IconNavBarColor,
        modifier = Modifier.size(35.dp)
    )
}

@Composable
private fun CustomAppBar(isBlueVersion: Boolean = false, navController: NavHostController) {
    TopAppBar(
        colors = TopAppBarColors(
            containerColor = if (isBlueVersion) BluePrimary else Color.White,
            titleContentColor = if (isBlueVersion) Color.White else Color.Black,
            navigationIconContentColor = if (isBlueVersion) Color.White else Color.Black,
            actionIconContentColor = if (isBlueVersion) Color.White else Color.Black,
            scrolledContainerColor = if (isBlueVersion) BluePrimary else Color.White,
        ),
        title = {
            Icon(
                imageVector = Icons.Filled.Pets,
                contentDescription = "Veterinaria Santa Barbara",
                tint = if (isBlueVersion) Color.White else BluePrimary,
                modifier = Modifier.size(40.dp)
            )
        },
        actions = {
            NotificationButtonWithNotifications(true, isBlueVersion, navController)
        }
    )
}

@Composable
private fun NotificationButtonWithNotifications(
    hasNotifications: Boolean = true,
    isBlueVersion: Boolean = false,
    navController: NavHostController
) {
    if (!hasNotifications) {
        return NotificationButton(isBlueVersion = isBlueVersion, navController = navController)
    }

    BadgedBox(
        badge = {
            Badge(
                containerColor = Color.Red,
                contentColor = Color.White,
                modifier = Modifier
                    .size(10.dp)
                    .offset((-20).dp, 5.dp),
            )
        },
    ) {
        NotificationButton(isBlueVersion = isBlueVersion, navController = navController)
    }
}

@Composable
private fun NotificationButton(navController: NavHostController, isBlueVersion: Boolean = false) {
    IconButton(
        onClick = { navController.navigate(Router.notificationsRoute) },
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.NotificationsNone,
            contentDescription = "Notifications",
            tint = if (isBlueVersion) Color.White else BluePrimary,
            modifier = Modifier.size(35.dp)
        )
    }
}
