package com.example.tiendapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiendapp.data.local.AppDatabase
import com.example.tiendapp.data.repository.UserRepository
import com.example.tiendapp.navigation.AppNavigation
import com.example.tiendapp.ui.theme.TiendAppTheme
import com.example.tiendapp.viewmodel.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TiendAppTheme {
                val database = AppDatabase.getDatabase(applicationContext)
                val userRepository = UserRepository(database.userDao())

                // Inyectamos repositorio y aplicación para la carga de JSON
                val userViewModel: UserViewModel =
                    viewModel(factory = UserViewModelFactory(userRepository, application))

                val productViewModel: ProductViewModel = viewModel()
                val homeViewModel: HomeViewModel = viewModel()
                val orderViewModel: OrderViewModel = viewModel()
                val contactViewModel: ContactViewModel = viewModel()

                AppNavigation(
                    userViewModel = userViewModel,
                    homeViewModel = homeViewModel,
                    productViewModel = productViewModel,
                    orderViewModel = orderViewModel,
                    contactViewModel = contactViewModel
                )
            }
        }
    }
}
