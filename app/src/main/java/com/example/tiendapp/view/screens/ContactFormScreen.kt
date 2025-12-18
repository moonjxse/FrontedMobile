package com.example.tiendapp.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tiendapp.model.domain.Region
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.auth.PermissionManager
import com.example.tiendapp.viewmodel.ContactViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(
    role: Role,
    contactViewModel: ContactViewModel,
    onNavigateBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val nombre by contactViewModel.nombre.collectAsStateWithLifecycle()
    val telefono by contactViewModel.telefono.collectAsStateWithLifecycle()
    val correo by contactViewModel.correo.collectAsStateWithLifecycle()
    val regionSeleccionada by contactViewModel.regionSeleccionada.collectAsStateWithLifecycle()
    val mensaje by contactViewModel.mensaje.collectAsStateWithLifecycle()

    val nombreError by contactViewModel.nombreError.collectAsStateWithLifecycle()
    val telefonoError by contactViewModel.telefonoError.collectAsStateWithLifecycle()
    val correoError by contactViewModel.correoError.collectAsStateWithLifecycle()
    val regionError by contactViewModel.regionError.collectAsStateWithLifecycle()
    val mensajeError by contactViewModel.mensajeError.collectAsStateWithLifecycle()

    val regiones by contactViewModel.regiones.collectAsStateWithLifecycle()
    val isLoading by contactViewModel.isLoading.collectAsStateWithLifecycle()
    val guardadoExitoso by contactViewModel.guardadoExitoso.collectAsStateWithLifecycle()

    LaunchedEffect(guardadoExitoso) {
        if (guardadoExitoso) {
            snackbarHostState.showSnackbar("Contacto guardado correctamente")
            contactViewModel.resetGuardadoExitoso()
            delay(800)
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulario de Contacto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = contactViewModel::onNombreChange,
                label = { Text("Nombre") },
                isError = nombreError != null,
                supportingText = {
                    nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = contactViewModel::onTelefonoChange,
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = telefonoError != null,
                supportingText = {
                    telefonoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = correo,
                onValueChange = contactViewModel::onCorreoChange,
                label = { Text("Correo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = correoError != null,
                supportingText = {
                    correoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            RegionDropdown(
                regiones = regiones,
                selectedRegion = regionSeleccionada,
                onRegionSelected = contactViewModel::onRegionChange,
                error = regionError
            )

            OutlinedTextField(
                value = mensaje,
                onValueChange = contactViewModel::onMensajeChange,
                label = { Text("Mensaje") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                maxLines = 5,
                isError = mensajeError != null,
                supportingText = {
                    mensajeError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            )

            Button(
                onClick = contactViewModel::saveContact,
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(22.dp))
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionDropdown(
    regiones: List<Region>,
    selectedRegion: Region?,
    onRegionSelected: (Region) -> Unit,
    error: String?
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedRegion?.nombre ?: "Seleccione una región",
            onValueChange = {},
            readOnly = true,
            label = { Text("Región") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            isError = error != null,
            supportingText = {
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            regiones.forEach { region ->
                DropdownMenuItem(
                    text = { Text(region.nombre) },
                    onClick = {
                        onRegionSelected(region)
                        expanded = false
                    }
                )
            }
        }
    }
}
