package com.example.tiendapp.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiendapp.viewmodel.UserViewModel
import com.example.tiendapp.model.domain.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userViewModel: UserViewModel,
    onLogout: () -> Unit
) {
    val currentUser by userViewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil") }
            )
        }
    ) { padding ->

        if (currentUser == null) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay usuario autenticado")
            }
            return@Scaffold
        }

        val user = currentUser!!

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text("Nombre", style = MaterialTheme.typography.labelMedium)
            Text(user.name, style = MaterialTheme.typography.bodyLarge)

            Divider()

            Text("Correo", style = MaterialTheme.typography.labelMedium)
            Text(user.email, style = MaterialTheme.typography.bodyLarge)

            Divider()

            Text("Rol", style = MaterialTheme.typography.labelMedium)
            Text(
                text = when (user.role) {
                    Role.ADMIN -> "Administrador"
                    Role.CLIENTE -> "Cliente"
                    Role.VENDEDOR -> "Vendedor"
                    Role.REPARTIDOR -> "Repartidor"
                },
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                onClick = {
                    userViewModel.logout()
                    onLogout()
                }
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
