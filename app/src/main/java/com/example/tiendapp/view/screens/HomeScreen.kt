package com.example.tiendapp.view.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.tiendapp.auth.PermissionManager
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiendapp.viewmodel.HomeViewModel
import com.example.tiendapp.viewmodel.ProductViewModel
import com.example.tiendapp.viewmodel.OrderViewModel
import com.example.tiendapp.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    userViewModel: UserViewModel,
    productViewModel: ProductViewModel,
    orderViewModel: OrderViewModel,
    onNavigateToContact: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onLogout: () -> Unit // Agregado para el botón de cerrar sesión
) {
    val currentUser by userViewModel.currentUser.collectAsStateWithLifecycle()
    val orderMessage by orderViewModel.message.collectAsStateWithLifecycle()
    val weather by homeViewModel.weather.collectAsStateWithLifecycle()

    val role = currentUser?.role

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ToxcitryPC",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = {
                        userViewModel.logout()
                        onLogout()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                // Widget de Clima
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = weather.icon, fontSize = 32.sp)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(text = weather.temperature, style = MaterialTheme.typography.titleLarge)
                            Text(text = weather.description, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Bienvenido a ToxcitryPC",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(16.dp))
                ImageCarousel(homeViewModel)
                Spacer(Modifier.height(24.dp))
            }

            item {
                if (role != null) {
                    AnimatedButton("Ver Perfil", onNavigateToProfile)
                    Spacer(Modifier.height(12.dp))
                }

                if (PermissionManager.canAccessOrders(role)) {
                    AnimatedButton("Ver Mis Pedidos", onNavigateToOrders)
                    Spacer(Modifier.height(12.dp))
                }

                AnimatedButton("Ver Productos", onNavigateToProducts)
                Spacer(Modifier.height(12.dp))

                AnimatedButton("Formulario de Contacto", onNavigateToContact)
                Spacer(Modifier.height(12.dp))
            }

            item {
                orderMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                    LaunchedEffect(it) {
                        delay(2000)
                        orderViewModel.clearMessage()
                    }
                }
            }
        }
    }
}

@Composable
fun ImageCarousel(viewModel: HomeViewModel) {
    val images = viewModel.carouselImages
    val pagerState = rememberPagerState(pageCount = { images.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) { page ->
        Image(
            painter = painterResource(id = images[page]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun AnimatedButton(text: String, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .scale(scale),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}
