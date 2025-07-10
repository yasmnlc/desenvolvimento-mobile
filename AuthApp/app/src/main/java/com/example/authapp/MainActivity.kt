package com.example.authapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authapp.remote.AuthApi
import com.example.authapp.remote.RetrofitClient
import com.example.authapp.repository.AuthRepository
import com.example.authapp.ui.screen.*
import com.example.authapp.ui.theme.AuthAppTheme
import com.example.authapp.viewmodel.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Cria Retrofit + Repository
            val api = RetrofitClient.instance.create(AuthApi::class.java)
            val repository = AuthRepository(api, applicationContext)

            // Cria ViewModel com factory (necessário por causa do parâmetro no construtor)
            val factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return AuthViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Classe desconhecida de ViewModel")
                }
            }

            val viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

            // Define o conteúdo da tela
            setContent {
                AuthAppTheme {
                    AppContent(viewModel)
                }
            }

        } catch (e: Exception) {
            Log.e("MainActivity", "Erro crítico ao iniciar o app", e)
        }
    }

    @Composable
    fun AppContent(viewModel: AuthViewModel) {
        val authState by viewModel.authState.collectAsState()
        var currentScreen by remember { mutableStateOf("login") }

        Box(modifier = Modifier.fillMaxSize()) {
            when (authState) {
                is AuthState.Authenticated -> {
                    HomeScreen(viewModel = viewModel) {
                        viewModel.logout()
                        currentScreen = "login"
                    }
                }

                is AuthState.Unauthenticated, AuthState.Idle -> {
                    if (currentScreen == "login") {
                        LoginScreen(
                            viewModel = viewModel,
                            onAuthenticated = { viewModel.checkAuth() },
                            onNavigateToRegister = { currentScreen = "register" }
                        )
                    } else {
                        RegisterScreen(
                            viewModel = viewModel,
                            onRegisterSuccess = { viewModel.checkAuth() },
                            onNavigateToLogin = { currentScreen = "login" }
                        )
                    }
                }

                is AuthState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is AuthState.Error -> {
                    val msg = (authState as AuthState.Error).message
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Erro: $msg", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
