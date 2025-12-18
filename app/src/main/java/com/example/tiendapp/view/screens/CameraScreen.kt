package com.example.tiendapp.view.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.auth.PermissionManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    role: Role,
    onImageSelected: (Uri) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cámara / Imagen") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (PermissionManager.canCreateProduct(role)) {
                Button(onClick = {
                    // Aquí conectas cámara o picker
                }) {
                    Text("Tomar Foto / Elegir Imagen")
                }
            } else {
                Text(
                    text = "No tienes permisos para acceder a la cámara",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
