package com.example.tiendapp.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.tiendapp.view.screens.*
import com.example.tiendapp.viewmodel.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Products : Screen("products")
    object ProductDetail : Screen("product_detail/{id}") {
        fun create(id: Long) = "product_detail/$id"
    }
    object ProductForm : Screen("product_form?productId={productId}") {
        fun create(productId: Long? = null) = if (productId != null) "product_form?productId=$productId" else "product_form"
    }
    object Orders : Screen("orders")
    object Delivery : Screen("delivery")
    object Contact : Screen("contact")
    object Camera : Screen("camera")
    object Admin : Screen("admin")
}

@Composable
fun AppNavigation(
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel,
    productViewModel: ProductViewModel,
    orderViewModel: OrderViewModel,
    contactViewModel: ContactViewModel
) {
    val navController = rememberNavController()
    val currentUser by userViewModel.currentUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        /* ---------- LOGIN ---------- */
        composable(Screen.Login.route) {
            LoginScreen(
                userViewModel = userViewModel,
                onLoginSuccess = { _ ->
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        /* ---------- REGISTER ---------- */
        composable(Screen.Register.route) {
            RegisterScreen(
                userViewModel = userViewModel,
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }

        /* ---------- HOME ---------- */
        composable(Screen.Home.route) {
            HomeScreen(
                homeViewModel = homeViewModel,
                userViewModel = userViewModel,
                productViewModel = productViewModel,
                orderViewModel = orderViewModel,
                onNavigateToContact = { navController.navigate(Screen.Contact.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToOrders = { navController.navigate(Screen.Orders.route) },
                onNavigateToProducts = { navController.navigate(Screen.Products.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        /* ---------- PROFILE ---------- */
        composable(Screen.Profile.route) {
            ProfileScreen(
                userViewModel = userViewModel,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        /* ---------- PRODUCT LIST ---------- */
        composable(Screen.Products.route) {
            ProductListScreen(
                productViewModel = productViewModel,
                currentUser = currentUser,
                onAddProduct = {
                    navController.navigate(Screen.ProductForm.create())
                },
                onViewProduct = { id ->
                    navController.navigate(Screen.ProductDetail.create(id))
                },
                onEditProduct = { id ->
                    navController.navigate(Screen.ProductForm.create(id))
                },
                onOrderProduct = { product ->
                    currentUser?.let { user ->
                        orderViewModel.createOrder(product.id, user.id)
                    }
                }
            )
        }

        /* ---------- PRODUCT DETAIL ---------- */
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStack ->
            val id = backStack.arguments?.getLong("id") ?: return@composable

            ProductDetailScreen(
                productId = id,
                productViewModel = productViewModel,
                orderViewModel = orderViewModel,
                currentUser = currentUser,
                onBack = { navController.popBackStack() }
            )
        }

        /* ---------- PRODUCT FORM ---------- */
        composable(
            route = Screen.ProductForm.route,
            arguments = listOf(
                navArgument("productId") { 
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStack ->
            val productId = backStack.arguments?.getLong("productId")?.takeIf { it != -1L }
            
            // Si hay un ID, cargamos el producto en el ViewModel
            LaunchedEffect(productId) {
                if (productId != null) {
                    productViewModel.loadProductById(productId)
                }
            }
            
            val productToEdit by productViewModel.selectedProduct.collectAsState()

            ProductFormScreen(
                productViewModel = productViewModel,
                currentUser = currentUser,
                product = if (productId != null) productToEdit else null,
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        /* ---------- ORDERS ---------- */
        composable(Screen.Orders.route) {
            val userRole = currentUser?.role ?: return@composable
            OrderListScreen(
                orderViewModel = orderViewModel,
                userId = currentUser?.id,
                userRole = userRole,
                onBack = { navController.popBackStack() }
            )
        }

        /* ---------- DELIVERY ---------- */
        composable(Screen.Delivery.route) {
            val userRole = currentUser?.role ?: return@composable
            DeliveryScreen(
                role = userRole,
                orderViewModel = orderViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        /* ---------- CONTACT ---------- */
        composable(Screen.Contact.route) {
            val userRole = currentUser?.role ?: return@composable
            ContactFormScreen(
                role = userRole,
                contactViewModel = contactViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        /* ---------- CAMERA ---------- */
        composable(Screen.Camera.route) {
            val userRole = currentUser?.role ?: return@composable
            CameraScreen(
                role = userRole,
                onImageSelected = { /* Manejar URI */ },
                onBack = { navController.popBackStack() }
            )
        }

        /* ---------- ADMIN DASHBOARD ---------- */
        composable(Screen.Admin.route) {
            val userRole = currentUser?.role ?: return@composable
            AdminDashboardScreen(
                role = userRole,
                onUsersClick = { /* No Screen defined for this yet */ },
                onProductsClick = { navController.navigate(Screen.Products.route) },
                onOrdersClick = { navController.navigate(Screen.Orders.route) },
                onAddProductClick = { navController.navigate(Screen.ProductForm.create()) }
            )
        }
    }
}
