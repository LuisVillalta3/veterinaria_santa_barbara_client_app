@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.notifications.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary

@Composable
fun NotificationsView(navController: NavHostController) {
    Scaffold(
        containerColor = Color.White,
        topBar = { CustomAppBar(navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)
            ) {
                items (16) {
                    NotificationCard()
                    SpacerUi(height = 20.dp)
                }
            }
        }
    }
}

@Composable
private fun CustomAppBar(navController: NavHostController) {
    TopAppBar(
        colors = TopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            actionIconContentColor = Color.Black,
            scrolledContainerColor = Color.White,
        ),
        title = {
            Text(
                text = "Notificaciones",
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                    modifier = Modifier.size(50.dp),
                    tint = BluePrimary
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete all notifications",
                    tint = Color.Red
                )
            }
        }
    )
}
