package com.example.tiendapp.view.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tiendapp.model.domain.Product
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.model.domain.User
import com.example.tiendapp.viewmodel.ProductViewModel

val drawableImages = listOf(
    "tecladomecanicorgb",
    "camara",
    "relojdeportivo",
    "audifonos"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    productViewModel: ProductViewModel,
    currentUser: User?,
    product: Product? = null,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val isEditing = product != null

    // 🔒 CONTROL DE PERMISOS
    val hasPermission = currentUser?.role == Role.ADMIN || currentUser?.role == Role.VENDEDOR

    if (!hasPermission) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "No tienes permisos", Toast.LENGTH_LONG).show()
            onBack()
        }
        return
    }

    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    // 🔄 RELLENAR DATOS AL EDITAR
    LaunchedEffect(product) {
        product?.let {
            nombre = it.nombre
            descripcion = it.descripcion
            precio = it.precio.toString()
            stock = it.stock.toString()
            imagenUrl = it.imagenUrl
        }
    }

    // 📸 CONFIGURACIÓN DE CÁMARA NATIVA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            // En una app real guardarías el bitmap a archivo y usarías la URI
            // Por ahora, simulamos que se capturó una imagen nueva
            imagenUrl = "Imagen_Capturada_${System.currentTimeMillis()}"
            Toast.makeText(context, "Imagen capturada con éxito", Toast.LENGTH_SHORT).show()
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (isEditing) "Editar Producto" else "Nuevo Producto") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = precio,
                    onValueChange = { if (it.all { char -> char.isDigit() }) precio = it },
                    label = { Text("Precio") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = stock,
                    onValueChange = { if (it.all { char -> char.isDigit() }) stock = it },
                    label = { Text("Stock") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            // Fila de Imagen (Dropdown + Cámara)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = imagenUrl,
                        onValueChange = { imagenUrl = it },
                        label = { Text("Imagen del producto") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true).fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        drawableImages.forEach { image ->
                            DropdownMenuItem(
                                text = { Text(image) },
                                onClick = {
                                    imagenUrl = image
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                IconButton(
                    onClick = { cameraLauncher.launch(null) },
                    modifier = Modifier
                        .size(56.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.medium)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Cámara")
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val pInt = precio.toIntOrNull() ?: 0
                    val sInt = stock.toIntOrNull() ?: 0
                    
                    val nuevoProducto = Product(
                        id = product?.id ?: 0L,
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = pInt,
                        stock = sInt,
                        imagenUrl = imagenUrl
                    )

                    if (isEditing) productViewModel.updateProduct(nuevoProducto)
                    else productViewModel.insertProduct(nuevoProducto)

                    onSaved()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(if (isEditing) "Actualizar Producto" else "Guardar Producto")
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
