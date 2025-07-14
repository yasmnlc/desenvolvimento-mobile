package com.example.msgapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.msgapp.ui.view.ChatScreen
import com.example.msgapp.ui.view.notifyNewMessage
import com.example.msgapp.viewmodel.MsgViewModel
import com.example.msgapp2.ui.view.RoomSelector
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

        FirebaseApp.initializeApp(this)
        setContent {
            MsgAppTheme {
                MsgAppRoot()
            }
        }
    }
}

@Composable
fun MsgAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = androidx.compose.ui.graphics.Color(0xFF1976D2),
            secondary = androidx.compose.ui.graphics.Color(0xFF42A5F5)
        ),
        content = content
    )
}

@Composable
fun MsgAppRoot(vm: MsgViewModel = viewModel()) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val firebaseAuth = remember { FirebaseAuth.getInstance() }
    val user by produceState(initialValue = firebaseAuth.currentUser) {

        if (value == null) {
            firebaseAuth.signInAnonymously()
                .addOnCompleteListener { task -> value = firebaseAuth.currentUser }
        }
    }

    val userId = user?.uid ?: "Yasmin"
    var userName by remember { mutableStateOf("Usuário-${userId}") }
    var currentRoom by remember { mutableStateOf("Geral") }
    var lastNotifiedId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(currentRoom) {
        vm.switchRoom(currentRoom)
    }

    Column {
        RoomSelector(onRoomSelected = { if (it.isNotBlank()) currentRoom = it })
        ChatScreen(
            username = userName,
            userId = userId,
            messages = vm.messages.collectAsState().value,
            onSend = { text -> vm.sendMessage(userId, userName, text) },
            currentRoom = currentRoom,
            lastNotifiedId = lastNotifiedId,
            onNotify = { msg ->
                notifyNewMessage(context, msg)
                lastNotifiedId = msg.id
            }
        )
    }
}