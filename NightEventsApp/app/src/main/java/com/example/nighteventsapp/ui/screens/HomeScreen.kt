package com.example.nighteventsapp.ui.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavHostController
import com.example.nighteventsapp.models.eventList

@Composable
fun HomeScreen(navController: NavHostController, context: Context) {
    // Cria o canal de notificação
    createNotificationChannel(context)

    Column {
        // Seção de eventos inscritos
        val subscribedEvents = eventList.filter { it.isSubscribed.value }
        if (subscribedEvents.isNotEmpty()) {
            Text(
                text = "Eventos Inscritos",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(16.dp)
            )
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(subscribedEvents) { event ->
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                navController.navigate("eventDetails/${event.id}")
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = event.imageRes),
                            contentDescription = event.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de todos os eventos
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(eventList) { event ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("eventDetails/${event.id}")
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            // Imagem do evento
                            Image(
                                painter = painterResource(id = event.imageRes),
                                contentDescription = event.title,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = event.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = event.location,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            // Ícone de favorito
                            Icon(
                                imageVector = if (event.isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favoritar",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        event.isFavorite.value = !event.isFavorite.value
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = event.description,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Botão de inscrição
                        Button(
                            onClick = {
                                event.isSubscribed.value = !event.isSubscribed.value
                                if (event.isSubscribed.value) {
                                    sendNotification(context, event.title)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (event.isSubscribed.value) "Inscrito" else "Se Inscrever"
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botão ver mais
                        Button(
                            onClick = {
                                navController.navigate("eventDetails/${event.id}")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Ver mais sobre ${event.title}")
                        }
                    }
                }
            }
        }
    }
}

// Criação do canal de notificação (requisito do Android 8+)
private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Inscrição de Eventos"
        val descriptionText = "Canal para notificações de inscrição em eventos"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("EVENT_CHANNEL", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

// Envia uma notificação de confirmação de inscrição
private fun sendNotification(context: Context, eventTitle: String) {
    val builder = NotificationCompat.Builder(context, "EVENT_CHANNEL")
        .setSmallIcon(android.R.drawable.ic_notification_overlay) // Use um ícone apropriado no seu app
        .setContentTitle("Inscrição Confirmada")
        .setContentText("Você foi inscrito no evento: $eventTitle")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        notify(eventTitle.hashCode(), builder.build())
    }
}
