package com.example.msgapp.ui.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.msgapp.R
import com.example.msgapp.model.Message

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun notifyNewMessage(context: Context, msg: Message) {
    val channelId = "msg_channel"
    val channelName = "Mensagens"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Nova mensagem de ${msg.senderName ?: "Desconhecido"}")
        .setContentText(msg.text ?: "")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    NotificationManagerCompat.from(context).notify(msg.id.hashCode(), notification)
}
