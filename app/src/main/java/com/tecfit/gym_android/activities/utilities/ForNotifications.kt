package com.tecfit.gym_android.activities.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tecfit.gym_android.R

class ForNotifications {
    companion object {


        private val CHANNEL_ID = "channelID"
        private val CHANNEL_NAME = "channelName"
        private val NOTIFICATION_ID = 1
        //                    sendNotification(context!!, "Tu membresía expira hoy mismo 😨\nRenuévala en nuestro gimnasio y sigue entrenándote 💪🏻", activity)
        //                    sendNotification(context!!, "Tu membresía expira en 3 días \nRenuévala en nuestro gimnasio y sigue entrenándote 💪🏻", activity)

        fun sendNotification(context: Context?){

            val notification = ForInternalStorageNotification.loadNotification(context)
            if (notification!= null){
                if (notification.notificationDisplayedSameDay){
                    showNotification(context!!, "Hoy expira tu membresía 😨", "Tu membresía expira hoy mismo. Renuévala en nuestro gimnasio y sigue entrenándote 💪🏻")
                } else if (notification.notificationDisplayedThreeDaysBefore){
                    showNotification(context!!, "Membresía por expirar 😥", "Tu membresía expira en pocos días. Renuévala en nuestro gimnasio y sigue entrenándote 💪🏻")
                }

            }

        }


        private fun showNotification(context: Context, title:String, message:String){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                    lightColor = Color.RED
                    enableLights(true)
                }

                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                manager.createNotificationChannel(channel)
            }


            val notification = NotificationCompat.Builder(context, CHANNEL_ID).also {
                it.setContentTitle(title)
                it.setContentText(message)
                it.setSmallIcon(R.drawable.ic_notification)
                it.priority = NotificationCompat.PRIORITY_HIGH
            }.build()

            val notificationManager = NotificationManagerCompat.from(context)

            notificationManager.notify(NOTIFICATION_ID, notification)

        }


    }
}