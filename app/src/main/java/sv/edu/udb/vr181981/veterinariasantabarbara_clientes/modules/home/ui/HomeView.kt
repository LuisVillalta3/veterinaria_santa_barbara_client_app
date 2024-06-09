package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.layouts.AppLayout
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.PetCitaCard
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.UserEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.home.domain.HomeViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GrayColor
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun HomeView(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    val user by homeViewModel.userEntity.collectAsState()
    val citas by homeViewModel.citas.collectAsState()

    AppLayout(
        navController = navController
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                HomeContainer(user = user, navController = navController)
                Box(modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp)) {
                    Text(text = "Mis citas", fontSize = 16.sp)
                }
            }
            citas?.let { citasList ->
                items(citasList.size) {
                    val cita = citasList[it]
                    Box(modifier = Modifier.padding(horizontal = 25.dp)) {
                        PetCitaCard(cita, onClick = {
                            navController.navigate("cita/ver/${cita.id}")
                        })
                    }
                    SpacerUi(height = 20.dp)
                }
            }

            if (citas?.isEmpty() == true) {
                item {
                    NoCitas()
                }
            }
        }
    }
}

@Composable
private fun HomeContainer(user: UserEntity?, navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(BluePrimary)
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        Text(
            text = "Hola",
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            color = Color.White,
        )
        user?.let {
            val userFullName = "${it.name} ${it.lastname}"
            Text(
                text = userFullName,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White,
            )
        }
        SpacerUi(height = 15.dp)
        Text(
            text = "¿Comó te podemos ayudar hoy?",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            color = Color.White,
        )
        SpacerUi(height = 15.dp)
        HomeContainerNavigation(navController = navController)
    }
}

@Composable
private fun HomeContainerNavigation(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeContainerNavigationLink(
            imageVector = Icons.Default.CalendarMonth,
            text = "Citas",
            onClick = { navController.navigate(Router.citasRoute) }
        )
        Spacer(modifier = Modifier.width(10.dp))
        HomeContainerNavigationLink(
            imageVector = Icons.Default.Pets,
            text = "Mascotas",
            onClick = { }
        )
        Spacer(modifier = Modifier.width(10.dp))
        HomeContainerNavigationLink(
            imageVector = Icons.Default.MedicalServices,
            text = "Doctores",
            onClick = { }
        )
        Spacer(modifier = Modifier.width(10.dp))
        HomeContainerNavigationLink(
            imageVector = Icons.Default.Person,
            text = "Mi perfil",
            onClick = { }
        )
    }
}

@Composable
private fun HomeContainerNavigationLink(
    imageVector: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = BluePrimary, shape = RoundedCornerShape(10.dp))
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(10.dp))
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = text,
                tint = BluePrimary,
                modifier = Modifier.size(40.dp)
            )
        }
        SpacerUi(height = 5.dp)
        Text(
            text = text,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            color = Color.White,
        )
    }
}

@Composable
private fun NoCitas() {
    SpacerUi(height = 20.dp)
    Image(
        painter = painterResource(id = R.drawable.catdog),
        contentDescription = "Cat - dog image",
        modifier = Modifier
            .fillMaxWidth()
            .size(150.dp)
    )
    Text(
        text = "No hay ninguna cita para sus mascotas", color = GrayColor,
        textAlign = TextAlign.Center,
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth()
    )
}
